package com.example.TodoList.Service;

import com.example.TodoList.Dao.UserRepo;
import com.example.TodoList.Model.Role;
import com.example.TodoList.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String registerUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return "User Registered Successfully";
    }
    
}
