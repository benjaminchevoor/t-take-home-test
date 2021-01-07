package com.bchevoor.incrementer.data;

import com.bchevoor.incrementer.User;

/**
 * Represents the data layer for persisting our user's data.
 */
public interface IncrementingData {

    /**
     * Returns the current integer stored for a specific user.
     * <p>
     * If the user current does not have any value stored, the first value returned by this method will be 0.
     *
     * @param user the user to increment the integer for
     * @return the current integer for the provided user
     * @throws IllegalArgumentException if the provided {@link User} is null
     */
    int currentIntegerForUser(User user);

    /**
     * Increments the integer stored for a specific user.
     * <p>
     * If the user current does not have any value stored, the first value returned by this method will be 1.
     *
     * @param user the user to increment the integer for
     * @return the next integer for the provided user
     * @throws IllegalArgumentException if the provided {@link User} is null
     */
    int incrementIntegerForUser(User user);

    /**
     * Sets the current integer value stored for a specific user.
     *
     * @param user the user to increment the integer for
     * @throws IllegalArgumentException if the provided {@link User} is null or if the provided integer value is
     *                                  negative.
     */
    void putIntegerForUser(User user, int val);
}
