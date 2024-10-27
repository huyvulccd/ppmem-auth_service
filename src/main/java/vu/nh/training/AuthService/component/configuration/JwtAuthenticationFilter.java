package vu.nh.training.AuthService.component.configuration;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import vu.nh.training.AuthService.component.common.WebCommon;
import vu.nh.training.AuthService.component.providers.JwtProvider;
import vu.nh.training.AuthService.services.jwtServices.UserService;

import java.io.IOException;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    UserService userDetailsService;
    JwtProvider JwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = WebCommon.getJWTFromRequest(request.getCookies(), "accessToken");
        String refreshToken = WebCommon.getJWTFromRequest(request.getCookies(), "refreshToken");

        logger.info("uri: " + request.getRequestURI());
        logger.info("accessToken: " + accessToken);
        logger.info("refreshToken: " + refreshToken);

        if (!StringUtils.isEmpty(accessToken) && JwtProvider.validateToken(accessToken)) {
            String username = JwtProvider.getUsernameFromToken(accessToken);
            setContextHolder(request, username);

        } else if (!StringUtils.isEmpty(refreshToken) && JwtProvider.validateToken(refreshToken)
                && JwtProvider.checkRefreshToken(refreshToken)) {
            String username = JwtProvider.processNewSession(response, refreshToken);
            setContextHolder(request, username);

        }
        filterChain.doFilter(request, response);
    }

    private void setContextHolder(HttpServletRequest request, String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
