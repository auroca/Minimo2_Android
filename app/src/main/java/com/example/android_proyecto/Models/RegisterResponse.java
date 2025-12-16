package com.example.android_proyecto.Models;

public class RegisterResponse {
    private boolean ok;
    private String message;

    public RegisterResponse() {}

    public boolean isOk() { return ok; }
    public void setOk(boolean ok) { this.ok = ok; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
