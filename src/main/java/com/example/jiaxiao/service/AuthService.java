package com.example.jiaxiao.service;

import com.example.jiaxiao.entity.AdminUser;

public interface AuthService {

    AdminUser register(AdminUser user);

    AdminUser login(String username, String password);
}
