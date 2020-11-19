package com.macro.mall.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class DateUtil {
    public static LocalDateTime getToadyMin(){
        return LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
    }
    public static LocalDateTime getYesterdayMin(){
        LocalDateTime of = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime localDateTime = of.minusDays(1);
        return localDateTime;
    }
}
