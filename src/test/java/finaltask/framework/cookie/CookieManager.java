package finaltask.framework.cookie;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import org.openqa.selenium.Cookie;

public class CookieManager {

    private static Browser browser = AqualityServices.getBrowser();

    public static void addCookie(String name, String value) {
        browser.getDriver().manage().addCookie(new Cookie(name,value));
    }

    public static void deleteAllCookie() {
        browser.getDriver().manage().deleteAllCookies();
    }
}
