package mfrolov.glassfishtest;

import javax.management.InstanceNotFoundException;

public class MBeanNotFoundExeption extends RuntimeException {

    /**
     * @param mbeanName
     *            name of the MBean
     * @param originalException
     */
    public MBeanNotFoundExeption(String mbeanName, InstanceNotFoundException originalException) {
        super("MBean " + mbeanName + " not found", originalException);
    }

}
