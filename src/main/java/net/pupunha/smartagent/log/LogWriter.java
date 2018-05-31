package net.pupunha.smartagent.log;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LogWriter {

    public static synchronized void log(String logFile, String className, String methodFullName, long elapsedTime, Object[] args) throws IOException {
        StringBuilder log = new StringBuilder();

        className = className.replaceAll("[/]", ".");
        className += ".";
        className += methodFullName.replaceAll("[/]",",");

        StringBuilder params = new StringBuilder();
        if (args.length > 0) {
            for (int i=0; i<args.length; i++) {
                params.append(args[i].toString()).append(",");
            }
            params.delete(params.length()-1, params.length());
        }

        log.append(className).append(";").append(params.toString()).append(";").append(elapsedTime);
        FileWriter fileWriter = new FileWriter(logFile, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(log.toString());
        printWriter.close();
    }

    public static void initLog(String logFile) throws IOException {
        FileWriter fileWriter = new FileWriter(logFile, false);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println("MethodFullName;Parameters;ElapsedTime");
        printWriter.close();
    }

}
