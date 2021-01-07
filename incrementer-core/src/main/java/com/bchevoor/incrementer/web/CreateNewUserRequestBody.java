package com.bchevoor.incrementer.web;

public class CreateNewUserRequestBody {

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public CreateNewUserRequestBody setEmail(String email) {
        this.email = email;
        return this;
    }

    public CreateNewUserRequestBody setPassword(String password) {
        this.password = password;
        return this;
    }
}
