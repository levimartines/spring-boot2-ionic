package com.levimartines.cursomc.controller;

import com.levimartines.cursomc.bean.EmailBean;
import com.levimartines.cursomc.security.JWTUtil;
import com.levimartines.cursomc.service.AuthService;
import com.levimartines.cursomc.service.UserService;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/auth")
@RestController
public class AuthController {

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private AuthService authService;

    @PostMapping(value = "/refresh_token")
    private ResponseEntity<?> refreshToken(HttpServletResponse res) {
        res.addHeader("Authorization",
            jwtUtil.generateToken(UserService.authenticated().getUsername()));
        res.addHeader("access-control-expose-headers", "Authorization");
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/forgot")
    private ResponseEntity<?> forgot(@Valid @RequestBody EmailBean emailBean) {
        authService.generateAndSendNewPassword(emailBean.getEmail());
        return ResponseEntity.noContent().build();
    }
}
