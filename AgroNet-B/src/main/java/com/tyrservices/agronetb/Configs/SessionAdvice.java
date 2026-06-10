package com.tyrservices.agronetb.Configs;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class SessionAdvice {

    @ModelAttribute("isLog")
    public boolean isLoggedIn() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.isAuthenticated()
                && !"anonymousUser".equals(auth.getPrincipal());
    }

    public static void syncSessionFromJwt(HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()
                && auth.getPrincipal() instanceof JwtUserDetails userDetails) {

            if (session.getAttribute("userId") == null) {
                session.setAttribute("userId", userDetails.getUserId());
                session.setAttribute("userName", userDetails.getUserName());
                session.setAttribute("userEmail", userDetails.getUserEmail());
                session.setAttribute("tipo", userDetails.getTipo());
            }
        }
    }
}
