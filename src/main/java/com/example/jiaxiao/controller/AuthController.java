package com.example.jiaxiao.controller;

import com.example.jiaxiao.entity.AdminUser;
import com.example.jiaxiao.service.AuthService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // ========== 页面 ==========

    @GetMapping("/login")
    public String loginPage() {
        return "login";   // templates/login.html
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";  // templates/register.html
    }

    @GetMapping("/students")
    public String studentsPage(jakarta.servlet.http.HttpSession session) {
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser == null) {
            // 没登录，重定向回登录页
            return "redirect:/login";
        }
        return "students";  // 已登录，正常返回页面
    }

    // ========== 注册（POST /register） ==========

    @PostMapping("/register")
    public String doRegister(@RequestParam String username,
                             @RequestParam(required = false) String phone,
                             @RequestParam String password,
                             Model model) {
        AdminUser user = new AdminUser();
        user.setUsername(username);
        user.setPhone(phone);
        user.setPassword(password);  // 简单明文存储

        try {
            authService.register(user);
        } catch (EntityExistsException e) {
            model.addAttribute("error", "用户名已存在");
            return "register";
        }

        // 注册成功跳到登录页
        return "redirect:/login";
    }

    // ========== 登录（POST /login） ==========

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          Model model,
                          HttpSession session) {
        try {
            AdminUser user = authService.login(username, password);

            // 写入服务端 Session（真正的“登录状态”）
            session.setAttribute("loginUser", user);

            // 登录成功后跳转到学生管理页面
            return "redirect:/students";
        } catch (EntityNotFoundException e) {
            // 登录失败：回到登录页并显示错误提示
            model.addAttribute("error", "用户名或密码错误");
            return "login";
        }
    }

    // ========== 退出登录 ==========

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
