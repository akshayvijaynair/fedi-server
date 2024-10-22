package com.PaceUniversity.Capstone.service;

import com.PaceUniversity.Capstone.model.User;
import com.PaceUniversity.Capstone.repository.UserRepository;
import com.PaceUniversity.Capstone.repository.User_Repository;

import java.util.List;

public class UserService implements User_Service {


    private User_Repository repository ;

    @Override
    public List<User> findAllUsers(){
        return repository.findAllUsers();
    }

    public void setRepository(User_Repository repository) {
        this.repository = repository;
    }
}
