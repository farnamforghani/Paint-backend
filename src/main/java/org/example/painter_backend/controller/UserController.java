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
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            if (username == null || username.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Username is required"));
            }

            User user = userService.signUpUser(username.trim());
            return ResponseEntity.ok(Map.of(
                    "message", "User signed up successfully",
                    "user", user
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/check/{username}")
    public ResponseEntity<Map<String, Boolean>> checkUser(@PathVariable String username) {
        boolean exists = userService.userExists(username);
        return ResponseEntity.ok(Map.of("exists", exists));
    }
}
