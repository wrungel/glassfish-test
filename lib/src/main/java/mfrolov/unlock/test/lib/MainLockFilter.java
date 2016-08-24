package mfrolov.unlock.test.lib;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainLockFilter {

    private static final Logger LOG = LoggerFactory.getLogger(MainLockFilter.class);

    @Inject
    private TransactionalLockReleaser transactionalLockReleaser;

    @Inject
    private Event<ReentrantBlockingLock> reentrantBlockingLockEvent;

    void unlock(@Observes(during = TransactionPhase.AFTER_SUCCESS) ReentrantBlockingLock equipmentLock) {
        // new tx required because the original transactional is gone
        transactionalLockReleaser.unlockInNewTransaction(equipmentLock);
    }

    void unlockAfterFailure(@Observes(during = TransactionPhase.AFTER_FAILURE) ReentrantBlockingLock equipmentLock) {
        // new tx required because the original transactional is gone
        transactionalLockReleaser.unlockInNewTransaction(equipmentLock);
    }

    public void fireEvent() {
        ReentrantBlockingLock reentrantBlockingLock = new ReentrantBlockingLock();
        LOG.info("-- fireEvent {}", reentrantBlockingLock);
        reentrantBlockingLockEvent.fire(reentrantBlockingLock);
    }

}
