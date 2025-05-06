package com.sandipsky.inventory_system.controller;

import com.sandipsky.inventory_system.dto.ApiResponse;
import com.sandipsky.inventory_system.dto.UserDTO;
import com.sandipsky.inventory_system.dto.filter.RequestDTO;
import com.sandipsky.inventory_system.entity.User;
import com.sandipsky.inventory_system.service.UserService;
import com.sandipsky.inventory_system.util.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<User>> createUser(@RequestPart("user") UserDTO user,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        User res = service.saveUser(user, imageFile);
        return ResponseEntity.ok(ResponseUtil.success(res.getId(), "User created successfully"));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable int id, @RequestPart("user") UserDTO user,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        User res = service.updateUser(id, user, imageFile);
        return ResponseEntity.ok(ResponseUtil.success(res.getId(), "User updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> deleteUser(@PathVariable int id) {
        service.deleteUser(id);
        return ResponseEntity.ok(ResponseUtil.success(id, "User deleted successfully"));
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<Resource> getUserImageFile(@PathVariable int id) {
        Resource imageFile = service.getUserImageFileById(id);

        return ResponseEntity.ok()
                .body(imageFile);
    }
}
