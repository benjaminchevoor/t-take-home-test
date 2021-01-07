package com.bchevoor.incrementer.model;

import com.bchevoor.incrementer.User;
import com.bchevoor.incrementer.data.IncrementingData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The main class that is the model of the incrementing integer service.
 */
@Service
public class IncrementingService {

    @Autowired
    private IncrementingData incrementingData;

    /**
     * Returns the current integer stored for a specific user.
     * <p>
     * If the user current does not have any value stored, the first value returned by this method will be 0.
     *
     * @param user the user to increment the integer for
     * @return the current integer for the provided user
     * @throws IllegalArgumentException if the provided {@link User} is null
     */
    public int currentIntegerForUser(User user) {
        //this model is so simple we just call our data layer directly
        return incrementingData.currentIntegerForUser(user);
    }

    /**
     * Increments the integer stored for a specific user.
     * <p>
     * If the user current does not have any value stored, the first value returned by this method will be 1.
     *
     * @param user the user to increment the integer for
     * @return the next integer for the provided user
     * @throws IllegalArgumentException if the provided {@link User} is null
     */
    public int incrementIntegerForUser(User user) {
        //this model is so simple we just call our data layer directly
        return incrementingData.incrementIntegerForUser(user);
    }

    /**
     * Sets the current integer value stored for a specific user.
     *
     * @param user the user to increment the integer for
     * @throws IllegalArgumentException if the provided {@link User} is null or if the provided integer value is
     *                                  negative.
     */
    public void putIntegerForUser(User user, int val) {
        if (val < 0) {
            throw new IllegalArgumentException("Value must be non-negative");
        }

        incrementingData.putIntegerForUser(user, val);
    }
}
