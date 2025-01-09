package ru.luttsev.authservice.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.luttsev.authservice.exception.ResourceNotFoundException;
import ru.luttsev.authservice.model.entity.User;
import ru.luttsev.authservice.model.mapper.UserMapper;
import ru.luttsev.authservice.model.payload.request.SignUpRequest;
import ru.luttsev.authservice.model.payload.request.UpdateUserRequest;
import ru.luttsev.authservice.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Validated
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Пользователь '%s' не найден.".formatted(email))
        );
    }

    public User getById(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Пользователь '%s' не найден.".formatted(id))
        );
    }

    public User update(UUID id, @Valid UpdateUserRequest request) {
        User user = this.getById(id);
        userMapper.patchUpdateUser(user, request);
        return userRepository.save(user);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User create(@Valid SignUpRequest request) {
        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(bCryptPasswordEncoder.encode(request.password()))
                .build();
        return userRepository.save(user);
    }

    public void blockById(UUID id) {
        userRepository.blockById(id);
    }

    public void unblockById(UUID id) {
        userRepository.unblockById(id);
    }
}
