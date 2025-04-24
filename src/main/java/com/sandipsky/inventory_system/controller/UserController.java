package com.sandipsky.inventory_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sandipsky.inventory_system.entity.User;
import com.sandipsky.inventory_system.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping()
    public List<User> getUsers() {
        return service.getUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        return service.getUserById(id);
    }

    @PostMapping()
    public User createUser(@RequestBody User user) {
        return service.saveUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User user) {
        return service.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        service.deleteUser(id);
    }
}
