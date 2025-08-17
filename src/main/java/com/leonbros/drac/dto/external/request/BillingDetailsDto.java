package com.leonbros.drac.dto.external.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

public record BillingDetailsDto(String name, AddressDto address) {
}
