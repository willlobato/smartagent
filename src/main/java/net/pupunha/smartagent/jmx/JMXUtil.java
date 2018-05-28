package net.pupunha.smartagent.jmx;

import javax.management.*;
import java.lang.management.ManagementFactory;

public final class JMXUtil {

    public static final String MBEAN_NAME = "net.pupunha.smartagent";

    public static String getObjectName(String classFullName, String methodFullName) {
        return JMXUtil.MBEAN_NAME+":type="+classFullName+",name="+methodFullName;
    }

    public static void registerMetricMBean(String classFullName, String methodFullName) throws JMException {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        Object mbean = new Metric();
        ObjectName objectName = new ObjectName(getObjectName(classFullName, methodFullName));
        mbs.registerMBean(mbean, objectName);
    }

    public static synchronized void updateMetric(String className, String methodFullName, long elapsedTime) throws JMException {
        final String objectNameStr = getObjectName(className, methodFullName);
        javax.management.MBeanServer mbs = java.lang.management.ManagementFactory.getPlatformMBeanServer();
        javax.management.ObjectName objectName = new javax.management.ObjectName(objectNameStr);
        Object $_Hit = mbs.getAttribute(objectName, "Hit");
        mbs.setAttribute(objectName, new javax.management.Attribute("Hit", Long.parseLong($_Hit.toString()) + 1));

        Object elapsedTimeTotal = mbs.getAttribute(objectName, "ElapsedTimeTotal");
        mbs.setAttribute(objectName, new javax.management.Attribute("ElapsedTimeTotal", Long.parseLong(elapsedTimeTotal.toString()) + elapsedTime));
    }

}
