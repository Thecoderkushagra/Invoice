package com.TheCoderKushagra.Invoice_Generator.repository;

import com.TheCoderKushagra.Invoice_Generator.entity.Invoice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvoiceRepository extends MongoRepository<Invoice, String> {
}
