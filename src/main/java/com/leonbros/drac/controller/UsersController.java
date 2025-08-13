package com.leonbros.drac.controller;

import com.leonbros.drac.dto.response.user.AddressDto;
import com.leonbros.drac.dto.response.user.AddressResponse;
import com.leonbros.drac.dto.response.user.RecipientDto;
import com.leonbros.drac.dto.response.user.RecipientResponse;
import com.leonbros.drac.dto.response.user.ShippingResponse;
import com.leonbros.drac.service.user.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UsersController {

  private final CheckoutService checkoutService;

  @Autowired
  public UsersController(CheckoutService checkoutService) {
    this.checkoutService = checkoutService;
  }

  @GetMapping(value = "/shipment", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ShippingResponse> getShipmentInfo() {
    return ResponseEntity.ok(checkoutService.shippingResponse());
  }

  @PostMapping(value = "/recipient")
  public ResponseEntity<RecipientResponse> addRecipient(@RequestBody RecipientDto recipientDto) {
    return ResponseEntity.ok(checkoutService.addRecipient(recipientDto));
  }

  @PostMapping(value = "/address")
  public ResponseEntity<AddressResponse> addAddress(@RequestBody AddressDto addressDto) {
    return ResponseEntity.ok(checkoutService.addAddress(addressDto));
  }

}
