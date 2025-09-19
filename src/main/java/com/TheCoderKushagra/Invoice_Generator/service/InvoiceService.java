package com.TheCoderKushagra.Invoice_Generator.service;

import com.TheCoderKushagra.Invoice_Generator.entity.Invoice;
import com.TheCoderKushagra.Invoice_Generator.entity.Users;
import com.TheCoderKushagra.Invoice_Generator.repository.InvoiceRepository;
import com.TheCoderKushagra.Invoice_Generator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private UserRepository userRepository;

    public void saveInvoice( Invoice invoice, String username ) {
        Users user = userRepository.findByUserName(username);
        Invoice savedInvoice = invoiceRepository.save(invoice);
        user.getMyInvoices().add(savedInvoice);
        userRepository.save(user);
    }

    public List<Invoice> fetchAll(String username) {
        Users user = userRepository.findByUserName(username);
        return user.getMyInvoices();
    }
    
    public boolean deleteInvoice( String invoiceId, String username ) {
        boolean remove = false;
        try{
            Users UserName = userRepository.findByUserName(username);
            remove = UserName.getMyInvoices().removeIf(x -> x.getId().equals(invoiceId));
            if (remove) {
                userRepository.save(UserName);
                invoiceRepository.deleteById(invoiceId);
            }
            return remove;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
