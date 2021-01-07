package com.bchevoor.incrementer.auth;

import com.bchevoor.incrementer.User;

/**
 * A service for managing {@link User} instances. Provides the ability to create and delete users, and authenticate
 * users back into the service.
 */
public interface UserManagementService {

    /**
     * Attempts to authenticate a {@link User} by their API key. If successful, the {@link User} will be returned.
     * <p>
     * If the authentication is unsuccessful a {@link AuthenticationFailureException} will be throw.
     *
     * @param apiKey the API key owned by the user to be authenticated
     * @throws AuthenticationFailureException if the user cannot be authenticated
     * @return the {@link User} if successful
     */
    User authenticate(String apiKey) throws AuthenticationFailureException;

    /**
     * Create a new {@link User} on this service.
     * <p>
     * The provided email and password must not be null.
     * <p>
     * If there already is a user registered with the provided email, this throws a {@link UserAlreadyExistsException}.
     *
     * @param email    the user's email
     * @param password the user's password
     * @return the newly created {@link User}
     */
    User createNewUser(String email, String password) throws UserAlreadyExistsException;

    /**
     * Attempts to delete a {@link User} by their API key. If successful, the operation returns true. False otherwise.
     *
     * @param apiKey the API key owned by the user to be deleted
     * @throws AuthenticationFailureException if the user cannot be authenticated
     * @return true if the user is successfully deleted, false otherwise
     */
    boolean deleteUser(String apiKey) throws AuthenticationFailureException;

}
