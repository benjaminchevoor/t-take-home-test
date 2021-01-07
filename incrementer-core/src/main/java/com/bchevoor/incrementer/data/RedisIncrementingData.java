package com.bchevoor.incrementer.data;

import com.bchevoor.incrementer.User;

/**
 * An implementation of {@link IncrementingData} backed by a Redis database. The all relevant data needed by this
 * service will be stored within the Redis databased backed by this data layer. If this service is restarted the data
 * will remain intact since it is all persisted by the external database.
 *
 * If data persistence is not desired, then see {@link InMemoryIncrementingData}.
 */
class RedisIncrementingData implements IncrementingData {

    @Override
    public int currentIntegerForUser(User user) {
        // Will not implement this for the interview problem, but stubbed out for an example.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int incrementIntegerForUser(User user) {
        // Will not implement this for the interview problem, but stubbed out for an example.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void putIntegerForUser(User user, int val) {
        // Will not implement this for the interview problem, but stubbed out for an example.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
