package ru.luttsev.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.luttsev.authservice.model.mapper.UserMapper;
import ru.luttsev.authservice.model.payload.UserPayload;
import ru.luttsev.authservice.model.payload.request.UpdateUserRequest;
import ru.luttsev.authservice.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/api/user/{id}")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public UserPayload getUserInfo(@PathVariable("id") UUID id) {
        return userMapper.mapToPayload(userService.getById(id));
    }

    @PatchMapping
    @PreAuthorize("authentication.principal.user.id == #id")
    public UserPayload updateUser(@PathVariable("id") UUID id, @RequestBody UpdateUserRequest request) {
        return userMapper.mapToPayload(userService.update(id, request));
    }
}
