package vu.nh.training.AuthService.component.common;

import jakarta.servlet.http.HttpServletRequest;

public class WebCommon {
    public static String getHeaderFromRequest(HttpServletRequest request, String tokenType) {
       return switch (tokenType) {
           case "Authorization" -> request.getHeader("Authorization");
           case "RefreshToken" -> request.getHeader("RefreshToken");
           default -> throw new IllegalArgumentException("Invalid token type: " + tokenType);
       };
    }
}
