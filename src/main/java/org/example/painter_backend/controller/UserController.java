package org.example.painter_backend.controller;

import org.example.painter_backend.model.User;
import org.example.painter_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/check/{username}")
    public ResponseEntity<Map<String, Boolean>> checkUser(@PathVariable String username) {
        boolean exists = userService.userExists(username);
        return ResponseEntity.ok(Map.of("exists", exists));
    }
}
