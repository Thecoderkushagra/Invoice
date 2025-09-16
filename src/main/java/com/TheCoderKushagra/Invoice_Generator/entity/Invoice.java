package com.TheCoderKushagra.Invoice_Generator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Invoices")
public class Invoice {
    @Id
    private String id;

    private Company company;
    private Billing billing;
    private Shipping shipping;
    private InvoiceDetail invoice;
    private List<Item> items;

    private String notes;
    private String logo;
    private double tax;

    @CreatedDate
    private Instant createdAt;
    @LastModifiedBy
    private Instant lastUpdatedAt;

    private String thumbnailUrl;
    private String template;
    private String title;

    // Required classes
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Company {
        private String name;
        private String phone;
        private String address;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Billing {
        private String name;
        private String phone;
        private String address;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Shipping {
        private String name;
        private String phone;
        private String address;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class InvoiceDetail {
        private String number;
        private String data;
        private String dueDate;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Item {
        private String name;
        private int qty;
        private double amount;
        private String description;
    }
}
