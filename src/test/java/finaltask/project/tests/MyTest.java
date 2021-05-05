package finaltask.project.tests;

import finaltask.framework.cookie.CookieManager;
import finaltask.framework.jdbc.SQLController;
import finaltask.framework.jdbc.SQLManager;
import finaltask.project.forms.*;
import finaltask.project.model.TestQA;
import finaltask.utils.*;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import finaltask.framework.rest.UniRestController;
import java.lang.reflect.Method;
import java.util.List;

import static org.testng.Assert.*;

public class MyTest extends BaseTest {

    private static String cookieName = "token";
    private static String projectName = "project26";
    private static String testName = "test for testing the web interface and checking the work of the api";
    private static int numberAttempts = 5;
    private static int waiting = 2;

    @Test
    public void testTestingWebInterface(ITestContext ctx, Method method) {
        TestQA testQA = new TestQA(testName, method.getName(), null, OtherUtils.getCurrentTime(), null, null);
        String variant = ConfigFileReader.getVariant();
        String token = UniRestController.getToken(variant);
        assertNotNull(token, "token is null");
        assertNotEquals(token, "", "token is empty");

        WelcomePage welcomePage = new WelcomePage();
        assertTrue(welcomePage.isOpened(), "welcome page has not been opened");

        CookieManager.addCookie(cookieName, token);
        BrowserActions.refresh();
        assertEquals(welcomePage.getFooterVariant(), variant, "the footer contains the wrong variant number");

        CookieManager.deleteAllCookie();
        BrowserActions.refresh();

        String nexageId = welcomePage.getNexageProjectId();

        welcomePage.openNexagePage();
        BrowserActions.waitPage();
        NexagePage nexagePage = welcomePage.getNexagePage();
        assertTrue(nexagePage.isOpened(), "Nexage page has not been opened");
        List<TestQA> testsFromPage = nexagePage.getListTestOnPage();
        List<TestQA> testsFromAPI = JSONUtils.getListTestFromJson(nexageId, numberAttempts);
        TestsUtils.sortByDescendingTime(testsFromAPI);
        assertTrue(TestsUtils.isDatesDescending(testsFromPage), "tests from the page were not descending by date");
        assertTrue(TestsUtils.isAllTestsEquals(testsFromPage, testsFromAPI), "tests don't match");

        BrowserActions.goBack();

        welcomePage.addProjectAndOpenPopUp();
        PopUpForm popUpForm = welcomePage.getPopUpForm();
        popUpForm.typeProjectName(projectName);
        popUpForm.saveProject();
        assertTrue(popUpForm.isSuccessfullySaved(), "project has not been saved");
//      BrowserActions.closePopUp();    ERROR
        BrowserActions.closePopUpRobot();
        assertTrue(popUpForm.modalWindowIsClosed(waiting), "model window has not been closed");
        BrowserActions.refresh();
        assertTrue(welcomePage.myProjectAdded(projectName), "my project has not been added");

        String projectId = welcomePage.getProjectId(projectName);
        welcomePage.openProjectByName(projectName);
        MyProjectPage myProjectPage = welcomePage.getMyProjectPage();
        assertTrue(myProjectPage.isOpened(), "my project page has not been opened");
        OtherUtils.takeScreenShot(method.getName());
        String testId = SQLController.addTest(testQA, projectId, ConfigFileReader.getEnv(), ctx);
        assertTrue(myProjectPage.testAdded(testId), "test has not been added");

        String currentLog = Logger.getLogCurrentTest();

        myProjectPage.openTestById(testId);
        TestPage testPage = myProjectPage.getTestPage();
        assertTrue(testPage.isOpened(), "test page has not been opened");
        assertEquals(testPage.getProjectName(), projectName, "project name has not been equals");
        assertEquals(testPage.getTestName(), testQA.getName(), "test name has not been equals");
        assertEquals(testPage.getMethodName(), testQA.getMethod(), "method name has not been equals");
        assertTrue(testQA.equalsStatus(testPage.getStatus()), "status has not been equals");
        assertTrue(OtherUtils.equalsTime(testPage.getStarTime(), testQA.getStartTime(), ConfigFileReader.getDateFormat()), "start time has not been equals");
        assertTrue(OtherUtils.equalsTime(testPage.getEndTime(), testQA.getEndTime(), ConfigFileReader.getDateFormat()), "end time has not been equals");
        assertTrue(OtherUtils.equalsTime(testPage.getDuration(), testQA.getDuration(), ConfigFileReader.getDurationFormat()), "duration has not been equals");
        assertEquals(testPage.getEnvironment(), ConfigFileReader.getEnv(), "env has not been equals");
        assertEquals(testPage.getBrowser(), ctx.getAttribute("browser"), "browser has not been equals");
        assertTrue(Logger.isEqualsLog(currentLog, testPage.getLogText()), "log has not been equals");
        String attachment = testPage.getDecodeAttachment();
        String expectedAttachment = OtherUtils.pngToBase64(method.getName());
        assertEquals(attachment, expectedAttachment, "attachments has not been equals");
        SQLManager.closeConnection();
    }
}
