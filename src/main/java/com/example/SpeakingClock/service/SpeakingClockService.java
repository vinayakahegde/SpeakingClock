package com.example.SpeakingClock.service;

import com.example.SpeakingClock.exception.ClockException;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class SpeakingClockService {

    public static int USER_TIME_NOT_INPUT_ERRORCODE = 101;
    public static String USER_TIME_NOT_INPUT_MESSAGE = "Please input a valid time in 24 Hour format";

    public static int USER_TIME_NOT_VALID_ERRORCODE = 102;
    public static String USER_TIME_NOT_VALID_MESSAGE = "Please enter the time in HH:MM format where HH is between 0 - 23 and MM is between 0 - 59";

    public String convertCurrentTimeTo24HourTextFormat(){
        String currentTimeIn24HourFormat = getCurrentTimeIn24HourTextFormat();
        return getTimeInTextFormat(currentTimeIn24HourFormat);
    }

    public String convertUserTimeTo24HourTextFormat(String userTime) throws ClockException {
        validateUserTimeIn24HourFormat(userTime);
        return getTimeInTextFormat(userTime);
    }

    private String getCurrentTimeIn24HourTextFormat(){
        LocalTime localTime = LocalTime.now();
        int hour = localTime.getHour();
        int minute = localTime.getMinute();

        String hourStr = "" + hour;
        String minuteStr = "" + minute;

        if (hour < 10){
            hourStr = "0" + hourStr;
        }

        if (minute < 10){
            minuteStr = "0" + minuteStr;
        }

        return hourStr + ":" + minuteStr;
    }


    public void validateUserTimeIn24HourFormat(String timeIn24HourFormat) throws ClockException {
        if (timeIn24HourFormat == null) {
            throw new ClockException(USER_TIME_NOT_INPUT_ERRORCODE, USER_TIME_NOT_INPUT_MESSAGE);
        }

        if (timeIn24HourFormat.length() != 5) {
            throw new ClockException(USER_TIME_NOT_VALID_ERRORCODE, USER_TIME_NOT_VALID_MESSAGE);
        }

        String[] timeParts = timeIn24HourFormat.split(":");
        if (timeParts.length != 2){
            throw new ClockException(USER_TIME_NOT_VALID_ERRORCODE, USER_TIME_NOT_VALID_MESSAGE);
        }

        if (timeParts[0].length() != 2 || timeParts[1].length() != 2){
            throw new ClockException(USER_TIME_NOT_VALID_ERRORCODE, USER_TIME_NOT_VALID_MESSAGE);
        }

        try {
            new Integer(timeParts[0]);
            new Integer(timeParts[1]);
        } catch (NumberFormatException e){
            throw new ClockException(USER_TIME_NOT_VALID_ERRORCODE, USER_TIME_NOT_VALID_MESSAGE);
        }

        int userTimeHour = new Integer(timeParts[0]);
        int userTimeMinute = new Integer(timeParts[1]);

        if (userTimeHour < 0 || userTimeHour > 23){
            throw new ClockException(USER_TIME_NOT_VALID_ERRORCODE, USER_TIME_NOT_VALID_MESSAGE);
        }

        if (userTimeMinute < 0 || userTimeMinute > 59){
            throw new ClockException(USER_TIME_NOT_VALID_ERRORCODE, USER_TIME_NOT_VALID_MESSAGE);
        }
    }

    public String getTimeInTextFormat(String timeIn24HourFormat){
        String[] timeParts = timeIn24HourFormat.split(":");
        if (timeParts[0].equals("00") && timeParts[1].equals("00")){
            return "It's Midnight";
        }

        if (timeParts[0].equals("12") && timeParts[1].equals("00")){
            return "It's Midday";
        }

        String hourPartText = convertNumericToTextFormat(timeParts[0]);
        String minutePartText = convertNumericToTextFormat(timeParts[1]);

        return "It's " + hourPartText + " " + minutePartText;
    }

    private String convertNumericToTextFormat(String twoDigitNumber){
        Map<Character, String> unitsPlaceText = new HashMap<Character, String>(10);
        Map<Character, String> tensPlaceText = new HashMap<Character, String>(10);

        unitsPlaceText.put('0', "");
        unitsPlaceText.put('1', "one");
        unitsPlaceText.put('2', "two");
        unitsPlaceText.put('3', "three");
        unitsPlaceText.put('4', "four");
        unitsPlaceText.put('5', "five");
        unitsPlaceText.put('6', "six");
        unitsPlaceText.put('7', "seven");
        unitsPlaceText.put('8', "eight");
        unitsPlaceText.put('9', "nine");

        tensPlaceText.put('0', "");
        tensPlaceText.put('1', "");
        tensPlaceText.put('2', "twenty");
        tensPlaceText.put('3', "thirty");
        tensPlaceText.put('4', "forty");
        tensPlaceText.put('5', "fifty");
        tensPlaceText.put('6', "sixty");
        tensPlaceText.put('7', "seventy");
        tensPlaceText.put('8', "eighty");
        tensPlaceText.put('9', "ninety");

        if (twoDigitNumber.startsWith("1")){
            Map<String, String> teensText = new HashMap<String, String>(10);

            teensText.put("10", "ten");
            teensText.put("11", "eleven");
            teensText.put("12", "twelve");
            teensText.put("13", "thirteen");
            teensText.put("14", "fourteen");
            teensText.put("15", "fifteen");
            teensText.put("16", "sixteen");
            teensText.put("17", "seventeen");
            teensText.put("18", "eighteen");
            teensText.put("19", "nineteen");

            return teensText.get(twoDigitNumber);
        }

        char[] twoDigitNumberInChars = twoDigitNumber.toCharArray();
        String tensPlaceTextVal = tensPlaceText.get(twoDigitNumberInChars[0]);
        String unitsPlaceTextVal = unitsPlaceText.get(twoDigitNumberInChars[1]);

        return tensPlaceTextVal + " " + unitsPlaceTextVal;
    }
}
