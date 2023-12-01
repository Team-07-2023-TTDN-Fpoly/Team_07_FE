package com.team.team_07_fe.utils;

import android.annotation.SuppressLint;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class FormatHelper {
    //Định dạng về tiền
    private static DecimalFormat moneyFormatter = new DecimalFormat("#,### VNĐ");

    //Định dạng lại số tiền
    public static String convertPriceToString(long price){
        return price >= 0  ? moneyFormatter.format(price) : "- " + moneyFormatter.format(Math.abs(price));
    }
    //Định dạng lại ngày tháng
    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat monthormat = new SimpleDateFormat("MM/yyyy");

    public static String convertMonthtoString(Date date){
        return date != null ? monthormat.format(date) : "";
    }

    public static Date convertStringtoMonth(String input){
        try {
            return !input.isEmpty() ? monthormat.parse(input) : null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    //Định dạng lại ngày tháng năm
    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static String convertDatetoString(Date date){
        return date != null ? dateFormat.format(date) : "";
    }

    public static Date convertStringtoDate(String input){
        try {
            return !input.isEmpty() ? dateFormat.parse(input) : null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    //Định dạng về giờ
    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    public static String convertTimeToString(Date time) {
        return time != null ? timeFormat.format(time) : "";
    }

    public static Date convertStringToTime(String timeString) {
        try {
            return !timeString.isEmpty() ? timeFormat.parse(timeString) : null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    //Định dạng số điện thoại
    public static String formatPhoneNumber(String number) {
        // Kiểm tra xem số điện thoại có đủ 10 số không
        if (number != null && number.length() == 10) {
            return number.substring(0, 4) + " " + number.substring(4, 7) + " " + number.substring(7);
        }
        return number; // Trả lại số không thay đổi nếu không đúng định dạng
    }
}
