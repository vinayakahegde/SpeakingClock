package com.example.SpeakingClock.domain;

public class Error {

    private Integer errorCode;

    private String errorMessage;

    public Integer getErrorCode(){
        return this.errorCode;
    }

    public String getErrorMessage(){
        return this.errorMessage;
    }

    public void setErrorCode(Integer errorCode){
        this.errorCode = errorCode;
    }

    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }
}
