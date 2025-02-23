package com.example.receiptprocessor;

import jakarta.validation.Valid;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.util.List;

@Data
class Receipt {
    @NotBlank(message = "Retailer name is required.")
    @Pattern(regexp = "^[\\w\\s\\-&]+$", message = "Retailer contains invalid characters.")
    private String retailer;

    @NotBlank(message = "Purchase date is required.")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Invalid date format.")
    private String purchaseDate;

    @NotBlank(message = "Purchase time is required.")
    @Pattern(regexp = "\\d{2}:\\d{2}", message = "Invalid time format.")
    private String purchaseTime;

    @NotEmpty(message = "At least one item is required.")
    private List<@Valid Item> items;

    @NotBlank(message = "Total amount is required.")
    @Pattern(regexp = "^\\d+\\.\\d{2}$", message = "Total must be in XX.XX format.")
    private String total;

    public String getRetailer() {
        return retailer;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public List<Item> getItems() {
        return items;
    }

    public String getTotal() {
        return total;
    }
}