package com.example.authtodo.service;

import com.example.authtodo.entity.JwtResponse;
import com.example.authtodo.entity.User;
import com.example.authtodo.repository.UserRepository;
import com.example.authtodo.security.JwtUtil;
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
            throw new IllegalArgumentException("Username already exists");
        }

        String encodedPassword = passwordEncoder.encode(password);

        return userRepository.save(User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build());

    }

    @Override
    public JwtResponse login(String username, String password) {
        // 사용자 존재 여부 확인
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password."));

        // 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password.");
        }

        // JWT 토큰 생성
        String accessToken = jwtUtil.createAccessToken(username);
        String refreshToken = jwtUtil.createRefreshToken(username);

        return new JwtResponse(accessToken, refreshToken);
    }
}
