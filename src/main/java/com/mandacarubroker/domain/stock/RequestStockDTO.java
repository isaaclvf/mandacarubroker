package com.mandacarubroker.domain.stock;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RequestStockDTO(
        @Pattern(regexp = "[A-Za-z]{3}\\d", message = "Symbol must be 3 letters followed by 1 number")
        @NotBlank(message = "Stock symbol cannot be blank")
        String symbol,

        @NotBlank(message = "Company name cannot be blank")
        @Pattern(regexp = "^[a-zA-Z\\d\\s]+$", message = "Company name has invalid characters. Alphanumeric characters and white spaces only.")
        String companyName,

        @NotNull(message = "Price cannot be null")
        @Min(value = 0, message = "Price must be a positive number")
        double price
) {
}
