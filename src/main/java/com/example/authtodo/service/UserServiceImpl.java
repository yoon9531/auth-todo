package com.example.authtodo.service;

import com.example.authtodo.entity.JwtResponse;
import com.example.authtodo.entity.User;
import com.example.authtodo.exception.handler.UserExceptionHandler;
import com.example.authtodo.global.ErrorStatus;
import com.example.authtodo.repository.UserRepository;
import com.example.authtodo.security.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public User registerUser(String username, String password) {

        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserExceptionHandler(ErrorStatus.USER_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncoder.encode(password);

        return userRepository.save(User.builder()
                .username(username)
                .password(encodedPassword)
                .build());

    }

    @Override
    public JwtResponse login(String username, String password) {
        // 사용자 존재 여부 확인
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserExceptionHandler(ErrorStatus.USER_NOT_FOUND));

        // 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserExceptionHandler(ErrorStatus.USER_PASSWORD_NOT_MATCH);
        }

        // JWT 토큰 생성
        String accessToken = jwtUtil.createAccessToken(username);
        String refreshToken = jwtUtil.createRefreshToken(username);

        return new JwtResponse(accessToken, refreshToken);
    }

    @Override
    public JwtResponse refresh(String refreshToken) {

        try {
            Claims claims = jwtUtil.getClaims(refreshToken);
            String username = claims.getSubject();

            if (userRepository.findByUsername(username).isEmpty()) {
                throw new UserExceptionHandler(ErrorStatus.USER_NOT_FOUND);
            }

            String accessToken = jwtUtil.createAccessToken(username);
            return new JwtResponse(accessToken, refreshToken);
        }
        catch (ExpiredJwtException e) {
            throw new UserExceptionHandler(ErrorStatus.REFRESH_TOKEN_EXPIRED);
        }
        catch (Exception e) {
            throw new UserExceptionHandler(ErrorStatus.INVALID_REFRESH_TOKEN);
        }
    }
}
