package com.example.TodoList.Controller;

import com.example.TodoList.Dto.Register;
import com.example.TodoList.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid Register register) {
        String response = userService.registerUser(register.toUser());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser() throws Exception{
        try {
            return ResponseEntity.ok("Successfully Loged in");
    
        } catch (Exception e) {
            throw new UsernameNotFoundException("User is not authorised");
        }
    }

}
