package finaltask.utils;

import org.apache.log4j.LogManager;
import java.io.*;

public class Logger {

    private static String logName = "log.txt";

    public static final org.apache.log4j.Logger LOGGER = LogManager.getLogger(Logger.class);

    public static void error(Object message) {
        LOGGER.error(message);
    }

    public static void info(Object message) {
        LOGGER.info(message);
    }

    public static String getLogCurrentTest() {
        String path = Logger.class.getClassLoader().getResource(logName).getFile();
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            Logger.error("current log has not been got");
        }
        return result.toString();
    }

    public static boolean isEqualsLog(String log1 ,String log2) {
        String currentLog = log1.replaceAll("\n", " ").trim();
        return currentLog.equals(log2.trim());
    }
}
