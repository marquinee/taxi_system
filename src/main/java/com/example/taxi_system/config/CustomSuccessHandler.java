package com.example.taxi_system.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        String role = authentication.getAuthorities().iterator().next().getAuthority();

        switch (role) {
            case "ROLE_ADMIN":
                response.sendRedirect("/admin/dashboard");
                break;
            case "ROLE_OPERATOR":
                response.sendRedirect("/operator/dashboard");
                break;
            case "ROLE_DRIVER":
                response.sendRedirect("/driver/orders");
                break;
            default:
                response.sendRedirect("/");
        }
    }
}

