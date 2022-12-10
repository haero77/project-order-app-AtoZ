package devcourse.baemin.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeUtil {

    private TimeUtil() {
    }

    public static LocalDateTime getCurrentSeoulTime() {
        return ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime();
    }
}
