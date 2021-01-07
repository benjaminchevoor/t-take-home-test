package com.bchevoor.incrementer.data;

import com.bchevoor.incrementer.User;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * An in-memory implementation of {@link IncrementingData}, meaning that the data backed by this layer is in-memory
 * and will not be persisted across service restarts.
 * <p>
 * If you are interested in a persisting data layer, see {@link RedisIncrementingData}.
 */
@Service
class InMemoryIncrementingData implements IncrementingData {

    private final Map<User, AtomicInteger> userIntegerCounterMap = new ConcurrentHashMap<>();

    @Override
    public int currentIntegerForUser(User user) {
        AtomicInteger userValue = getAtomicIntegerForUser(user);
        return userValue.get();
    }

    private AtomicInteger getAtomicIntegerForUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("The provided user cannot be null");
        }

        return userIntegerCounterMap.computeIfAbsent(user, user1 -> new AtomicInteger(0));
    }

    @Override
    public int incrementIntegerForUser(User user) {
        AtomicInteger userValue = getAtomicIntegerForUser(user);
        return userValue.incrementAndGet();
    }

    @Override
    public void putIntegerForUser(User user, int val) {
        AtomicInteger userValue = getAtomicIntegerForUser(user);
        userValue.set(val);
    }
}
