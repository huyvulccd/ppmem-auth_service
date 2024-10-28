package vu.nh.training.AuthService.component.constants;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class TimeConstant {
    public static final long JWT_ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60; // an hour

    public static Date getTimeMonthsAfter(int months) {
        LocalDate currentDate = LocalDate.now();
        LocalDate dateAfterMonths = currentDate.plusMonths(months);

        return Date.from(dateAfterMonths.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
