package com.bchevoor.incrementer.auth;

import com.bchevoor.incrementer.User;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An implementation of {@link UserManagementService} which stores all users in-memory.
 */
@Service
public class InMemoryUserManagementService implements UserManagementService {

    private final Map<String, User> usersByEmail = new ConcurrentHashMap<>();
    private final Map<String, User> usersByApiKey = new ConcurrentHashMap<>();

    @Override
    public User authenticate(String apiKey) throws AuthenticationFailureException {
        if (apiKey == null) {
            throw new AuthenticationFailureException();
        }

        return usersByApiKey.get(apiKey);
    }

    @Override
    public User createNewUser(String email, String password) throws UserAlreadyExistsException {
        if (email == null || password == null) {
            throw new IllegalArgumentException("Email and password cannot be null");
        }
        if (usersByEmail.get(email) != null) {
            throw new UserAlreadyExistsException("User already registered with email: " + email);
        }

        // the password will be dropped here since we do not use it in this implementation right now, and it will be
        // insecure to store it in-memory
        User newUser = User.createNewUser(email);
        usersByApiKey.put(newUser.getApiKey(), newUser);
        usersByEmail.put(email, newUser);
        return newUser;
    }

    @Override
    public boolean deleteUser(String apiKey) throws AuthenticationFailureException {
        User user = authenticate(apiKey);
        if (user == null) {
            return false;
        }

        usersByApiKey.remove(user.getApiKey());
        usersByEmail.remove(user.getEmail());
        return true;
    }

}
