package org.financetracker.Controller;

import org.financetracker.Modal.Dto.LoginRequest;
import org.financetracker.Modal.Dto.LoginResponse;
import org.financetracker.Modal.User;
import org.financetracker.Security.Jwt.JwtUtil;
import org.financetracker.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        Optional<User> optionalUser = userService.findById(id);
        return optionalUser.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User user) {
        Optional<User> optionalUser = userService.findById(id);
        if (optionalUser.isPresent()) {
            User updatedUser = optionalUser.get();
            updatedUser.setUsername(user.getUsername());
            updatedUser.setPassword(user.getPassword());
            userService.updateUser(updatedUser, id);
            return ResponseEntity.ok(updatedUser);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // Check if username is provided
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Check if user already exists
        Optional<User> existingUser = userService.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        // Set default role to USER if not provided
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }

        // Encrypt the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the user
        User newUser = userService.saveUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable UUID id) {
        Optional<User> optionalUser = userService.findById(id);
        if (optionalUser.isPresent()) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String rawPassword = loginRequest.getPassword();

        try {
            User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
                String token = jwtUtil.generateToken(username);
                LoginResponse loginResponse = new LoginResponse(user.getId(), token);
                return ResponseEntity.ok(loginResponse);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(null, "Invalid username or password"));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(null, "Invalid username or password"));
        }
    }

}