package net.pupunha.smartagent;

import javassist.*;
import net.pupunha.smartagent.configuration.ClassConfiguration;
import net.pupunha.smartagent.configuration.Configuration;
import net.pupunha.smartagent.configuration.MethodConfiguration;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Map;

public class SmartTransformClass implements ClassFileTransformer {

    private Configuration configuration;

    public SmartTransformClass(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (configuration != null && configuration.getClassFullName().get(className) != null) {
            try {
                ClassPool cp = ClassPool.getDefault();
                cp.insertClassPath(new ByteArrayClassPath(className, classfileBuffer));

                String classReplace = className.replaceAll("[/]", ".");
                CtClass cc = cp.get(classReplace);

                ClassConfiguration classConfiguration = configuration.getClassFullName().get(className);
                Map<String, MethodConfiguration> methods = classConfiguration.getMethods();
                if (methods != null && !methods.isEmpty()) {
                    for (String method : methods.keySet()) {
                        MethodConfiguration methodConfiguration = methods.get(method);
                        if (methods.containsKey(method)) {
                            CtMethod ctMethod = null;
                            String parameterFullName = null;
                            boolean foundMethod = true;
                            try {
                                if (methodConfiguration != null) {
                                    String[] parameters = methodConfiguration.getParameters();
                                    if (parameters != null && parameters.length > 0) {
                                        if (!"".equals(parameters[0])) {
                                            CtClass[] ctClasses = new CtClass[parameters.length];
                                            for (int i=0; i<parameters.length; i++) {
                                                ctClasses[i] = cp.get(parameters[i]);
                                            }
                                            ctMethod = cc.getDeclaredMethod(method, ctClasses);
                                            parameterFullName = methodConfiguration.getParameterFullName();
                                        } else {
                                            ctMethod = cc.getDeclaredMethod(method);
                                            parameterFullName = "()";
                                        }
                                    } else {
                                        ctMethod = cc.getDeclaredMethod(method);
                                        parameterFullName = "()";
                                    }
                                } else {
                                    ctMethod = cc.getDeclaredMethod(method);
                                    parameterFullName = "()";
                                }
                            } catch (NotFoundException e) {
                                foundMethod = false;
                            }

                            if (foundMethod && ctMethod != null) {

                                final String methodFullName = method + parameterFullName;

                                ctMethod.addLocalVariable("$_elapsedTime", CtClass.longType);
                                ctMethod.insertBefore("$_elapsedTime = System.currentTimeMillis();");

                                StringBuilder sb = new StringBuilder("{");
                                sb.append("$_elapsedTime = System.currentTimeMillis() - $_elapsedTime;");
                                sb.append("net.pupunha.smartagent.jmx.JMXUtil.updateMetric(\"")
                                        .append(className).append("\", \"")
                                        .append(methodFullName)
                                        .append("\", $_elapsedTime);");
                                sb.append("net.pupunha.smartagent.log.LogWriter.log(\"")
                                        .append(configuration.getLogFile()).append("\", \"")
                                        .append(className).append("\", \"")
                                        .append(methodFullName).append("\", $_elapsedTime);");
                                sb.append("}");
                                ctMethod.insertAfter(sb.toString());
                            }
                        }
                    }
                }

                byte[] byteCode = cc.toBytecode();
                cc.detach();
                return byteCode;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}
