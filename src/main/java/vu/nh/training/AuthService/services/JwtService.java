package vu.nh.training.AuthService.services.jwtServices;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import vu.nh.training.AuthService.component.common.WebCommon;
import vu.nh.training.AuthService.component.constants.SecurityConstants;
import vu.nh.training.AuthService.component.constants.TimeConstant;
import vu.nh.training.AuthService.component.utils.AdapterUtils;
import vu.nh.training.AuthService.component.utils.TimeStampUtil;
import vu.nh.training.AuthService.controller.dtos.FingerDevice;
import vu.nh.training.AuthService.repositories.SessionTableRepository;
import vu.nh.training.AuthService.repositories.entities.SessionTable;
import vu.nh.training.AuthService.repositories.entities.UserApp;

import java.text.ParseException;
import java.util.Date;

@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtService {
    SessionTableRepository sessionTableRepository;
    @Lazy
    UserService userService;

    @NonFinal
    @Value("${jwt.secret}")
    private String jwtSecret;

    public void loginOauth2Success(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) {
        String accessToken = generateAccessToken(authentication);
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

    public String generateAccessToken(Authentication authentication) {
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + TimeConstant.JWT_ACCESS_TOKEN_EXPIRE_TIME);
        String username = authentication.getName();

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issueTime(currentDate)
                .expirationTime(expireDate)
                .build();
        return generateToken(jwtClaimsSet);
    }

    public String generateRefreshToken(Authentication authentication) {
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + TimeConstant.getTimeMonthsAfter(6).getTime());
        String username = authentication.getName();
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issueTime(currentDate)
                .expirationTime(expireDate)
                .build();
        return generateToken(jwtClaimsSet);
    }

    public String rotateRefreshToken(HttpServletResponse response, String refreshToken) throws ParseException {
        JWTClaimsSet oldPayload = getJWTClaimsSetFromJwt(refreshToken);
        Date currentDate = new Date();

        Date oldExpirationTime = oldPayload.getExpirationTime();

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(oldPayload.getSubject())
                .issueTime(currentDate)
                .expirationTime(oldExpirationTime)
                .build();

        return generateToken(jwtClaimsSet);
    }

    private String generateToken(JWTClaimsSet jwtClaimsSet) {
        JWSHeader header = new JWSHeader(SecurityConstants.JWT_ALGORITHM);

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(jwtSecret));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("", e);
            throw new RuntimeException(e);
        }
    }

    private JWTClaimsSet getJWTClaimsSetFromJwt(String token) throws ParseException {
        return getSignedJWTFromJWT(token).getJWTClaimsSet();
    }

    public SignedJWT getSignedJWTFromJWT(String token) throws ParseException{
        return SignedJWT.parse(token);
    }

    public void deleteRefreshToken(String refreshToken) {
        sessionTableRepository.deleteById(refreshToken);
    }

    public void logoutHandler(HttpServletRequest request, Authentication authentication) {
        String refreshToken = WebCommon.getHeaderFromRequest(request, "RefreshToken");
        deleteRefreshToken(refreshToken);
    }

    public boolean validateToken(String token) {
        try {
            JWSVerifier verifier = new MACVerifier(jwtSecret.getBytes());
            SignedJWT signedJWT = getSignedJWTFromJWT(token);

            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            boolean isVerifier = signedJWT.verify(verifier);

            return isVerifier && expirationTime.after(new Date());
        } catch (JOSEException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkRefreshToken(String refreshToken) {
        return sessionTableRepository.existsByRefreshToken(refreshToken);
    }

    public String getUsernameFromToken(String accessToken) {
        // TODO Auto-generated method stub
        try {
            return getSignedJWTFromJWT(accessToken).getJWTClaimsSet().getSubject();
        } catch (ParseException e) {
            e.printStackTrace();
            // TODO Auto-generated catch block
            throw new IllegalArgumentException("token " + accessToken + "invalid");
        }
    }
}
