package com.leonbros.drac.dto.external.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

public record CustomerDto(String name, String email, String phone) {
}
