package com.smartclinic.service;

import com.smartclinic.dto.LoginRequestDto;
import com.smartclinic.dto.LoginResponseDto;
import com.smartclinic.entity.Admin;
import com.smartclinic.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AdminRepository adminRepository;
    private final TokenService tokenService;

    public AuthService(AdminRepository adminRepository, TokenService tokenService) {
        this.adminRepository = adminRepository;
        this.tokenService = tokenService;
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Admin admin = adminRepository.findByUsername(loginRequestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!admin.getPassword().equals(loginRequestDto.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        return new LoginResponseDto(tokenService.generateToken(admin.getUsername()));
    }
}
