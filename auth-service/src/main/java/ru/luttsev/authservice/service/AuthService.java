package ru.luttsev.authservice.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.luttsev.authservice.exception.InvalidTokenException;
import ru.luttsev.authservice.exception.UserAlreadyExistsException;
import ru.luttsev.authservice.model.entity.User;
import ru.luttsev.authservice.model.entity.UserRole;
import ru.luttsev.authservice.model.payload.request.SignInRequest;
import ru.luttsev.authservice.model.payload.request.SignUpRequest;
import ru.luttsev.authservice.model.payload.request.UpdateAccessTokenRequest;
import ru.luttsev.authservice.model.payload.response.TokenPairResponse;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Validated
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    public TokenPairResponse signIn(@Valid SignInRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.email(), signInRequest.password())
        );
        List<String> userRoles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        long currentTimestamp = System.currentTimeMillis() / 1000;
        String accessToken = jwtService.generateAccessToken(signInRequest.email(), userRoles, currentTimestamp);
        String refreshToken = jwtService.generateRefreshToken(signInRequest.email(), currentTimestamp);
        LocalDateTime issuedAt = LocalDateTime.ofInstant(Instant.ofEpochSecond(currentTimestamp), ZoneId.systemDefault());
        refreshTokenService.deleteByEmail(signInRequest.email());
        refreshTokenService.create(
                signInRequest.email(),
                refreshToken,
                issuedAt,
                LocalDateTime.ofInstant(Instant.ofEpochSecond(jwtService.getTokenExpiration(accessToken)), ZoneId.systemDefault())
        );
        return new TokenPairResponse(accessToken, refreshToken, jwtService.getTokenExpiration(accessToken));
    }

    public void signUp(@Valid SignUpRequest request) {
        if (!userService.existsByEmail(request.email())) {
            User createdUser = userService.create(request);
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(createdUser.getEmail(), createdUser.getPassword())
            );
        } else {
            throw new UserAlreadyExistsException(request.email());
        }
    }

    public TokenPairResponse updateAccessToken(@Valid UpdateAccessTokenRequest updateAccessTokenRequest) {
        String refreshToken = updateAccessTokenRequest.refreshToken();
        if (jwtService.validateRefreshToken(refreshToken)) {
            LocalDateTime refreshTokenExpirationDate = LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(jwtService.getTokenExpiration(refreshToken)), ZoneId.systemDefault()
            );
            if (refreshTokenExpirationDate.isAfter(LocalDateTime.now())) {
                String email = jwtService.getTokenSubject(refreshToken);
                User user = userService.getByEmail(email);
                long currentTimestamp = System.currentTimeMillis() / 1000;
                LocalDateTime issuedAt = LocalDateTime.ofInstant(Instant.ofEpochSecond(currentTimestamp), ZoneId.systemDefault());
                String newAccessToken = jwtService.generateAccessToken(
                        email,
                        user.getRoles().stream()
                                .map(UserRole::getId)
                                .toList(),
                        currentTimestamp);
                String newRefreshToken = jwtService.generateRefreshToken(email, currentTimestamp);
                refreshTokenService.deleteByEmail(email);
                refreshTokenService.create(
                        email,
                        newRefreshToken,
                        issuedAt,
                        LocalDateTime.ofInstant(Instant.ofEpochSecond(jwtService.getTokenExpiration(newRefreshToken)), ZoneId.systemDefault())
                );
                return new TokenPairResponse(newAccessToken, newRefreshToken, jwtService.getTokenExpiration(newAccessToken));
            }
            throw new InvalidTokenException("Refresh token is expired.");
        }
        throw new InvalidTokenException("Invalid refresh token.");
    }
}
