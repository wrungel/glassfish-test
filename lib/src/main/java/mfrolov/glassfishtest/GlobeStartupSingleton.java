package mfrolov.glassfishtest;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.slf4j.bridge.SLF4JBridgeHandler;

@Singleton
@Startup
public class GlobeStartupSingleton {

    @PostConstruct
    public void initialize() {

        Logger logger = Logger.getLogger(GlobeStartupSingleton.class.getName());
        logger.info("Redirecting all java.util.Logger logs to SLF4J.");
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        logger.info("java.util.Logger logs redirected to SLF4J");


        if (MBeanOperationUtil.isMBeanRegistered(MBeanOperationUtil.START_JMS_CONSUMPTION)) {
            // this works with original genericra (Glassfish)
            MBeanOperationUtil.invoke(MBeanOperationUtil.START_JMS_CONSUMPTION);
        }
    }
}
