package com.TheCoderKushagra.Invoice_Generator.controller;

import com.TheCoderKushagra.Invoice_Generator.entity.Users;
import com.TheCoderKushagra.Invoice_Generator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-changes")

public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping("/change-info")
    public ResponseEntity<Users> updateInfo(@RequestBody Users user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Users userInDb = userService.findByUserName(userName);

        if(userInDb != null){
            userInDb.setPassword(user.getPassword());
            userInDb.setUserName(user.getUserName());
            userService.saveNewUser(userInDb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteByUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        userService.deleteByUserName(authentication.getName());
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }
}
