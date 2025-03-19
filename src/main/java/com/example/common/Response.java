package com.example.common;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private String message;
    private String TechnicalMessage;
    private Object data;

    public Response(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public Response(String message, String technicalMessage, Object data) {
        this.message = message;
        TechnicalMessage = technicalMessage;
        this.data = data;
    }

    public Response(Object data) {
        this.data = data;
    }

    public Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getTechnicalMessage() {
        return TechnicalMessage;
    }

    public void setTechnicalMessage(String technicalMessage) {
        TechnicalMessage = technicalMessage;
    }

    @Override
    public String toString() {
        return "message='" + message + '\'' +
                ", TechnicalMessage='" + TechnicalMessage + '\'' +
                ", data=" + data;
    }
}
