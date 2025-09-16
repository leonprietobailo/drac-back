package com.leonbros.drac.service.user;

import com.github.f4b6a3.uuid.UuidCreator;
import com.leonbros.drac.dto.external.request.AddressDto;
import com.leonbros.drac.dto.external.request.BillingDetailsDto;
import com.leonbros.drac.dto.external.request.CustomerDto;
import com.leonbros.drac.dto.external.request.PaymentObjectRequest;
import com.leonbros.drac.dto.external.revolut.request.Customer;
import com.leonbros.drac.dto.external.revolut.request.OrderBody;
import com.leonbros.drac.dto.external.revolut.response.OrderResponse;
import com.leonbros.drac.dto.request.checkout.RequestPaymentDto;
import com.leonbros.drac.dto.response.cart.CartItemResponse;
import com.leonbros.drac.dto.response.checkout.RequestPaymentResponse;
import com.leonbros.drac.entity.cart.Cart;
import com.leonbros.drac.entity.item.Color;
import com.leonbros.drac.entity.item.Item;
import com.leonbros.drac.entity.item.Size;
import com.leonbros.drac.entity.order.Order;
import com.leonbros.drac.entity.order.OrderElement;
import com.leonbros.drac.entity.order.PaymentStatusValues;
import com.leonbros.drac.entity.order.PaymentTransaction;
import com.leonbros.drac.entity.user.Address;
import com.leonbros.drac.entity.user.BillingInfo;
import com.leonbros.drac.entity.user.Recipient;
import com.leonbros.drac.entity.user.User;
import com.leonbros.drac.repository.AddressRepository;
import com.leonbros.drac.repository.BillingInfoRepository;
import com.leonbros.drac.repository.CartRepository;
import com.leonbros.drac.repository.ColorRepository;
import com.leonbros.drac.repository.ItemRepository;
import com.leonbros.drac.repository.PaymentTransactionRepository;
import com.leonbros.drac.repository.RecipientRepository;
import com.leonbros.drac.repository.SizeRepository;
import com.leonbros.drac.security.AuthUtils;
import com.leonbros.drac.service.CartService;
import com.leonbros.drac.service.RevolutApiService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CommonsLog
@Service
public class PurchaseService {

  private final RevolutApiService revolutApiService;

  private final ItemRepository itemRepository;
  private final SizeRepository sizeRepository;
  private final ColorRepository colorRepository;
  private final CartRepository cartRepository;
  private final AddressRepository addressRepository;
  private final BillingInfoRepository billingInfoRepository;
  private final PaymentTransactionRepository paymentTransactionRepository;
  private final RecipientRepository recipientRepository;

  @Autowired
  public PurchaseService(RevolutApiService revolutApiService, ItemRepository itemRepository,
      SizeRepository sizeRepository, ColorRepository colorRepository, CartRepository cartRepository,
      AddressRepository addressRepository, BillingInfoRepository billingInfoRepository,
      PaymentTransactionRepository paymentTransactionRepository,
      RecipientRepository recipientRepository) {
    this.revolutApiService = revolutApiService;
    this.itemRepository = itemRepository;
    this.sizeRepository = sizeRepository;
    this.colorRepository = colorRepository;
    this.cartRepository = cartRepository;
    this.addressRepository = addressRepository;
    this.billingInfoRepository = billingInfoRepository;
    this.paymentTransactionRepository = paymentTransactionRepository;
    this.recipientRepository = recipientRepository;
  }

  @Transactional
  public RequestPaymentResponse generatePayment(RequestPaymentDto dto) {
    final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (!AuthUtils.isUserAuthenticated(auth)) {
      return new RequestPaymentResponse(RequestPaymentResponse.Status.NON_AUTHENTICATED, null,
          null);
    }
    final Optional<Cart> cartOptional = cartRepository.findCartByCod(dto.getCart().getId());
    if (cartOptional.isEmpty()) {
      return new RequestPaymentResponse(RequestPaymentResponse.Status.CART_NOT_FOUND, null, null);
    }
    final Cart cart = cartOptional.get();
    final User user = cart.getUser();

    if (!auth.getName().equals(user.getEmail())) {
      return new RequestPaymentResponse(RequestPaymentResponse.Status.UNAUTHORIZED, null, null);
    }

    final OrderResponse orderResponse;
    try {
      orderResponse = revolutApiService.generatePaymentGateway(generateOrderBody(user, cart, dto));
    } catch (RevolutApiService.Non2xxException e) {
      return new RequestPaymentResponse(RequestPaymentResponse.Status.EXTERNAL_API_ERROR, null,
          null);
    }

    //    if (!orderResponse.getNextAction().mustRedirect()) {
    //      return new RequestPaymentResponse(RequestPaymentResponse.Status.UNEXPECTED_ERROR, null);
    //    }
    //    persistTransaction(dto, user, orderResponse.getOrderId())
    //
    //    ;
    final PaymentTransaction paymentTransaction =
        computePaymentTransaction(dto, user, cart, orderResponse.getId());

    paymentTransactionRepository.save(paymentTransaction);

    return new RequestPaymentResponse(RequestPaymentResponse.Status.REDIRECT,
        orderResponse.getCheckout_url(), orderResponse.getToken());
  }

  // Deprecated Monei, see what to do with this.
  @Deprecated
  private static PaymentObjectRequest generateRequestPayload(User user, Cart cart,
      RequestPaymentDto dto) {
    // Customer DTO
    final CustomerDto customerDto =
        new CustomerDto(user.getLastName().concat(", ").concat(user.getFirstName()),
            user.getEmail(), user.getTelephone());

    // Billing Details Dto.
    BillingDetailsDto billingDetailsDto = null;
    if (dto.isRequestBilling()) {
      billingDetailsDto = new BillingDetailsDto(dto.getBillingInfo().getEntityName(),
          new AddressDto("ES", dto.getBillingAddress().getCity(),
              dto.getBillingAddress().getStreet(), dto.getBillingAddress().getZip()));
    }
    // Price
    final int totalPrice = BigDecimal.valueOf(CartService.CheckoutPrices.of(cart.getCartItems(),
            RequestPaymentDto.ShipmentTypes.ADDRESS.equals(dto.getType())).total()).movePointRight(2)
        .intValue();
    // Build object
    return new PaymentObjectRequest(totalPrice, "EUR", UuidCreator.getTimeOrderedEpoch().toString(),
        "Drac de Ribes: Botiga Online", customerDto, billingDetailsDto,
        "https://example.com/checkout/callback", "https://example.com/checkout/complete",
        "https://example.com/checkout/cancel", "ca");
  }

  private static OrderBody generateOrderBody(User user, Cart cart, RequestPaymentDto dto) {
    final Customer customerDto =
        new Customer(null, String.format("%s, %s", user.getFirstName(), user.getLastName()),
            user.getTelephone(), user.getEmail());
    final int totalPrice = BigDecimal.valueOf(CartService.CheckoutPrices.of(cart.getCartItems(),
            RequestPaymentDto.ShipmentTypes.ADDRESS.equals(dto.getType())).total()).movePointRight(2)
        .intValue();
    return new OrderBody(totalPrice, "EUR", "Drac de Ribes: Compra online", user.getEmail(),
        user.getBirthdate(), OrderBody.EnforceChallengeValues.AUTOMATIC);
  }

  private PaymentTransaction computePaymentTransaction(RequestPaymentDto dto, User user, Cart cart,
      String transactionIdentifier) {
    final PaymentTransaction transaction =
        PaymentTransaction.builder().identifier(transactionIdentifier).createdAt(new Date())
            .status(PaymentStatusValues.PENDING.getStatus()).user(user).build();
    final Recipient recipient =
        recipientRepository.findByCodAndUserCod_Email(dto.getRecipient().getId(), user.getEmail());
    final Address shippingAddress =
        addressRepository.findAddressByCodAndUserCod_Email(dto.getAddress().getId(),
            user.getEmail());
    final BillingInfo billingInfo = dto.isRequestBilling() ?
        billingInfoRepository.findBillingInfoByCodAndUserCod_Email(dto.getBillingInfo().getId(),
            user.getEmail()) :
        null;
    final Address billingAddress = dto.isRequestBilling() ?
        addressRepository.findAddressByCodAndUserCod_Email(dto.getBillingAddress().getId(),
            user.getEmail()) :
        null;
    final CartService.CheckoutPrices prices = CartService.CheckoutPrices.of(cart.getCartItems(),
        dto.getType().equals(RequestPaymentDto.ShipmentTypes.ADDRESS));
    final Order order =
        new Order(null, transaction, dto.getType().toString(), shippingAddress, recipient,
            billingInfo, billingAddress, null, null, prices.subtotal(), prices.shipment(),
            new ArrayList<>());
    transaction.setOrder(order);
    for (CartItemResponse itemDto : dto.getCart().getItems()) {
      final Color color = colorRepository.findByColor(itemDto.getSelectedColor());
      final Size size = sizeRepository.findBySize(itemDto.getSelectedSize());
      final Item item = itemRepository.findByCod(itemDto.getId());
      final Integer quantity = itemDto.getQuantity();
      order.getOrderElements().add(new OrderElement(null, order, item, size, color, quantity));
    }
    return transaction;
  }

}
