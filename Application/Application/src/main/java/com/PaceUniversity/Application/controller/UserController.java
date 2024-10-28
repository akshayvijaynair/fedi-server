package com.PaceUniversity.Application.controller;


import com.PaceUniversity.Application.dto.UserRegistrationDTO;
import com.PaceUniversity.Application.exception.UserAlreadyExistException;
import com.PaceUniversity.Application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {



    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDTO registrationDTO){
        try{
            userService.registerUser(registrationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Congradulations on signing up.User has successfully been registered !");
//            return ResponseEntity.ok("Congradulations on signing up.User has successfully been registered !");
        }catch (UserAlreadyExistException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("AN Error occured during registration.");
        }
    }

    @PutMapping("/deactivate/userId")
    public ResponseEntity<String> deactivateUser(@PathVariable Long userid){
        try{
            userService.deactivateUser(userid);
            return ResponseEntity.ok("user deactivation successfully");
        } catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
