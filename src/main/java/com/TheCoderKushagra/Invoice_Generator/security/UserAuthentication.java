package com.TheCoderKushagra.Invoice_Generator.security;

import com.TheCoderKushagra.Invoice_Generator.entity.Users;
import com.TheCoderKushagra.Invoice_Generator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserAuthentication implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUserName(username);
        if(user != null){
            return User.builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .build();
        }
        throw new UsernameNotFoundException("User not fund with username " + username);
    }
}
