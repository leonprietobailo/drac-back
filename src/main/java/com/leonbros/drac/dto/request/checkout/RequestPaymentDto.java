package com.leonbros.drac.dto.request.checkout;

import com.leonbros.drac.dto.response.cart.CartDto;
import com.leonbros.drac.dto.response.user.AddressDto;
import com.leonbros.drac.dto.response.user.BillingInfoDto;
import com.leonbros.drac.dto.response.user.RecipientDto;
import lombok.Getter;

@Getter
public class RequestPaymentDto {

  // SHIPMENT
  private ShipmentTypes type;
  private RecipientDto recipient;
  private AddressDto address;

  // BILLING
  private boolean requestBilling;
  private BillingInfoDto billingInfo;
  private AddressDto billingAddress;

  // CART
  private CartDto cart;

  public enum ShipmentTypes {
    POINT, DIRECTION;
  }
}
