package com.levimartines.cursomc.controller;

import com.levimartines.cursomc.security.JWTUtil;
import com.levimartines.cursomc.service.UserService;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/auth")
@RestController
public class AuthController {

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping(value = "/refresh_token")
    private ResponseEntity<?> refreshToken(HttpServletResponse res) {
        res.addHeader("Authorization",
            jwtUtil.generateToken(UserService.authenticated().getUsername()));
        return ResponseEntity.noContent().build();
    }

}
