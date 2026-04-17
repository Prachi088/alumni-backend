package com.alumni.backend.controller;

import com.alumni.backend.model.User;
import com.alumni.backend.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // GET all users
    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    // POST — register a new user
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    // GET user by email — used by Login to check credentials
    @GetMapping("/by-email")
    public User getUserByEmail(@RequestParam String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    // GET single user by ID — used by Profile page
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // PUT — update profile fields (does NOT overwrite password unless explicitly provided)
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updated) {
        return userRepository.findById(id).map(user -> {
            if (updated.getName()     != null) user.setName(updated.getName());
            if (updated.getPassword() != null && !updated.getPassword().isBlank())
                user.setPassword(updated.getPassword());
            if (updated.getBatch()   != null) user.setBatch(updated.getBatch());
            if (updated.getBranch()  != null) user.setBranch(updated.getBranch());
            if (updated.getCompany() != null) user.setCompany(updated.getCompany());
            if (updated.getSkills()  != null) user.setSkills(updated.getSkills());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found: " + id));
    }
}
