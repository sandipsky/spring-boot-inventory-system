package com.sandipsky.inventory_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sandipsky.inventory_system.dto.UserDTO;
import com.sandipsky.inventory_system.dto.filter.RequestDTO;
import com.sandipsky.inventory_system.entity.User;
import com.sandipsky.inventory_system.exception.DuplicateResourceException;
import com.sandipsky.inventory_system.exception.ResourceNotFoundException;
import com.sandipsky.inventory_system.repository.UserRepository;
import com.sandipsky.inventory_system.util.SpecificationBuilder;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    // @Autowired
    // private PasswordEncoder passwordEncoder;

    private final SpecificationBuilder<User> specBuilder = new SpecificationBuilder<>();

    @Transactional
    public User saveUser(UserDTO dto) {
        if (repository.existsByUsername(dto.getUsername())) {
            throw new DuplicateResourceException("Username already exists");
        }
        if (repository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        User user = new User();
        mapDtoToEntity(dto, user);
        return repository.save(user);
    }

    public Page<UserDTO> getPaginatedUsersList(RequestDTO request) {
        Pageable pageable = PageRequest.of(
                request.getPagination() != null ? request.getPagination().getPageIndex() : 0,
                request.getPagination() != null ? request.getPagination().getPageSize() : 25,
                specBuilder.buildSort(request.getSortDTO()));

        Specification<User> spec = specBuilder.buildSpecification(request.getFilter());
        Page<User> productPage = repository.findAll(spec, pageable);
        return productPage.map(this::mapToDTO);
    }

    public List<UserDTO> getUsers() {
        return repository.findAll().stream().map(this::mapToDTO).toList();
    }

    public UserDTO getUserById(int id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return mapToDTO(user);
    }

    public User updateUser(int id, UserDTO dto) {
        User existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (repository.existsByUsernameAndIdNot(dto.getUsername(), id)) {
            throw new DuplicateResourceException("Username already exists");
        }

        if (repository.existsByEmailAndIdNot(dto.getEmail(), id)) {
            throw new DuplicateResourceException("Email already exists");
        }

        mapDtoToEntity(dto, existing);
        return repository.save(existing);
    }

    public void deleteUser(int id) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        repository.deleteById(id);
    }

    private UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPassword("");
        dto.setGender(user.getGender());
        dto.setContact(user.getContact());
        dto.setActive(user.isActive());
        return dto;
    }

    private void mapDtoToEntity(UserDTO dto, User user) {
        user.setUsername(dto.getUsername().trim());
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail().trim());
        // String hashedPassword = passwordEncoder.encode(dto.getPassword());
        // user.setPassword(hashedPassword);
        user.setGender(dto.getGender());
        user.setContact(dto.getContact());
        user.setActive(dto.isActive());
    }
}
