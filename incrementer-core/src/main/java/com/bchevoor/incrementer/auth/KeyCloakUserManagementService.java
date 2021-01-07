package com.bchevoor.incrementer.auth;

import com.bchevoor.incrementer.User;

/**
 * An implementation of {@link UserManagementService} backed by a KeyCloak server.
 */
public class KeyCloakUserManagementService implements UserManagementService {

    @Override
    public User authenticate(String apiKey) throws AuthenticationFailureException {
        // Will not implement this for the interview problem, but stubbed out for an example.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public User createNewUser(String email, String password) throws UserAlreadyExistsException {
        // Will not implement this for the interview problem, but stubbed out for an example.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean deleteUser(String apiKey) throws AuthenticationFailureException {
        // Will not implement this for the interview problem, but stubbed out for an example.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
