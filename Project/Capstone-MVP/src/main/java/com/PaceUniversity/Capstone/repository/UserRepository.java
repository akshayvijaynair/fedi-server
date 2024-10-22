package com.PaceUniversity.Capstone.repository;

import com.PaceUniversity.Capstone.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserRepository implements User_Repository {


    @Override
    public List<User> findAllUsers(){
        List<User> userList = new ArrayList<>();

        User user = new User();
        user.setUsername("User1");
        user.setFirstname("Jane");
        user.setLastname("Doe");
        user.setEmail("emali@email.com");

        Date dob = new Date(01,00,0000);
        user.setDob(dob);
        user.setBio("This is my first every Social Media profile");

        userList.add(user);
        
        return userList;
    }




}
