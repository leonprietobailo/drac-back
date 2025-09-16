package com.leonbros.drac.service;

import com.leonbros.drac.dto.external.revolut.response.OrderResponse;
import com.leonbros.drac.dto.external.revolut.response.PaymentResponse;
import com.leonbros.drac.entity.order.Order;
import com.leonbros.drac.entity.order.OrderElement;
import com.leonbros.drac.entity.order.PaymentTransaction;
import com.leonbros.drac.repository.OrderRepository;
import com.leonbros.drac.repository.PaymentTransactionRepository;
import com.leonbros.drac.utils.PdfGeneratorUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class InvoiceGenerationService {

  private static final String INVOICE_TEMPLATE = "invoice.ftl";
  private static final String PAYMENT_API_URL =
      "https://sandbox-merchant.revolut.com/api/orders/%s";

  private final String authToken;
  private final Configuration configuration;
  private final RestTemplate restTemplate;

  private final PaymentTransactionRepository paymentTransactionRepository;
  private final OrderRepository orderRepository;

  @Autowired
  public InvoiceGenerationService(@Value("${revolut.secret}") String authToken,
      @Qualifier("provideConfig") Configuration configuration, RestTemplate restTemplate,
      PaymentTransactionRepository paymentTransactionRepository, OrderRepository orderRepository) {
    this.authToken = authToken;
    this.configuration = configuration;
    this.restTemplate = restTemplate;
    this.paymentTransactionRepository = paymentTransactionRepository;
    this.orderRepository = orderRepository;
  }

  @Transactional
  public Optional<Path> generateInvoiceIfRequired(String orderId) {
    final Optional<PaymentTransaction> transactionOptional =
        paymentTransactionRepository.findByIdentifier(orderId);
    if (transactionOptional.isEmpty()) {
      log.error("Payment transaction not found for orderId={}", orderId);
      return Optional.empty();
    }
    PaymentTransaction paymentTransaction = transactionOptional.get();

    if (paymentTransaction.getOrder().getBillingInfo() == null) {
      return Optional.empty();
    }

    paymentTransaction = generateInvoiceId(paymentTransaction);
    paymentTransaction = fetchPaymentMethod(paymentTransaction);

    final Dto dto = Dto.of(paymentTransaction);

    final Path output = Path.of(String.format("/tmp/invoices/%s.pdf", dto.getId()));

    try {
      Files.createDirectories(output.getParent());
      final Template template = configuration.getTemplate(INVOICE_TEMPLATE);
      final StringWriter sw = new StringWriter();
      template.process(dto, sw);
      PdfGeneratorUtils.renderHtmlToPdf(sw.toString(), output);
    } catch (IOException e) {
      log.error("Failed to retrieve template. : {}. Order Id: {} Exception Message: {}",
          INVOICE_TEMPLATE, paymentTransaction.getIdentifier(), e.getMessage(), e);
    } catch (TemplateException e) {
      log.error(
          "Exception while parsing template. Template: {}. Order Id: {} Exception Message: {}",
          INVOICE_TEMPLATE, orderId, e.getMessage(), e);
    }

    return Optional.of(output);
  }

  @Transactional
  public synchronized PaymentTransaction generateInvoiceId(PaymentTransaction paymentTransaction) {
    final int year =
        paymentTransaction.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            .getYear();
    final int nextNumber = orderRepository.findMaxNumberByYear(year) + 1;
    final Order order = paymentTransaction.getOrder();
    order.setInvoiceYear(year);
    order.setInvoiceNumber(nextNumber);
    orderRepository.save(order);
    return paymentTransaction;
  }

  @Transactional
  public PaymentTransaction fetchPaymentMethod(PaymentTransaction paymentTransaction) {
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));
    headers.setBearerAuth(authToken);
    headers.set("Revolut-Api-Version", "2024-09-01");
    final HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

    final ResponseEntity<OrderResponse> response =
        restTemplate.exchange(String.format(PAYMENT_API_URL, paymentTransaction.getIdentifier()),
            HttpMethod.GET, requestEntity, OrderResponse.class);
    if (!response.getStatusCode().is2xxSuccessful()) {
      throw new IllegalStateException(
          "Order could not be found. Payment Id: " + paymentTransaction.getIdentifier());
    }
    if (response.getBody() == null) {
      throw new IllegalStateException(
          "Response body was null for Order Id: " + paymentTransaction.getIdentifier());
    }
    paymentTransaction.setPaymentMethod(
        response.getBody().getPayments().getFirst().getPayment_method().getType().toString());
    return paymentTransactionRepository.save(paymentTransaction);
  }

  @Getter
  public static class Dto {
    private final String id;
    private final String date;
    private final String entityName;
    private final String entityId;
    private final String entityAddress;
    private final String entityEmail;
    private final List<Item> items;
    private final String base;
    private final String vat;
    private final String total;
    private final String paymentMethod;

    private Dto(String id, String date, String entityName, String entityId, String entityAddress,
        String entityEmail, List<Item> items, String base, String vat, String total,
        String paymentMethod) {
      this.id = id;
      this.date = date;
      this.entityName = entityName;
      this.entityId = entityId;
      this.entityAddress = entityAddress;
      this.entityEmail = entityEmail;
      this.items = items;
      this.base = base;
      this.vat = vat;
      this.total = total;
      this.paymentMethod = paymentMethod;
    }

    public static Dto of(PaymentTransaction paymentTransaction) {
      final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
      final Order order = paymentTransaction.getOrder();

      return new Dto(String.format("FAC-%d-%04d", order.getInvoiceYear(), order.getInvoiceNumber()),
          sdf.format(new Date()), order.getBillingInfo().getEntityName(),
          order.getBillingInfo().getTaxId(),
          Stream.of(order.getBillingAddress().getStreet(), order.getBillingAddress().getFlat(),
                  order.getBillingAddress().getPostalCode(), order.getBillingAddress().getCity(),
                  order.getBillingAddress().getProvince()).filter(Objects::nonNull).map(String::trim)
              .filter(s -> !s.isBlank()).collect(Collectors.joining(", ")),
          order.getBillingInfo().getEmail(), Item.of(order.getOrderElements()),
          computePercentile(order.getOrderElements(), 0.79),
          computePercentile(order.getOrderElements(), 0.21),
          computePercentile(order.getOrderElements(), 1), paymentTransaction.getPaymentMethod());
    }

    private static String computePercentile(List<OrderElement> elements, double percentile) {
      final NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.of("ca", "ES"));
      BigDecimal result = new BigDecimal(0);
      for (OrderElement element : elements) {
        result = result.add(BigDecimal.valueOf(element.getItem().getPrice())
            .multiply(new BigDecimal(element.getQuantity())).multiply(new BigDecimal(percentile)));
      }
      return nf.format(result);
    }

    @Getter
    public static class Item {
      private final String concept;
      private final String quantity;
      private final String noVatPrice;
      private final String vatPercentage;
      private final String subtotal;
      private final String vat;
      private final String total;

      private Item(String concept, String quantity, String noVatPrice, String vatPercentage,
          String subtotal, String vat, String total) {
        this.concept = concept;
        this.quantity = quantity;
        this.noVatPrice = noVatPrice;
        this.vatPercentage = vatPercentage;
        this.subtotal = subtotal;
        this.vat = vat;
        this.total = total;
      }

      public static List<Item> of(List<OrderElement> orderElements) {
        final NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.of("ca", "ES"));
        final List<Item> result = new ArrayList<>();
        for (OrderElement element : orderElements) {
          final String color =
              element.getSelectedColor() != null ? element.getSelectedColor().getColor() : null;
          final String size =
              element.getSelectedSize() != null ? element.getSelectedSize().getSize() : null;
          final BigDecimal noVatPrice =
              BigDecimal.valueOf(element.getItem().getPrice()).multiply(new BigDecimal("0.79"));
          final BigDecimal vatPrice =
              new BigDecimal("0.21").multiply(new BigDecimal(element.getQuantity()))
                  .multiply(BigDecimal.valueOf(element.getItem().getPrice()));
          result.add(new Item(String.format("%s %s", element.getItem().getTitle(),
              Stream.of(color, size).filter(Objects::nonNull).collect(Collectors.joining(", "))),
              element.getQuantity().toString(), nf.format(noVatPrice), "21%",
              nf.format(noVatPrice.multiply(new BigDecimal(element.getQuantity()))),
              nf.format(vatPrice), nf.format(new BigDecimal(element.getQuantity()).multiply(
              BigDecimal.valueOf(element.getItem().getPrice())))));
        }
        return result;
      }
    }
  }
}
