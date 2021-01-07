package com.bchevoor.incrementer.web;

public class CreateNewUserResponseBody {

    private String email;
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }

    public String getEmail() {
        return email;
    }

    public CreateNewUserResponseBody setApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public CreateNewUserResponseBody setEmail(String email) {
        this.email = email;
        return this;
    }
}
