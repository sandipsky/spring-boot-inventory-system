package com.sandipsky.inventory_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandipsky.inventory_system.entity.User;
import com.sandipsky.inventory_system.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User saveUser(User user) {
        return repository.save(user);
    }

    public List<User> getUsers() {
        return repository.findAll();
    }

    public User getUserById(int id) {
        return repository.findById(id).get();
    }

    public User updateUser(int id, User user) {
        User existing = repository.findById(id).get();
        existing.setFullName(user.getFullName());
        return repository.save(existing);
    }

    public void deleteUser(int id) {
        repository.deleteById(id);
    }
}
