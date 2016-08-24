package mfrolov.unlock.test.lib;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class TransactionalLockReleaser {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionalLockReleaser.class);

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void unlockInNewTransaction(ReentrantBlockingLock equipmentLock) {
        LOG.info("-- Hello from TransactionalLockReleaser, equipmentLock: {}", equipmentLock);
    }
}
