package com.logiconets.c196_nick_albers.model;

import androidx.room.TypeConverter;

import java.util.Calendar;
import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value){
        Date date = new Date(value);
        return value == null ? null : date;
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date){
        return date == null ? null : date.getTime();
    }
}
