package resourceManager;

import java.util.concurrent.locks.ReentrantLock;

public class FullResourceManager extends BasicResourceManager {

    ReentrantLock lock = new ReentrantLock();
    /**
     * Set the resource and initialise the numbers of waiting processes, and the number of users, to zero.
     *
     * @param resource the resource managed by this manager
     * @param maxUses  the maximum number of uses permitted for this manager's resource.
     */
    public FullResourceManager(Resource resource, int maxUses) {
        super(resource, maxUses);
    }

    @Override
    public void requestResource(int priority) throws ResourceError {
        increaseNumberWaiting(priority);
        lock.lock();
        try {
            if(!resourceIsExhausted()){
                useResource(20);
            }
            decreaseNumberWaiting(priority);
            releaseResource();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int releaseResource() throws ResourceError {
        int priorityReturn = getRandomPriority();
        return priorityReturn;
    }
}
