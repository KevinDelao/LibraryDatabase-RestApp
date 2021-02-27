package com.librarymanagment.springbootlibrary.model;

public class ErrorModel {
    public ErrorModel(){
        this.show = false;
    }

    public String getMessage() {
        return message;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String message;
    boolean show;
}
