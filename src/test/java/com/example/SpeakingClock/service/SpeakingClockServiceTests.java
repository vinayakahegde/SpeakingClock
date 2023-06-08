package com.example.SpeakingClock.service;

import com.example.SpeakingClock.exception.ClockException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpeakingClockServiceTests {

    @Autowired
    SpeakingClockService speakingClockService;

    @Test
    public void getTimeInTextFormatTest(){
        String timeInTextFormat1 = speakingClockService.getTimeInTextFormat("00:00");
        Assertions.assertTrue(timeInTextFormat1.equalsIgnoreCase("it's midnight"));

        String timeInTextFormat2 = speakingClockService.getTimeInTextFormat("12:00");
        Assertions.assertTrue(timeInTextFormat2.equalsIgnoreCase("it's midday"));

        String timeInTextFormat3 = speakingClockService.getTimeInTextFormat("08:30");
        Assertions.assertTrue(timeInTextFormat3.contains("It's"));
        Assertions.assertTrue(timeInTextFormat3.contains("eight"));
        Assertions.assertTrue(timeInTextFormat3.contains("thirty"));

        String timeInTextFormat4 = speakingClockService.getTimeInTextFormat("21:45");
        Assertions.assertTrue(timeInTextFormat4.contains("It's"));
        Assertions.assertTrue(timeInTextFormat4.contains("twenty"));
        Assertions.assertTrue(timeInTextFormat4.contains("one"));
        Assertions.assertTrue(timeInTextFormat4.contains("forty"));
        Assertions.assertTrue(timeInTextFormat4.contains("five"));

        String timeInTextFormat5 = speakingClockService.getTimeInTextFormat("12:05");
        Assertions.assertTrue(timeInTextFormat5.contains("It's"));
        Assertions.assertTrue(timeInTextFormat5.contains("twelve"));
        Assertions.assertTrue(timeInTextFormat5.contains("five"));
    }

    @Test
    public void validateUserInputTest(){
        String testMessage1 = "";
        try {
            speakingClockService.validateUserTimeIn24HourFormat(null);
        } catch(ClockException e){
            testMessage1 = e.getMessage();
        }

        Assertions.assertTrue(testMessage1.equals(SpeakingClockService.USER_TIME_NOT_INPUT_MESSAGE));

        String testMessage2 = "";
        try {
            speakingClockService.validateUserTimeIn24HourFormat("000:30");
        } catch(ClockException e){
            testMessage2 = e.getMessage();
        }

        Assertions.assertTrue(testMessage2.equals(SpeakingClockService.USER_TIME_NOT_VALID_MESSAGE));

        String testMessage3 = "";
        try {
            speakingClockService.validateUserTimeIn24HourFormat("00-30");
        } catch(ClockException e){
            testMessage3 = e.getMessage();
        }

        Assertions.assertTrue(testMessage3.equals(SpeakingClockService.USER_TIME_NOT_VALID_MESSAGE));

        String testMessage4 = "";
        try {
            speakingClockService.validateUserTimeIn24HourFormat("0:1:0");
        } catch(ClockException e){
            testMessage4 = e.getMessage();
        }

        Assertions.assertTrue(testMessage4.equals(SpeakingClockService.USER_TIME_NOT_VALID_MESSAGE));

        String testMessage5 = "";
        try {
            speakingClockService.validateUserTimeIn24HourFormat("ab:cd");
        } catch(ClockException e){
            testMessage5 = e.getMessage();
        }

        Assertions.assertTrue(testMessage5.equals(SpeakingClockService.USER_TIME_NOT_VALID_MESSAGE));

        String testMessage6 = "";
        try {
            speakingClockService.validateUserTimeIn24HourFormat("24:30");
        } catch(ClockException e){
            testMessage6 = e.getMessage();
        }

        Assertions.assertTrue(testMessage6.equals(SpeakingClockService.USER_TIME_NOT_VALID_MESSAGE));

        String testMessage7 = "";
        try {
            speakingClockService.validateUserTimeIn24HourFormat("12:61");
        } catch(ClockException e){
            testMessage7 = e.getMessage();
        }

        Assertions.assertTrue(testMessage7.equals(SpeakingClockService.USER_TIME_NOT_VALID_MESSAGE));
    }
}
