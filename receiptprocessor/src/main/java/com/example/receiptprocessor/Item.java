package com.example.receiptprocessor;

import lombok.Data;
import jakarta.validation.constraints.*;
@Data
class Item {
    @NotBlank(message = "Item description is required.")
    @Pattern(regexp = "^[\\w\\s\\-]+$", message = "Item description contains invalid characters.")
    private String shortDescription;

    @NotBlank(message = "Item price is required.")
    @Pattern(regexp = "^\\d+\\.\\d{2}$", message = "Price must be in XX.XX format.")
    private String price;

    public String getShortDescription() {
        return shortDescription;
    }
    public String getPrice() {
        return price;
    }
}