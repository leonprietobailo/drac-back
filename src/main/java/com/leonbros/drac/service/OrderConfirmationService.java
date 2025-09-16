package com.leonbros.drac.service;

import com.leonbros.drac.entity.order.OrderElement;
import com.leonbros.drac.entity.order.PaymentTransaction;
import com.leonbros.drac.port.EmailGateway;
import com.leonbros.drac.repository.PaymentTransactionRepository;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
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
public class OrderConfirmationService {

  private static final String ORDER_EMAIL_TEMPLATE = "order_email.ftl";

  private final Configuration config;
  private final EmailGateway emailGateway;

  private final PaymentTransactionRepository paymentTransactionRepository;

  @Autowired
  public OrderConfirmationService(@Qualifier("provideConfig") Configuration config,
      EmailGateway emailGateway, PaymentTransactionRepository paymentTransactionRepository) {
    this.config = config;
    this.emailGateway = emailGateway;
    this.paymentTransactionRepository = paymentTransactionRepository;
  }

  @Transactional(readOnly = true)
  public void sendOrderConfirmationEmail(String orderId, Optional<Path> invoice) {
    final Optional<PaymentTransaction> transactionOptional =
        paymentTransactionRepository.findByIdentifier(orderId);
    if (transactionOptional.isEmpty()) {
      log.error("Payment transaction not found for orderId={}", orderId);
      return;
    }
    final PaymentTransaction paymentTransaction = transactionOptional.get();
    final Dto dto = Dto.of(paymentTransaction);

    try {
      final Template template = config.getTemplate(ORDER_EMAIL_TEMPLATE);
      final StringWriter sw = new StringWriter();
      template.process(dto, sw);
      emailGateway.sendHtmlMail(paymentTransaction.getUser().getEmail(), "Confirmació de compra.",
          sw.toString(), invoice.orElse(null));
    } catch (IOException e) {
      log.error("Failed to retrieve template. : {}. Order Id: {} Exception Message: {}",
          ORDER_EMAIL_TEMPLATE, dto.getOrderId(), e.getMessage(), e);
    } catch (TemplateException e) {
      log.error(
          "Exception while parsing template. Template: {}. Order Id: {} Exception Message: {}",
          ORDER_EMAIL_TEMPLATE, orderId, e.getMessage(), e);
    }
  }

  @Getter
  public static class Dto {

    private String firstName;
    private String orderId;
    private String date;
    private String subtotal;
    private String shipment;
    private String total;
    private String recipientFirstName;
    private String recipientLastName;
    private String recipientPhone;
    private String shipmentType;
    private String address;
    private List<Item> items;

    private Dto(String firstName, String orderId, String date, String subtotal, String shipment,
        String total, String recipientFirstName, String recipientLastName, String recipientPhone,
        String shipmentType, String address, List<Item> items) {
      this.firstName = firstName;
      this.orderId = orderId;
      this.date = date;
      this.subtotal = subtotal;
      this.shipment = shipment;
      this.total = total;
      this.recipientFirstName = recipientFirstName;
      this.recipientLastName = recipientLastName;
      this.recipientPhone = recipientPhone;
      this.shipmentType = shipmentType;
      this.address = address;
      this.items = items;
    }

    public static Dto of(PaymentTransaction paymentTransaction) {
      final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
      final NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.of("ca", "ES"));
      return new Dto(paymentTransaction.getUser().getFirstName(),
          paymentTransaction.getIdentifier(), sdf.format(new Date()),
          nf.format(paymentTransaction.getOrder().getSubtotal()),
          nf.format(paymentTransaction.getOrder().getShipment()), nf.format(
          new BigDecimal(String.valueOf(paymentTransaction.getOrder().getSubtotal())).add(
              new BigDecimal(String.valueOf(paymentTransaction.getOrder().getShipment())))),
          paymentTransaction.getOrder().getRecipient().getFirstname(),
          paymentTransaction.getOrder().getRecipient().getSurname(),
          paymentTransaction.getOrder().getRecipient().getTelephone(),
          paymentTransaction.getOrder().getAddressType(),
          Stream.of(paymentTransaction.getOrder().getShippingAddress().getStreet(),
                  paymentTransaction.getOrder().getShippingAddress().getFlat(),
                  paymentTransaction.getOrder().getShippingAddress().getPostalCode(),
                  paymentTransaction.getOrder().getShippingAddress().getCity(),
                  paymentTransaction.getOrder().getShippingAddress().getProvince())
              .filter(Objects::nonNull).map(String::trim).filter(s -> !s.isBlank())
              .collect(Collectors.joining(", ")),
          Item.of(paymentTransaction.getOrder().getOrderElements()));

    }

    @Getter
    public static class Item {
      private String title;
      private String color;
      private String size;
      private String quantity;
      private String price;
      private String total;

      private Item(String title, String color, String size, String quantity, String price,
          String total) {
        this.title = title;
        this.color = color;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
      }

      public static List<Item> of(List<OrderElement> orderElements) {
        final NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.of("ca", "ES"));
        final List<Item> result = new ArrayList<>();
        for (OrderElement orderElement : orderElements) {
          final String color = orderElement.getSelectedColor() != null ?
              orderElement.getSelectedColor().getColor() :
              null;
          final String size = orderElement.getSelectedSize() != null ?
              orderElement.getSelectedSize().getSize() :
              null;

          result.add(new Item(orderElement.getItem().getTitle(), color, size,
              orderElement.getQuantity().toString(), nf.format(orderElement.getItem().getPrice()),
              nf.format(new BigDecimal(String.valueOf(orderElement.getItem().getPrice())).multiply(
                  new BigDecimal(String.valueOf(orderElement.getQuantity()))))));
        }
        return result;
      }
    }
  }
}
