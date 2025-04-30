package com.sandipsky.inventory_system.controller;

import com.sandipsky.inventory_system.dto.ApiResponse;
import com.sandipsky.inventory_system.dto.UserDTO;
import com.sandipsky.inventory_system.dto.filter.RequestDTO;
import com.sandipsky.inventory_system.entity.User;
import com.sandipsky.inventory_system.service.UserService;
import com.sandipsky.inventory_system.util.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping()
    public List<UserDTO> getUsers() {
        return service.getUsers();
    }

    @PostMapping("/view")
    public Page<UserDTO> getPaginatedUsersList(@RequestBody RequestDTO request) {
        return service.getPaginatedUsersList(request);
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable int id) {
        return service.getUserById(id);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody UserDTO user) {
        User res = service.saveUser(user);
        return ResponseEntity.ok(ResponseUtil.success(res.getId(), "User created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable int id, @RequestBody UserDTO user) {
        User res = service.updateUser(id, user);
        return ResponseEntity.ok(ResponseUtil.success(res.getId(), "User updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> deleteUser(@PathVariable int id) {
        service.deleteUser(id);
        return ResponseEntity.ok(ResponseUtil.success(id, "User deleted successfully"));
    }
}
