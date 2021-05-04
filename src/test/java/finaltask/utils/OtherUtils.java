package finaltask.utils;

import aquality.selenium.browser.AqualityServices;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OtherUtils {

    private static int timeError = 1000;

    public static void deletedFile(String name) {
        String pngImageName = null;
        try {
            pngImageName = String.format("%s.%s", name, ConfigFileReader.getFormatScreenShot());
            String path = OtherUtils.class.getClassLoader().getResource(pngImageName).getFile().substring(1);
            Files.delete(Paths.get(path));
        } catch (Exception e) {
            Logger.error(pngImageName + " has not been deleted");
        }
    }

    public static void takeScreenShot(String name) {
        try {
            File screenshot = AqualityServices.getBrowser().getDriver().getScreenshotAs(OutputType.FILE);
            String pathDir = OtherUtils.class.getClassLoader().getResource("").getFile().substring(1);
            String path = String.format("%s%s.%s", pathDir, name, ConfigFileReader.getFormatScreenShot());
            FileUtils.copyFile(screenshot, new File(Files.createFile(Paths.get(path)).toString()));
        } catch (IOException e) {
            Logger.error("ScreenShot has not been deleted");
        }
    }

    public static String pngToBase64(String imageName) {
        return toBase64(fileToByteArray(imageName).getBytes());
    }

    public static String fileToByteArray(String imageName) {
        String pngImageName = String.format("%s.%s", imageName, ConfigFileReader.getFormatScreenShot());
        String path = OtherUtils.class.getClassLoader().getResource(pngImageName).getFile();
        String result = null;
        try {
            result = new String(FileUtils.readFileToByteArray(new File(path)));
        } catch (IOException e) {
            Logger.error(imageName + "has not been converted to byte array");
        }
        return result;
    }

    public static String toBase64(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    public static String generateSID() {
        return new Date().getTime() + "";
    }

    public static String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat(ConfigFileReader.getDateFormat());
        return format.format(new Date());
    }

    public static String getHtmlCurrentPage() {
        return AqualityServices.getBrowser().getDriver().getPageSource();
    }

    public static boolean equalsTime(String time1, String time2, String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = format.parse(time1);
            date2 = format.parse(time2);
        } catch (ParseException e) {
            Logger.error("date has not been parsed");
        }
        return Math.abs(date1.getTime() - date2.getTime()) < timeError;
    }

    public static String getUrlToJsMethod(String pathToJsMethods, String methodName) {
        URL url = null;
        try {
            url = new URL(ConfigFileReader.getApplicationUrl());
            url.openConnection();
        } catch (IOException e) {
            Logger.error("Url to ss Methods has not been got");
        }
        return url.toString().replaceAll(url.getPath(), pathToJsMethods) + methodName;
    }
}
