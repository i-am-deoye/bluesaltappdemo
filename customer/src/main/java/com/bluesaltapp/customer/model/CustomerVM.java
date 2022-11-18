package com.bluesaltapp.customer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public record CustomerVM(
        String firstName,
        String lastName,
        String email
) {
}
