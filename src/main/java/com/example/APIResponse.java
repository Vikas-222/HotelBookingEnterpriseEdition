package com.example;

public class APIResponse {

    private String error;
    private Object object;

    public APIResponse(Object object) {
        this.object = object;
    }

    public APIResponse(String error) {
        this.error = error;
    }

    public APIResponse(String error, Object object) {
        this.error = error;
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "error='" + error + '\'' +
                ", object=" + object;
    }
}
