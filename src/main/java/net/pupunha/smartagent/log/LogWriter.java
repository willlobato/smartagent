package net.pupunha.smartagent.log;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LogWriter {

    public static void log(String logFile, String className, String methodFullName, long elapsedTime) throws IOException {

        StringBuilder log = new StringBuilder();
//        log.append("CurrentDate;ClassName;MethodFullName;ElapsedTime");
        log.append(className.replaceAll("[/]","."))
                .append(".").append(methodFullName.replaceAll("[/]",","))
                .append(";").append(elapsedTime);
        FileWriter fileWriter = new FileWriter(logFile, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(log.toString());
        printWriter.close();
    }

    public static void initLog(String logFile) throws IOException {
        StringBuilder log = new StringBuilder();
        log.append("MethodFullName;ElapsedTime");
        FileWriter fileWriter = new FileWriter(logFile, false);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(log.toString());
        printWriter.close();
    }

}
