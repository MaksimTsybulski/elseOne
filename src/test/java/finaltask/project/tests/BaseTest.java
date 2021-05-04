package finaltask.project.tests;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import finaltask.utils.SQLRequestReader;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import finaltask.framework.testrail.TestRailManage;
import finaltask.utils.ConfigFileReader;
import java.lang.reflect.Method;

public class BaseTest {

    public BaseTest() {
    }

    @BeforeSuite
    public void createSuite(ITestContext ctx) {
        ConfigFileReader.init();
        SQLRequestReader.init();
        TestRailManage.logIn();
        TestRailManage.setTestContext(ctx);
    }

    @BeforeMethod
    protected void beforeMethod(ITestContext ctx, Method method) {
        ConfigFileReader.init();
        SQLRequestReader.init();
        getBrowser().goTo(ConfigFileReader.getApplicationUrl());
        getBrowser().maximize();
        getBrowser().waitForPageToLoad();
        TestRailManage.beforeMethod(ctx, method);
    }

    @AfterMethod
    public void afterTest(ITestResult result, ITestContext ctx, Method method) {
        TestRailManage.afterMethod(result, ctx, method);
        if (AqualityServices.isBrowserStarted()) {
            AqualityServices.getBrowser().quit();
        }
    }

    protected Browser getBrowser() {
        return AqualityServices.getBrowser();
    }
}
