package com.yohoard.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateTimeUtils {
    public static LocalDateTime fromMicros(long input) {
        long seconds = input / 1_000_000;
        long micros = input % 1_000_000;
        long nanos = micros * 1_000;

        Instant instant = Instant.ofEpochSecond(seconds, nanos);

        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
