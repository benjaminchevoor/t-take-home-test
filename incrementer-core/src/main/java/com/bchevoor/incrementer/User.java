package com.bchevoor.incrementer;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents our user of the service.
 */
public class User {

    /**
     * Creates a new {@link User}.
     *
     * @return a new {@link User} instance
     */
    public static User createNewUser(String email) {
        String apiKey = UUID.randomUUID().toString();
        return new User(apiKey)
                .setEmail(email);
    }

    private final String apiKey;
    private String email;

    private User(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return Objects.equals(apiKey, user.apiKey);
    }

    /**
     * Returns the universally unique ID for this user.
     *
     * @return the universally unique ID for this user.
     */
    public String getApiKey() {
        return apiKey;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public int hashCode() {
        return Objects.hash(apiKey);
    }

    private User setEmail(String email) {
        this.email = email;
        return this;
    }
}
