package com.PaceUniversity.Application.service;


import com.PaceUniversity.Application.dto.UserRegistrationDTO;
import com.PaceUniversity.Application.entity.User;
import com.PaceUniversity.Application.exception.UserAlreadyExistException;
import com.PaceUniversity.Application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

   @Transactional
    public  User registerUser(UserRegistrationDTO registrationDTO) {
        if (userRepository.findBuserNAme(registrationDTO.getUserName()).isPresent()) {
            throw new UserAlreadyExistException("Username Already exists");
        }
               if( userRepository.findByemail(registrationDTO.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("Email already exists");
        }

        User user = new User();
        user.setUsername("user1");
        user.setEmail("email@email.com");
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        user.setActive(true);


        return userRepository.save(user);



    }

    public void deactivateUser(Long userId){
       User user = userRepository.findById(userId)
               .orElseThrow(() -> new RuntimeException("User not found"));
user.setActive(false);
userRepository.save(user);
    }





}
