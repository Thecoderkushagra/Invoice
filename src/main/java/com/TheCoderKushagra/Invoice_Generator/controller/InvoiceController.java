package com.TheCoderKushagra.Invoice_Generator.controller;

import com.TheCoderKushagra.Invoice_Generator.entity.Invoice;
import com.TheCoderKushagra.Invoice_Generator.service.EmailService;
import com.TheCoderKushagra.Invoice_Generator.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/invoice")
@CrossOrigin("*")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private EmailService emailService;

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

    @PostMapping("/sendPdf")
    public ResponseEntity<String> sendPFf( @RequestParam("pdfFile") MultipartFile pdfFile,
                                           @RequestParam("recipientEmail") String recipientEmail ) {
        if (recipientEmail == null || recipientEmail.trim().isEmpty()) {
            return new ResponseEntity<>("Email address is required", HttpStatus.BAD_REQUEST);
        }
        if (pdfFile == null || pdfFile.isEmpty()) {
            return new ResponseEntity<>("PDF file is required", HttpStatus.BAD_REQUEST);
        }
        String mailBody =
                """
                <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;">
                    <h2 style="color: #333; border-bottom: 2px solid #007bff; padding-bottom: 10px;">
                        Invoice Document
                    </h2>
                    <p>Dear Valued Customer,</p>
                    <p>Please find your invoice attached to this email as a PDF document.</p>
                    <div style="background-color: #f8f9fa; padding: 15px; border-radius: 5px; margin: 20px 0;">
                    <p style="margin: 0;"><strong>Important Notes:</strong></p>
                    <ul style="margin: 10px 0;">
                        <li>Please review the invoice details carefully</li>
                        <li>Keep this document for your records</li>
                        <li>Contact us if you have any questions</li>
                    </ul>
                    </div>
                    <p>If you have any questions or concerns regarding this invoice, please don't hesitate to contact our support team.</p>
                    <br>
                    <p style="color: #666;">
                        Best regards,<br>
                        <strong>Your Invoice Team</strong><br>
                        <small>This is an automated email, please do not reply directly to this message.</small>
                    </p>
                </div>
                """;
        List<MultipartFile> listPfPdf = new ArrayList<>();
        listPfPdf.add(pdfFile);
        emailService.mailSender(recipientEmail,
                "Invoice Document - " + java.time.LocalDate.now().toString(),
                mailBody,
                true,
                listPfPdf );
        return new ResponseEntity<>("successful",HttpStatus.OK);
    }
}
