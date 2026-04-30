package com.example.jiaxiao.service.impl;

import com.example.jiaxiao.entity.AdminUser;
import com.example.jiaxiao.repository.AdminUserRepository;
import com.example.jiaxiao.service.AuthService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final AdminUserRepository adminUserRepository;

    @Override
    public AdminUser register(AdminUser user) {
        // 简单判断用户名是否重复
        adminUserRepository.findByUsername(user.getUsername())
                .ifPresent(u -> {
                    throw new EntityExistsException("用户名已存在");
                });

        // 这里只是课程设计，先不加加密，直接存明文
        // 如果想加密，可以用 BCryptPasswordEncoder 再改
        return adminUserRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public AdminUser login(String username, String password) {
        AdminUser user = adminUserRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("用户名或密码错误"));
        if (!user.getPassword().equals(password)) {
            throw new EntityNotFoundException("用户名或密码错误");
        }
        return user;
    }
}
