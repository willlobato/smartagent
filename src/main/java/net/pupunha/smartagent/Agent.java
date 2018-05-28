package net.pupunha.smartagent;

import net.pupunha.smartagent.configuration.Configuration;
import net.pupunha.smartagent.configuration.MethodConfiguration;
import net.pupunha.smartagent.configuration.ReaderConfiguration;
import net.pupunha.smartagent.jmx.JMXUtil;
import net.pupunha.smartagent.log.LogWriter;

import javax.management.JMException;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.Set;

public class Agent {

    public static void premain(String agentArgs, Instrumentation inst) {

        ReaderConfiguration readerConfiguration = new ReaderConfiguration();
        Configuration configuration;
        try {
            configuration = readerConfiguration.read(agentArgs);
            LogWriter.initLog(configuration.getLogFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Set<String> classesFullName = configuration.getClassFullName().keySet();
        for (String classFullName : classesFullName) {
            Set<String> methods = configuration.getClassFullName().get(classFullName).getMethods().keySet();
            for (String method : methods) {

                MethodConfiguration methodConfiguration = configuration.getClassFullName()
                        .get(classFullName).getMethods().get(method);

                String parameterFullName = methodConfiguration != null ? methodConfiguration.getParameterFullName() : "()";
                String methodFullName = method + parameterFullName;
                try {
                    JMXUtil.registerMetricMBean(classFullName, methodFullName);
                } catch (JMException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        inst.addTransformer(new SmartTransformClass(configuration));
    }

}
