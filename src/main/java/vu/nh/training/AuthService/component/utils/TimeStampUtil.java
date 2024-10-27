package vu.nh.training.AuthService.component.utils;

import java.sql.Timestamp;

public class TimeStampUtil {

    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
