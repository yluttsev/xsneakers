package ru.luttsev.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.luttsev.authservice.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @PostMapping("/block")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> blockUser(@RequestParam("user_id") UUID userId) {
        userService.blockById(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/unblock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> unblockUser(@RequestParam("user_id") UUID userId) {
        userService.unblockById(userId);
        return ResponseEntity.ok().build();
    }
}
