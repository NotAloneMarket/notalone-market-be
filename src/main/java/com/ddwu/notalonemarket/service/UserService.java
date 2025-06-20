package com.ddwu.notalonemarket.service;

import com.ddwu.notalonemarket.domain.User;
import com.ddwu.notalonemarket.repository.UserRepository;
import com.ddwu.notalonemarket.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
	
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String loginAndGetToken(String loginId, String rawPw) {
        Optional<User> userOpt = userRepository.findByLoginId(loginId);
        if (userOpt.isPresent() && passwordEncoder.matches(rawPw, userOpt.get().getPassword())) {
            return jwtUtil.generateToken(loginId);
        }
        throw new IllegalArgumentException("Invalid credentials");
    }

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> login(String loginId, String rawPw) {
        Optional<User> optUser = userRepository.findByLoginId(loginId);
        if (optUser.isPresent()) {
            User user = optUser.get();
            System.out.println("rawPw = " + rawPw);
            System.out.println("encodedPw = " + user.getPassword());
            System.out.println("match = " + passwordEncoder.matches(rawPw, user.getPassword()));
        }
        return optUser.filter(user -> passwordEncoder.matches(rawPw, user.getPassword()));
    }

    // 기존 방식 (전화번호 + 계좌번호만 변경)
    public User updateProfile(Long userId, String newPhone, String newAccount) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setPhoneNum(newPhone);
        user.setAccountNumber(newAccount);
        return userRepository.save(user);
    }

    // 이미지 포함 프로필 업데이트용 오버로딩
    public User updateProfile(Long userId, String nickname, String phoneNum, String profileImageUrl) {
        User user = userRepository.findById(userId).orElseThrow();

        if (nickname != null) user.setNickname(nickname);
        if (phoneNum != null) user.setPhoneNum(phoneNum);
        if (profileImageUrl != null) user.setProfileImageUrl(profileImageUrl);

        return userRepository.save(user);
    }

    public User changePassword(Long userId, String currentPw, String newPw) {
        User user = userRepository.findById(userId).orElseThrow();
        if (!passwordEncoder.matches(currentPw, user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }
        user.setPassword(passwordEncoder.encode(newPw));
        return userRepository.save(user);
    }

    public Long findUserIdByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId)
                             .map(User::getUserId)
                             .orElse(null);
    }
    public String getUsernameById(Long userId) {
        return userRepository.findById(userId)
                             .map(User::getNickname)
                             .orElse("알 수 없음");
    }

}
