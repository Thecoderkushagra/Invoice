package com.TheCoderKushagra.Invoice_Generator.controller;

import com.TheCoderKushagra.Invoice_Generator.RedisCache.OtpCache;
import com.TheCoderKushagra.Invoice_Generator.RedisCache.UserCache;
import com.TheCoderKushagra.Invoice_Generator.entity.Users;
import com.TheCoderKushagra.Invoice_Generator.security.JwtUtil;
import com.TheCoderKushagra.Invoice_Generator.security.UserAuthentication;
import com.TheCoderKushagra.Invoice_Generator.service.EmailService;
import com.TheCoderKushagra.Invoice_Generator.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/public")
@CrossOrigin("*")
public class PublicController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private OtpCache otpRedis;
    @Autowired
    private UserCache userRedis;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserAuthentication userAuthentication;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<String> SignUp(@RequestBody Users users){
        try {
            String generatedOtp = userService.generateSixDigitNumber();
            String userName = users.getUserName();
            otpRedis.set(userName, generatedOtp, 600);
            userRedis.set("bringUserData", users, 600);
            // Sending male
            String email = users.getEmail();
            String mailBody = String.format("""   
                <div style="max-width:600px;margin:0 auto;background:#ffffff;border-radius:8px;padding:30px;box-shadow:0 4px 10px rgba(0,0,0,0.1)">
                    <h2 style="color:#6e8efb;margin-top:0">OTP Verification</h2>
                    <p>Dear User,</p>
                    <p>Your One-Time Password (OTP) is: <strong style="font-size:20px;color:#6e8efb">%s</strong></p>
                    <p style="background-color:#f8f9fa;padding:10px;border-left:4px solid #6e8efb;border-radius:4px">This OTP is valid for only 5 minutes.</p>
                    <p style="color:#ff6b6b">Please do not share this OTP with anyone.</p>
                    <p>Best regards,<br><strong style="color:#6e8efb">Team Vixify</strong></p>
                </div>
                """, generatedOtp);
            emailService.mailSender(email, "EMAIL OTP FOR VARIFICATION", mailBody,
                    true, null);
            return new ResponseEntity<>("Male sent successfully", HttpStatus.OK);
        }catch (Exception e){
            log.error("Error occur in public-SignUp",e);
            return new ResponseEntity<>("Unable to send mail.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/varify-otp")
    public ResponseEntity<?> otpVarify(@RequestParam("Name") String name,
                                       @RequestParam("OTP") String otp){
        try {
            String redisOtp = otpRedis.get(name);
            assert redisOtp != null;
            if (otp.equals(redisOtp)){
                Users users = userRedis.get("bringUserData", Users.class);
                userService.saveNewUser(users);
                return new ResponseEntity<>("SingUp Successfully", HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("SingUp Fail", HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Some error:", e);
            return new ResponseEntity<>("unable to save user data", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users user) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUserName(),
                            user.getPassword())
            );
            UserDetails userDetails = userAuthentication.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            Map<String, String> response = new HashMap<>();
            response.put("token", jwt);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("error",e);
            return new ResponseEntity<>("Unable to SngIn: Incorrect Username or Password", HttpStatus.BAD_REQUEST);
        }
    }

}
