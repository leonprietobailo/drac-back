package com.leonbros.drac.service.user;

import com.leonbros.drac.dto.response.user.AddressDto;
import com.leonbros.drac.dto.response.user.BillingInfoDto;
import com.leonbros.drac.dto.response.user.RecipientDto;
import com.leonbros.drac.dto.response.user.ShippingResponse;
import com.leonbros.drac.repository.AddressRepository;
import com.leonbros.drac.repository.BillingInfoRepository;
import com.leonbros.drac.repository.RecipientRepository;
import com.leonbros.drac.security.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CheckoutService {

  private final RecipientRepository recipientRepository;
  private final AddressRepository addressRepository;
  private final BillingInfoRepository billingInfoRepository;

  @Autowired
  public CheckoutService(RecipientRepository recipientRepository, AddressRepository addressRepository,
      BillingInfoRepository billingInfoRepository) {
    this.recipientRepository = recipientRepository;
    this.addressRepository = addressRepository;
    this.billingInfoRepository = billingInfoRepository;
  }

  public ShippingResponse shippingResponse() {
    final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (!AuthUtils.isUserAuthenticated(auth)) {
      return new ShippingResponse(ShippingResponse.Status.UNAUTHORIZED, Collections.emptyList(),
          Collections.emptyList(), Collections.emptyList());
    }
    return new ShippingResponse(ShippingResponse.Status.SUCCESS, computeAddressDto(auth),
        computeBillingInfoDto(auth), computeRecipientDto(auth));
  }

  private List<AddressDto> computeAddressDto(Authentication auth) {
    return addressRepository.getAddressesByUserCod_Email(auth.getName()).stream().map(
        address -> new AddressDto(address.getCod(), address.getCity(), address.getProvince(),
            address.getStreet(), address.getFlat(), address.getPostalCode(), false)).toList();
  }

  private List<RecipientDto> computeRecipientDto(Authentication auth) {
    return recipientRepository.getRecipientsByUserCod_Email(auth.getName()).stream().map(
        recipient -> new RecipientDto(recipient.getCod(), recipient.getFirstname(),
            recipient.getSurname(), recipient.getEmail(), recipient.getTelephone(),
            recipient.getStarred())).toList();
  }

  private List<BillingInfoDto> computeBillingInfoDto(Authentication auth) {
    return billingInfoRepository.getBillingInfoByUserCod_Email(auth.getName()).stream().map(
        billingInfo -> new BillingInfoDto(billingInfo.getCod(), billingInfo.getEntityName(),
            billingInfo.getEmail(), billingInfo.getTaxId(), billingInfo.getStarred())).toList();
  }


}
