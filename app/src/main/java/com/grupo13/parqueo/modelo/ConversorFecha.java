package com.grupo13.parqueo.modelo;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.Calendar;

public class ConversorFecha {
    @TypeConverter
    public static Calendar fromTimestamp(Long value){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(value);
        return value == null ? null: calendar;
    }

    @TypeConverter
    public static Long calendarToTimestamp(Calendar calendar){
        return calendar == null ? null : calendar.getTimeInMillis();
    }
}
