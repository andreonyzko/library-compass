package com.andre.librarycompass.service;

import com.andre.librarycompass.entity.User;
import com.andre.librarycompass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService<User> {

    @Autowired
    public UserService(UserRepository userRepository) {
        super(userRepository);
    }

}
