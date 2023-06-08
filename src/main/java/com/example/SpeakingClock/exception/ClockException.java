package com.example.SpeakingClock.exception;

public class ClockException extends Exception {

    private String message;

    private int code;

    public ClockException(){

    }

    public ClockException(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode(){
        return this.code;
    }

    public void setCode(int code){
        this.code = code;
    }

    public String getMessage(){
        return this.message;
    }

    public void setMessage(String message){
        this.message = message;
    }

}
