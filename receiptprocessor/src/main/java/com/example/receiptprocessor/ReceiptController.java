package com.example.receiptprocessor;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {
    private final Map<String, Integer> receiptPointStore = new ConcurrentHashMap<>();

    // Endpoint: Process Receipts
    @PostMapping("/process")
    public ResponseEntity<Map<String, String>> processReceipt(@Valid @RequestBody Receipt receipt) {
        String id = UUID.randomUUID().toString();
        int points = calculatePoints(receipt);
        receiptPointStore.put(id, points);
        return ResponseEntity.ok(Collections.singletonMap("id", id));
    }


    // Endpoint: Get Points
    @GetMapping("/{id}/points")
    public ResponseEntity<Map<String, Integer>> getPoints(@PathVariable String id) {
        Integer points = receiptPointStore.get(id);
        if (points == null) {
            throw new ReceiptNotFoundException(id);
        }
        return ResponseEntity.ok(Collections.singletonMap("points", points));
    }

    private int calculatePoints(Receipt receipt) {
        int points = receipt.getRetailer().replaceAll("[^a-zA-Z0-9]", "").length();
        double total = Double.parseDouble(receipt.getTotal());
        if (total % 1 == 0) {
            points += 50;
        }
        if (total % 0.25 == 0) {
            points += 25;
        }
        points += (receipt.getItems().size() / 2) * 5;
        for (Item item : receipt.getItems()) {
            if (item.getShortDescription().trim().length() % 3 == 0) {
                points += (int) Math.ceil(Double.parseDouble(item.getPrice()) * 0.2);
            }
        }
        String[] dateParts = receipt.getPurchaseDate().split("-");
        if (Integer.parseInt(dateParts[2]) % 2 != 0) {
            points += 6;
        }
        String[] timeParts = receipt.getPurchaseTime().split(":");
        int hour = Integer.parseInt(timeParts[0]);
        if (hour >= 14 && hour < 16) {
            points += 10;
        }
        return points;
    }
}

@ResponseStatus(value = org.springframework.http.HttpStatus.NOT_FOUND)
class ReceiptNotFoundException extends RuntimeException {
    public ReceiptNotFoundException(String id) {
        super("No receipt found for ID: " + id);
    }
}
