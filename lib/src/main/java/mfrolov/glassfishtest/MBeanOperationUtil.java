package mfrolov.glassfishtest;

import java.lang.management.ManagementFactory;
import java.util.Arrays;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

public final class MBeanOperationUtil {

    /** start JMS consumption for unpatched genericra (Glassfish) */
    public static final MBeanOperationUtil.MBeanOperation START_JMS_CONSUMPTION = new MBeanOperationUtil.MBeanOperation("com.sun.genericra:name=Monitoring,category=InboundResources", "startConsumption");

    /**
     * Contains mbean operation descriptor that helps to define immutable reusable operations
     * descriptors.
     *
     * @author alech
     * @since GLOBE 2.2
     *
     */
    public static final class MBeanOperation {

        private final String mbeanName;
        private final String operation;
        private final String[] signature;

        /**
         * Constructor.
         *
         * @param mbeanName
         *            MBean containing operation
         * @param operation
         *            operation (method) name
         * @param signature
         *            operation signature containing array of fully qualified class names
         */
        public MBeanOperation(String mbeanName, String operation, String[] signature) {
            this.mbeanName = mbeanName;
            this.operation = operation;
            this.signature = Arrays.copyOf(signature, signature.length);
        }

        /**
         * Constructor for non-arg operation.
         *
         * @param mbeanName
         *            MBean containing operation
         * @param operation
         *            operation (method) name
         */
        public MBeanOperation(String mbeanName, String operation) {
            this(mbeanName, operation, new String[] {});
        }

        /**
         * @return the mbeanName
         */
        public String getMbeanName() {
            return mbeanName;
        }

        /**
         * @return the operation
         */
        public String getOperation() {
            return operation;
        }

        /**
         * @return the signature
         */
        public String[] getSignature() {
            return Arrays.copyOf(signature, signature.length);
        }
    }

    private MBeanOperationUtil() {
    }

    /**
     * Invokes non argument management operation described by <code>operation</code>.
     *
     * @param operation
     *            descriptor for mbean operation
     * @return result
     */
    public static Object invoke(MBeanOperation operation) {
        return invoke(operation.getMbeanName(), operation.getOperation(), operation.getSignature(), new Object[] {});
    }

    /**
     * Invokes management operation described by <code>operation</code> with arguments
     * <code>args</code>.
     *
     * @param operation
     *            descriptor for mbean operation
     * @param args
     *            operation arguments
     * @return result
     */
    public static Object invoke(MBeanOperation operation, Object[] args) {
        return invoke(operation.getMbeanName(), operation.getOperation(), operation.getSignature(), args);
    }

    /**
     * Invokes management operation <code>operation</code> with signature <code>signature</code>
     * on mbean with name <code>mbeanName</code> using arguments <code>args</code>.
     *
     * @param mbeanName
     *            MBean containing operation
     * @param operation
     *            operation (method) name
     * @param signature
     *            operation signature containing array of fully qualified class names
     * @param args
     *            operation arguments
     * @return result
     */
    public static Object invoke(String mbeanName, String operation, String[] signature, Object[] args) {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        try {
            return mBeanServer.invoke(new ObjectName(mbeanName), operation, args, signature);
        } catch (InstanceNotFoundException mbeanNotFoundException) {
            throw new MBeanNotFoundExeption(mbeanName, mbeanNotFoundException);
        } catch (MalformedObjectNameException | ReflectionException | MBeanException otherException) {
            throw new RuntimeException("Error invoking operation on mbean: " + mbeanName + ", operation: " + operation + ", signature: " + Arrays.toString(signature) + ", with arguments: " + Arrays.toString(args), otherException);
        }
    }

    /**
     * @param mbeanOperation
     *            mbean operation whose mbean is checked
     * @return true if the mbean from the given operation exists, false otherwise
     */
    public static boolean isMBeanRegistered(MBeanOperation mbeanOperation) {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        try {
            return mBeanServer.isRegistered(new ObjectName(mbeanOperation.mbeanName));
        } catch (MalformedObjectNameException exc) {
            throw new RuntimeException("Invalid mbean name: " + mbeanOperation.mbeanName, exc);
        }
    }
}
