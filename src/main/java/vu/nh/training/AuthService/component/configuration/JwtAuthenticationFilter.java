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
import vu.nh.training.AuthService.services.jwtServices.JwtService;
import vu.nh.training.AuthService.services.jwtServices.UserService;

import java.io.IOException;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    UserService userDetailsService;
    JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = WebCommon.getHeaderFromRequest(request, "accessToken");
        String refreshToken = WebCommon.getHeaderFromRequest(request, "refreshToken");

        logger.info("uri: " + request.getRequestURI());
        logger.info("accessToken: " + accessToken);
        logger.info("refreshToken: " + refreshToken);

        if (!StringUtils.isEmpty(accessToken) && jwtService.validateToken(accessToken)) {
            String username = jwtService.getUsernameFromToken(accessToken);
            setContextHolder(request, username);

        } else if (!StringUtils.isEmpty(refreshToken) && jwtService.validateToken(refreshToken)
                && jwtService.checkRefreshToken(refreshToken)) {
            String username = jwtService.processNewSession(response, refreshToken);
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
