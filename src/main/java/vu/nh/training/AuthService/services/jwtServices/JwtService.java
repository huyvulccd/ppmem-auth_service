package vu.nh.training.AuthService.services.jwtServices;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import vu.nh.training.AuthService.component.common.WebCommon;
import vu.nh.training.AuthService.component.utils.AdapterUtils;
import vu.nh.training.AuthService.component.utils.TimeStampUtil;
import vu.nh.training.AuthService.controller.dtos.FingerDevice;
import vu.nh.training.AuthService.repositories.SessionTableRepository;
import vu.nh.training.AuthService.repositories.entities.SessionTable;
import vu.nh.training.AuthService.repositories.entities.UserApp;

@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PACKAGE)
public class JwtService {
    SessionTableRepository sessionTableRepository;
    @Lazy
    UserService userService;

    public void loginOauth2Success(HttpServletRequest request, HttpServletResponse response,
                                   Authentication authentication) {
        String accessToken = generateToken(authentication);
        String refreshToken = generateRefreshToken(authentication);
        String fingerDevice = WebCommon.getHeaderFromRequest(request, "FingerDevice");
        saveRefreshToken(AdapterUtils.parse(fingerDevice, FingerDevice.class), authentication.getName(), refreshToken);

        response.addHeader("Authorization", "Bearer " + accessToken);
        response.addHeader("RefreshToken", refreshToken);
    }

    public void saveRefreshToken(FingerDevice fingerDevice, String username, String refreshToken) {
        UserApp userApp = userService.getUserByUsername(username);

        String action = this.getClass() + "/" + "saveRefreshToken";
        String ipAddress = fingerDevice.ipAddress();
        String deviceInfo = fingerDevice.deviceInfo();
        SessionTable sessionTable = SessionTable.builder()
                .RefreshToken(refreshToken)
                .userApp(userApp)
                .action(action)
                .timestamp(TimeStampUtil.getCurrentTimestamp())
                .ipAddress(ipAddress)
                .deviceInfo(deviceInfo)
                .build();
        sessionTableRepository.save(sessionTable);
    }

    public String generateToken(Authentication authentication) {
        return null;
    }

    public String generateRefreshToken(Authentication authentication) {
        return null;
    }

    public void deleteRefreshToken(String name, String refreshToken) {

    }

    public void logoutHandler(HttpServletRequest request, Authentication authentication) {
        String refreshToken = WebCommon.getHeaderFromRequest(request, "RefreshToken");
        deleteRefreshToken(authentication.getName(), refreshToken);
    }

    public boolean validateToken(String accessToken) {
        return false;
    }

    public String getUsernameFromToken(String accessToken) {
        return null;
    }

    public boolean checkRefreshToken(String refreshToken) {
        return false;
    }

    public String generateAccessToken(String username) {
        return null;
    }

    public String processNewSession(HttpServletResponse response, String refreshToken) {
        return "";
    }
}
