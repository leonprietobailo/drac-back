package com.leonbros.drac.dto.validation;



import com.leonbros.drac.dto.request.AddressRegistrationRequest;
import com.leonbros.drac.dto.request.UserRegistrationRequest;
import com.leonbros.drac.dto.validation.annotations.AddressGroup;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AddressGroupValidation
    implements ConstraintValidator<AddressGroup, AddressRegistrationRequest> {

  @Override
  public boolean isValid(AddressRegistrationRequest address,
      ConstraintValidatorContext constraintValidatorContext) {
    final String city = address.getCity();
    final String province = address.getProvince();
    final String postalCode = address.getPostalCode();
    final String streetNumber = address.getStreetNumber();
    if (isEmptyAddress(address)) {
      return true;
    } else
      return !StringUtils.isEmpty(city) && !StringUtils.isEmpty(province) && !StringUtils.isEmpty(
          postalCode) && !StringUtils.isEmpty(streetNumber) && postalCode.length() == 5;
  }

  public static boolean isEmptyAddress(AddressRegistrationRequest address) {
    return StringUtils.isEmpty(address.getCity()) && StringUtils.isEmpty(
        address.getProvince()) && StringUtils.isEmpty(
        address.getStreetNumber()) && StringUtils.isEmpty(address.getPostalCode());
  }
}
