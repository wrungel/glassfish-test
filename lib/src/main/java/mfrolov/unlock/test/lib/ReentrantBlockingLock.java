package mfrolov.unlock.test.lib;

import java.util.UUID;

public class ReentrantBlockingLock {

    private final UUID uuid = UUID.randomUUID();

    @Override
    public String toString() {
        return uuid.toString();
    }
}
