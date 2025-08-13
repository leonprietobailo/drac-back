package com.leonbros.drac.service.user;

import com.leonbros.drac.dto.response.user.AddressDto;
import com.leonbros.drac.dto.response.user.AddressResponse;
import com.leonbros.drac.dto.response.user.BillingInfoDto;
import com.leonbros.drac.dto.response.user.RecipientDto;
import com.leonbros.drac.dto.response.user.RecipientResponse;
import com.leonbros.drac.dto.response.user.ShippingResponse;
import com.leonbros.drac.entity.user.Address;
import com.leonbros.drac.entity.user.Recipient;
import com.leonbros.drac.entity.user.User;
import com.leonbros.drac.repository.AddressRepository;
import com.leonbros.drac.repository.BillingInfoRepository;
import com.leonbros.drac.repository.RecipientRepository;
import com.leonbros.drac.repository.UserRepository;
import com.leonbros.drac.security.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CheckoutService {

  private final UserRepository userRepository;
  private final RecipientRepository recipientRepository;
  private final AddressRepository addressRepository;
  private final BillingInfoRepository billingInfoRepository;

  @Autowired
  public CheckoutService(UserRepository userRepository, RecipientRepository recipientRepository,
      AddressRepository addressRepository, BillingInfoRepository billingInfoRepository) {
    this.userRepository = userRepository;
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

  @Transactional
  public RecipientResponse addRecipient(RecipientDto recipientDto) {
    final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (!AuthUtils.isUserAuthenticated(auth)) {
      return new RecipientResponse(RecipientResponse.Status.UNAUTHORIZED, null);
    }
    final User user = userRepository.getUserByEmail(auth.getName()).orElseThrow();
    final Recipient recipient =
        new Recipient(null, user, recipientDto.getName(), recipientDto.getSurname(),
        recipientDto.getPhone(), false);
    final Recipient persistedRecipient = recipientRepository.save(recipient);
    return new RecipientResponse(RecipientResponse.Status.SUCCESS,
        new RecipientDto(persistedRecipient.getCod(), persistedRecipient.getFirstname(),
            persistedRecipient.getSurname(),
            persistedRecipient.getTelephone(), persistedRecipient.getStarred()));
  }

  @Transactional
  public AddressResponse addAddress(AddressDto addressDto) {
    final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (!AuthUtils.isUserAuthenticated(auth)) {
      return new AddressResponse(AddressResponse.Status.UNAUTHORIZED, null);
    }
    final User user = userRepository.getUserByEmail(auth.getName()).orElseThrow();
    final Address address = new Address(null, user, addressDto.getCity(), addressDto.getProvince(),
        addressDto.getStreet(), addressDto.getFlat(), addressDto.getZip());
    final Address persistedAddress = addressRepository.save(address);
    addressDto.setId(persistedAddress.getCod());
    return new AddressResponse(AddressResponse.Status.SUCCESS, addressDto);
  }

  private List<AddressDto> computeAddressDto(Authentication auth) {
    return addressRepository.getAddressesByUserCod_Email(auth.getName()).stream().map(
        address -> new AddressDto(address.getCod(), address.getCity(), address.getProvince(),
            address.getStreet(), address.getFlat(), address.getPostalCode(), false)).toList();
  }

  private List<RecipientDto> computeRecipientDto(Authentication auth) {
    return recipientRepository.getRecipientsByUserCod_Email(auth.getName()).stream().map(
        recipient -> new RecipientDto(recipient.getCod(), recipient.getFirstname(),
            recipient.getSurname(),  recipient.getTelephone(),
            recipient.getStarred())).toList();
  }

  private List<BillingInfoDto> computeBillingInfoDto(Authentication auth) {
    return billingInfoRepository.getBillingInfoByUserCod_Email(auth.getName()).stream().map(
        billingInfo -> new BillingInfoDto(billingInfo.getCod(), billingInfo.getEntityName(),
            billingInfo.getEmail(), billingInfo.getTaxId(), billingInfo.getStarred())).toList();
  }


}
