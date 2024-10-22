package com.PaceUniversity.Capstone.repository;

import com.PaceUniversity.Capstone.model.User;

import java.util.List;

public interface User_Repository {
    List<User> findAllUsers();
}
