package com.TheCoderKushagra.Invoice_Generator.service;

import com.TheCoderKushagra.Invoice_Generator.entity.Users;
import com.TheCoderKushagra.Invoice_Generator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // USER Methods
    public void saveNewUser(Users users){
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        userRepository.save(users);
    }

    public Users findByUserName(String user){
        return userRepository.findByUserName(user);
    }

    // Otp Generator
    public String generateSixDigitNumber() {
        String uuidDigitsOnly = UUID.randomUUID().toString().replaceAll("[^0-9]", "");
        // Ensure we have at least 6 digits
        while (uuidDigitsOnly.length() < 6) {
            uuidDigitsOnly += UUID.randomUUID().toString().replaceAll("[^0-9]", "");
        }
        // Extract the first 6 digits
        return uuidDigitsOnly.substring(0, 6);
    }
}
