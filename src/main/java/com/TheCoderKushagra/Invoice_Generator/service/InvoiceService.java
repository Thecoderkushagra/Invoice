package com.TheCoderKushagra.Invoice_Generator.service;

import com.TheCoderKushagra.Invoice_Generator.entity.Invoice;
import com.TheCoderKushagra.Invoice_Generator.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    public Invoice saveInvoice( Invoice invoice ) {
        return invoiceRepository.save(invoice);
    }
}
