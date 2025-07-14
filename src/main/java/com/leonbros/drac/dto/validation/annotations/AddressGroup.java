package com.leonbros.drac.dto.validation.annotations;

import com.leonbros.drac.dto.validation.AddressGroupValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target( { ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AddressGroupValidation.class)
public @interface AddressGroup {
  String message() default "Address is not valid.";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
