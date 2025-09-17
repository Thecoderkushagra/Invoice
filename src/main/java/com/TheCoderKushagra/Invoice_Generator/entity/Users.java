package com.TheCoderKushagra.Invoice_Generator.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    private String id;
    @Indexed(unique = true)
    @NonNull
    private String userName;
    @NonNull
    private String email;
    @NonNull
    private String password;
    @DBRef
    private List<Invoice> MyInvoices = new ArrayList<>();
}
