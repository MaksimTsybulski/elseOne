package finaltask.project.tests;

import aquality.selenium.browser.AqualityServices;
import finaltask.utils.Logger;
import finaltask.utils.OtherUtils;
import kong.unirest.Unirest;
import java.awt.*;
import java.awt.event.InputEvent;

public class BrowserActions {

    private static String pathToJsMethods = "/web/resources/js/";
    private static String closeModal = "closeModal.js";
    private static int middleY = 400;
    private static int leftEdgeX = 0;
    private static int rightEdgeX = 2000;


    private BrowserActions() {
    }

    public static void waitPage() {
        AqualityServices.getBrowser().waitForPageToLoad();
    }

    public static void refresh() {
        AqualityServices.getBrowser().refresh();
    }

    public static void closePopUp() {
        String methodCode = Unirest.get(OtherUtils.getUrlToJsMethod(pathToJsMethods, closeModal)).asString().getBody();
        AqualityServices.getBrowser().getDriver().executeAsyncScript(methodCode);
    }

    public static void closePopUpRobot() {
        try {
            Robot robot = new Robot();
            robot.mouseMove(leftEdgeX, middleY);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseMove(rightEdgeX, middleY);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        } catch (AWTException e) {
            Logger.error("robot error");
        }
    }

    public static void goBack() {
        AqualityServices.getBrowser().goBack();
    }
}

