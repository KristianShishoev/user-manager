package com.example.usermanager.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.usermanager.entity.User;

@Service
public class UserService {
	
    @Autowired
    private UserRepository dao;
    
    public Page<User> findPaginated(int page, int size) {
        return dao.findAll(PageRequest.of(page, size));
    }
}
