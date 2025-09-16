package com.TheCoderKushagra.Invoice_Generator.service;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void mailSender(String to, String subject, String body,
                           boolean isHtml, List<MultipartFile> attachments
    ){
        try {
            MimeMessage message = mailSender.createMimeMessage();
            // true -> allows multipart (attachments)
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, isHtml);

            // Add attachments if provided
            if (attachments != null && !attachments.isEmpty()) {
                for (MultipartFile file : attachments) {
                    if (file != null) {
                        helper.addAttachment(file.getName(), file);
                    }
                }
            }
            mailSender.send(message);
            log.info("Mail sent successfully");
        } catch (Exception e) {
            log.error("Error while sending mail",e);
        }
    }

}
