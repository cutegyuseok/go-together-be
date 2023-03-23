package com.example.gotogether.admin.service.impl;

import com.example.gotogether.admin.service.AdminService;
import com.example.gotogether.auth.dto.UserDTO;
import com.example.gotogether.auth.entity.User;
import com.example.gotogether.auth.repository.UserRepository;
import com.example.gotogether.global.response.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ResponseDTO<?> setUserToAdmin(UserDTO.EmailOnly dto) {
        try {
            User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(IllegalArgumentException::new);
            user.setRole("ROLE_ADMIN");
            return new ResponseDTO<>(HttpStatus.OK, user.getEmail(), "관리자 권한 부여 완료.");
        } catch (IllegalArgumentException e) {
            return new ResponseDTO<>(HttpStatus.BAD_REQUEST, null, "관리자 권한 부여 실패");
        }
    }

    @Override
    @Transactional
    public ResponseDTO<?> setAdminToUser(UserDTO.EmailOnly dto) {
        try {
            User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(IllegalArgumentException::new);
            user.setRole("ROLE_USER");
            return new ResponseDTO<>(HttpStatus.OK, user.getEmail(), "관리자 권한 박탈 완료");
        } catch (IllegalArgumentException e) {
            return new ResponseDTO<>(HttpStatus.BAD_REQUEST, dto.getEmail(), "관리자 권한 박탈 실패");
        }
    }

    @Override
    public ResponseDTO<?> findUserList(Pageable pageable) {
        try {
            Page<UserDTO.PatchUserResDTO> userList = userRepository.findAll(pageable)
                    .map(UserDTO.PatchUserResDTO::new);
            return new ResponseDTO<>(new PageResponseDTO(userList));
        } catch (Exception e) {
            return new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR, null, "회원 목록을 불러오지 못했습니다.");
        }
    }

}
