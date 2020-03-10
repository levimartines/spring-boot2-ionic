package com.levimartines.cursomc.service;

import com.levimartines.cursomc.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserService {

    public static CustomUserDetails authenticated() {
        try {
            return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}
