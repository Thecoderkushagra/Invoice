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

    private String thumbnailUri;
    private String template;
    private String title;
}
