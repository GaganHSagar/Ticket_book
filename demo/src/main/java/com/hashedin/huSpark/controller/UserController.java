package com.hashedin.huSpark.controller;

import com.hashedin.huSpark.model.Jwt.JwtRequest;
import com.hashedin.huSpark.model.Jwt.JwtResponse;
import com.hashedin.huSpark.model.User;
import com.hashedin.huSpark.model.UserRole;
import com.hashedin.huSpark.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.hashedin.huSpark.utils.JwtUtils;
import com.hashedin.huSpark.service.UserAuthDetailService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserAuthDetailService userAuthDetailService;

    // User CRUD Endpoints
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user.setUserRole(UserRole.USER); // Set the user role to USER
        return ResponseEntity.ok(userService.addUser(user));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userService.getUserById(id);
        return userOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword()); // Assuming you don't want to update the password here
            user.setBirthDay(userDetails.getBirthDay());
            return ResponseEntity.ok(userService.updateUser(id, user));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isPresent()) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // Admin CRUD Endpoints

    @PostMapping("/admin")
    public ResponseEntity<User> createAdmin(@RequestBody User admin) {
        admin.setUserRole(UserRole.ADMIN); // Set the user role to ADMIN
        return ResponseEntity.ok(userService.addUser(admin));
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<User> getAdminById(@PathVariable Long id) {
        Optional<User> adminOptional = userService.getUserById(id);
        if (adminOptional.isPresent() && adminOptional.get().getUserRole() == UserRole.ADMIN) {
            return ResponseEntity.ok(adminOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<User> updateAdmin(@PathVariable Long id, @RequestBody User adminDetails) {
        Optional<User> adminOptional = userService.getUserById(id);
        if (adminOptional.isPresent() && adminOptional.get().getUserRole() == UserRole.ADMIN) {
            adminDetails.setUserRole(UserRole.ADMIN); // Ensure the user role remains ADMIN
            return ResponseEntity.ok(userService.updateUser(id, adminDetails));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        Optional<User> adminOptional = userService.getUserById(id);
        if (adminOptional.isPresent() && adminOptional.get().getUserRole() == UserRole.ADMIN) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateEmployee(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        final UserDetails userDetails = userAuthDetailService.loadUserByUsername(authenticationRequest.getEmail());
        final String token = jwtUtils.generateJwtToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        } catch (Exception e) {
            throw new Exception("Authentication failed", e);
        }
    }
}

