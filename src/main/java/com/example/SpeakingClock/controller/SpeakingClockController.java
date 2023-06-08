package com.example.SpeakingClock.controller;

import com.example.SpeakingClock.domain.UserTimeInput;
import com.example.SpeakingClock.exception.ClockException;
import com.example.SpeakingClock.service.SpeakingClockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SpeakingClockController {

    @Autowired
    private SpeakingClockService speakingClockService;

    @RequestMapping(path = "/v1/currentTime", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> tellCurrentTimeIn24HourFormat(){
        String timeInTextFormat = speakingClockService.convertCurrentTimeTo24HourTextFormat();
        Map<String, Object> responseMap = new HashMap<String, Object>(1);
        responseMap.put("message", timeInTextFormat);

        ResponseEntity<Map<String, Object>> responseEntity =
                new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.OK);

        return responseEntity;
    }

    @RequestMapping(path = "/v1/userTime", method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> tellUserTimeIn24HourFormat
                                                    (@RequestBody UserTimeInput userTimeInput){

        Map<String, Object> responseMap = new HashMap<String, Object>(2);
        ResponseEntity<Map<String, Object>> responseEntity;
        try {
            String timeInTextFormat = speakingClockService.convertUserTimeTo24HourTextFormat(userTimeInput.getTime());
            responseMap.put("message", timeInTextFormat);

            responseEntity = new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.OK);
        } catch (ClockException e){
            responseMap.put("errorCode", e.getCode());
            responseMap.put("errorMessage", e.getMessage());

            responseEntity = new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return responseEntity;
    }
}
