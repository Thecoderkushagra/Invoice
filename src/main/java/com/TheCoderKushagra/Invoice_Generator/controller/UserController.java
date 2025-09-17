package com.TheCoderKushagra.Invoice_Generator.controller;

import com.TheCoderKushagra.Invoice_Generator.entity.Users;
import com.TheCoderKushagra.Invoice_Generator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userCommands")
public class UserController {
    @Autowired
    private UserService userService;



}
