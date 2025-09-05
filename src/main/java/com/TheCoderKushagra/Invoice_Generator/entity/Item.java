package com.TheCoderKushagra.Invoice_Generator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private String name;
    private int qty;
    private double amount;
    private String description;
}
