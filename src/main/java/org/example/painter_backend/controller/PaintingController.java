package org.example.painter_backend.controller;

import org.example.painter_backend.dto.PaintingDto;
import org.example.painter_backend.model.User;
import org.example.painter_backend.service.PaintingService;
import org.example.painter_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/paintings")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class PaintingController {

    @Autowired
    private PaintingService paintingService;

    @Autowired
    private UserService userService;

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUserPaintings(@PathVariable String username) {
        Optional<User> user = userService.getUserByUsername(username);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "User not found: " + username));
        }

        List<PaintingDto> paintings = paintingService.getUserPaintings(user.get());
        return ResponseEntity.ok(paintings);
    }

    @GetMapping("/{id}/user/{username}")
    public ResponseEntity<?> getPainting(@PathVariable Long id, @PathVariable String username) {
        Optional<User> user = userService.getUserByUsername(username);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "User not found: " + username));
        }

        Optional<PaintingDto> painting = paintingService.getPaintingById(id, user.get());
        if (painting.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(painting.get());
    }

    @PostMapping("/user/{username}")
    public ResponseEntity<?> savePainting(@PathVariable String username,
                                          @RequestBody PaintingDto paintingDto) {
        Optional<User> user = userService.getUserByUsername(username);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "User not found: " + username));
        }

        try {
            PaintingDto savedPainting = paintingService.savePainting(paintingDto, user.get());
            return ResponseEntity.ok(savedPainting);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Failed to save painting: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}/user/{username}")
    public ResponseEntity<?> updatePainting(@PathVariable Long id,
                                            @PathVariable String username,
                                            @RequestBody PaintingDto paintingDto) {
        Optional<User> user = userService.getUserByUsername(username);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "User not found: " + username));
        }

        try {
            PaintingDto updatedPainting = paintingService.updatePainting(id, paintingDto, user.get());
            return ResponseEntity.ok(updatedPainting);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Failed to update painting: " + e.getMessage()));
        }
    }

}
