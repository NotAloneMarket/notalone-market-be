package com.ddwu.notalonemarket.service;
import com.ddwu.notalonemarket.domain.User;
import com.ddwu.notalonemarket.domain.UserDAO;
import com.ddwu.notalonemarket.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    // 사용자 등록
    @Transactional
    public UserDTO registerUser(UserDTO userDTO) {
        User user = new User(userDTO.getUsername(), userDTO.getEmail(), userDTO.getPassword(), userDTO.getNickname());
        user = userDAO.save(user);
        return toDTO(user);
    }

    // 이메일로 사용자 조회
    public UserDTO getUserByEmail(String email) {
        User user = userDAO.findByEmail(email);
        return toDTO(user);
    }

    // 사용자 이름으로 사용자 조회
    public UserDTO getUserByUsername(String username) {
        User user = userDAO.findByUsername(username);
        return toDTO(user);
    }

    // 사용자 삭제
    @Transactional
    public void deleteUser(Long id) {
        userDAO.deleteById(id);
    }

 // User 엔티티를 UserDTO로 변환
    private UserDTO toDTO(User user) {
        return new UserDTO(user.getUsername(), user.getEmail(), user.getPassword(), user.getNickname());
    }
}
