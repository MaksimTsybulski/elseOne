package finaltask.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

public class ConfigFileReader {

    private static Properties properties;
    private static String propertyFileName = "configuration.properties";

    public static void init() {
        String propertyFilePath = ConfigFileReader.class.getClassLoader().getResource(propertyFileName).getFile();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(propertyFilePath.replaceAll("%20", " ")))) {
            properties = new Properties();
            properties.load(reader);
        } catch (Exception e) {
            Logger.error(e.getMessage());
        }
    }

    public static String getApplicationUrl() {
        String url = properties.getProperty("url");
        if (url != null) return String.format(url, getLogin(), getPassword());
        else throw new RuntimeException("Application URL not specified in the Configuration.properties file.");
    }

    public static String getLogin() {
        String login = properties.getProperty("login");
        if (login != null) return login;
        else throw new RuntimeException("login not specified in the Configuration.properties file.");
    }

    public static String getPassword() {
        String password = properties.getProperty("password");
        if (password != null) return password;
        else throw new RuntimeException("password not specified in the Configuration.properties file.");
    }

    public static String getUrlApi() {
        String urlApi = properties.getProperty("urlApi");
        if (urlApi != null) return urlApi;
        else throw new RuntimeException("urlApi not specified in the Configuration.properties file.");
    }

    public static String getVariant() {
        String variant = properties.getProperty("variant");
        if (variant != null) return variant;
        else throw new RuntimeException("variant not specified in the Configuration.properties file.");
    }

    public static String getURLJDBS() {
        String URLJDBS = properties.getProperty("urlJdbs");
        if (URLJDBS != null) return URLJDBS;
        else throw new RuntimeException("URLJDBS not specified in the Configuration.properties file.");
    }

    public static String getLoginJDBC() {
        String loginJDBC = properties.getProperty("loginJdbs");
        if (loginJDBC != null) return loginJDBC;
        else throw new RuntimeException("loginJDBC not specified in the Configuration.properties file.");
    }

    public static String getPasswordJDBS() {
        String passwordJDBS = properties.getProperty("passwordJdbs");
        if (passwordJDBS != null) return passwordJDBS;
        else throw new RuntimeException("passwordJDBS not specified in the Configuration.properties file.");
    }

    public static String getFormatScreenShot() {
        String formatScreenShot = properties.getProperty("formatScreenShot");
        if (formatScreenShot != null) return formatScreenShot;
        else throw new RuntimeException("format Screen Shot not specified in the Configuration.properties file.");
    }

    public static String getSuiteName() {
        String suiteName = properties.getProperty("suiteName");
        if (suiteName != null) return suiteName;
        else throw new RuntimeException("suite name not specified in the Configuration.properties file.");
    }

    public static String getSectionName() {
        String sectionName = properties.getProperty("sectionName");
        if (sectionName != null) return sectionName;
        else throw new RuntimeException("section name not specified in the Configuration.properties file.");
    }

    public static String getURLTestRail() {
        String URLTestRail = properties.getProperty("URLTestRail");
        if (URLTestRail != null) return URLTestRail;
        else throw new RuntimeException("URL TestRail not specified in the Configuration.properties file.");
    }

    public static String getLoginTestRail() {
        String loginTestRail = properties.getProperty("loginTestRail");
        if (loginTestRail != null) return loginTestRail;
        else throw new RuntimeException("projectId not specified in the Configuration.properties file.");
    }

    public static String getPasswordTestRail() {
        String passwordTestRail = properties.getProperty("passwordTestRail");
        if (passwordTestRail != null) return passwordTestRail;
        else throw new RuntimeException("projectId not specified in the Configuration.properties file.");
    }

    public static String getProjectIdTestRail() {
        String projectId = properties.getProperty("projectIdTestRail");
        if (projectId != null) return projectId;
        else throw new RuntimeException("projectId not specified in the Configuration.properties file.");
    }

    public static String getEnv() {
        String env = properties.getProperty("env");
        if (env != null) return env;
        else throw new RuntimeException("projectId not specified in the Configuration.properties file.");
    }

    public static String getDateFormat() {
        String dateFormat = properties.getProperty("dateFormat");
        if (dateFormat != null) return dateFormat;
        else throw new RuntimeException("projectId not specified in the Configuration.properties file.");
    }

    public static String getDurationFormat() {
        String dateFormat = properties.getProperty("durationFormat");
        if (dateFormat != null) return dateFormat;
        else throw new RuntimeException("projectId not specified in the Configuration.properties file.");
    }
}