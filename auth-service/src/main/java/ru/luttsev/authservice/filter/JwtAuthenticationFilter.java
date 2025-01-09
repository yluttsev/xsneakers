package ru.luttsev.authservice.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.luttsev.authservice.exception.InvalidTokenException;
import ru.luttsev.authservice.security.UserDetailsImpl;
import ru.luttsev.authservice.security.UserDetailsServiceImpl;
import ru.luttsev.authservice.service.JwtService;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authenticationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.nonNull(authenticationHeader) && authenticationHeader.startsWith("Bearer ")) {
            String accessToken = authenticationHeader.substring(7);
            if (jwtService.validateAccessToken(accessToken)) {
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime accessTokenExpiration = LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(jwtService.getTokenExpiration(accessToken)), ZoneId.systemDefault()
                );
                if (accessTokenExpiration.isAfter(now)) {
                    String email = jwtService.getTokenSubject(accessToken);
                    UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(email);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, userDetails.getPassword(), userDetails.getAuthorities()
                    );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                    return;
                }
                throw new InvalidTokenException("Access token просрочен.");
            }
            throw new InvalidTokenException("Неверный access token.");
        }
        filterChain.doFilter(request, response);
    }
}
