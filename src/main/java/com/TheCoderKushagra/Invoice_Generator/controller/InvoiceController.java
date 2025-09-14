package com.TheCoderKushagra.Invoice_Generator.controller;

import com.TheCoderKushagra.Invoice_Generator.entity.Invoice;
import com.TheCoderKushagra.Invoice_Generator.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoice")
@CrossOrigin("*")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/saveInvoice")
    public ResponseEntity<Invoice> callSaveInvoice( @RequestBody Invoice invoice ) {
        Invoice invoices = invoiceService.saveInvoice(invoice);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/seeInvoices")
    public ResponseEntity<List<Invoice>> callFetchInvoice() {
        List<Invoice> invoices = invoiceService.fetchAll();
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @DeleteMapping("/{invoiceId}")
    public ResponseEntity<?> callDeleteInvoice( @PathVariable String invoiceId ) {
        invoiceService.deleteInvoice(invoiceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
