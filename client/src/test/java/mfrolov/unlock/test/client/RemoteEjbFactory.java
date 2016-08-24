package mfrolov.unlock.test.client;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class RemoteEjbFactory {

    static final String GLOBE_SYNC_JNDI_PREFIX = "java:global/GlobeUnlockTest/";

    private RemoteEjbFactory() {
        // Utility class
    }

    /**
     * Same as {@link #lookup(String, Class) lookup(ejbName, remoteInterface)} but <code>ejbName</code> is calculated
     * from <code>remoteInterface</code> parameter.
     */
    public static <T> T lookup(Class<T> remoteInterface) {
        String ejbName = remoteInterface.getSimpleName() + "Ejb";
        return lookup(ejbName, remoteInterface);
    }

    public static <T> T lookup(String ejbName, Class<T> remoteInterface) {
        String JNDI_NAME = GLOBE_SYNC_JNDI_PREFIX + ejbName;
        try {
            Properties properties = new Properties();
            // some times the default port 3700 is different (for example if it is already used).
            //properties.put("org.omg.CORBA.ORBInitialPort", "49792");
            return remoteInterface.cast(new InitialContext(properties).lookup(JNDI_NAME));
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
}
