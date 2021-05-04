package finaltask.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

public class SQLRequestReader {

    private static Properties properties;
    private static String propertyFileName = "SQLRequest.properties";

    public static void init(){
        String propertyFilePath = ConfigFileReader.class.getClassLoader().getResource(propertyFileName).getFile();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(propertyFilePath.replaceAll("%20"," ")))) {
            properties = new Properties();
            properties.load(reader);
        } catch (Exception e) {
            Logger.error("ConfigFileReader has not been initial");
        }
    }

    public static String addTest() {
        String addProject = properties.getProperty("addTest");
        if (addProject != null) return addProject;
        else throw new RuntimeException("addProject not specified in the Configuration.properties file.");
    }

    public static String addSession() {
        String addSession = properties.getProperty("addSession");
        if (addSession != null) return addSession;
        else throw new RuntimeException("addSession not specified in the Configuration.properties file.");
    }

    public static String addAttachment() {
        String addAttachment = properties.getProperty("addAttachment");
        if (addAttachment != null) return addAttachment;
        else throw new RuntimeException("addAttachment not specified in the Configuration.properties file.");
    }
    public static String addLog() {
        String addLog = properties.getProperty("addLog");
        if (addLog != null) return addLog;
        else throw new RuntimeException("addLog not specified in the Configuration.properties file.");
    }

    public static String getMaxvalue() {
        String maxValue = properties.getProperty("maxValue");
        if (maxValue != null) return maxValue;
        else throw new RuntimeException("addLog not specified in the Configuration.properties file.");
    }
}
