package ru.luttsev.authservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luttsev.authservice.model.entity.RefreshToken;
import ru.luttsev.authservice.repository.RefreshTokenRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    public RefreshToken create(String email, String refreshToken, LocalDateTime issuedAt, LocalDateTime expiresAt) {
        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .user(userService.getByEmail(email))
                .refreshToken(refreshToken)
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .build();
        return refreshTokenRepository.save(refreshTokenEntity);
    }

    public void deleteByEmail(String email) {
        refreshTokenRepository.deleteByEmail(email);
    }
}
