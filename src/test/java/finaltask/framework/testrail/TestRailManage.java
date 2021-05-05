package finaltask.framework.testrail;

import aquality.selenium.browser.AqualityServices;
import finaltask.framework.jdbc.SQLController;
import finaltask.utils.OtherUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.ITestContext;
import org.testng.ITestResult;
import finaltask.utils.ConfigFileReader;
import finaltask.utils.Logger;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class TestRailManage {
    public static APIClient CLIENT;

    public static void beforeMethod(ITestContext ctx, Method method) {
        try {
            long sectionId = (long) ctx.getAttribute("sectionId");
            long caseId = addCase(sectionId, method.getName());
            ctx.setAttribute("caseId", caseId);
            ctx.setAttribute("browser", AqualityServices.getBrowser().getBrowserName().name());
        } catch (APIException | IOException e) {
            Logger.error("Test case has not been added");
        }
    }

    public static void afterMethod(ITestResult result, ITestContext ctx, Method method) {
        try {
            long runId = (long) ctx.getAttribute("runId");
            long caseId = (long) ctx.getAttribute("caseId");
            addResultForCase(runId, caseId, result);
            String screenShotName = method.getName();
            long resultId = getResultsIdForCase(runId, caseId);
            addAttachmentToResult(resultId, screenShotName);
            OtherUtils.deletedFile(screenShotName);
        } catch (IOException | APIException e) {
            Logger.error("Data has not been sent to the test rail");
        }
    }

    public static void setTestContext(ITestContext ctx) {
        try {
            String projectId = ConfigFileReader.getProjectIdTestRail();
            long suiteId = addSuite(ConfigFileReader.getSuiteName(), projectId);
            long sectionId = addSection(ConfigFileReader.getSectionName(), suiteId, projectId);
            long runId = addRun(projectId, suiteId, true);
            String buildNumber = SQLController.getIncreaseMaxValueFromTable("build_number", "session");
            String sessionId = SQLController.getIncreaseMaxValueFromTable("id", "session");
            ctx.setAttribute("runId", runId);
            ctx.setAttribute("suiteId", suiteId);
            ctx.setAttribute("sectionId", sectionId);
            ctx.setAttribute("SID", OtherUtils.generateSID());
            ctx.setAttribute("buildNumber", buildNumber);
            ctx.setAttribute("sessionId", sessionId);
        } catch (IOException | APIException e) {
            Logger.error("Test Context has not been added");
        }
    }

    public static void addResultForCase(long runId, long caseId, ITestResult result) throws IOException, APIException {
        Map data = new HashMap();
        if (result.isSuccess()) {
            data.put("status_id", TestResult.PASSED.STATUS);
        } else {
            data.put("status_id", TestResult.FAILED.STATUS);
        }
        data.put("comment", Logger.getLogCurrentTest());
        CLIENT.sendPost(String.format("add_result_for_case/%s/%s", runId, caseId), data);
    }

    public static long addSuite(String name, String idProject) throws IOException, APIException {
        Map data = new HashMap();
        data.put("name", name);
        return (long) ((JSONObject) CLIENT.sendPost(String.format("add_suite/%s", idProject), data)).get("id");
    }

    public static long addRun(String projectId, long suiteId, boolean includeAll) throws IOException, APIException {
        Map data = new HashMap();
        data.put("include_all", includeAll);
        data.put("suite_id", suiteId);
        return (long) ((JSONObject) CLIENT.sendPost(String.format("add_run/%s", projectId), data)).get("id");
    }

    public static long addSection(String name, long suiteId, String projectId) throws IOException, APIException {
        Map data = new HashMap();
        data.put("name", name);
        data.put("suite_id", suiteId);
        return (long) ((JSONObject) CLIENT.sendPost(String.format("add_section/%s", projectId), data)).get("id");
    }

    public static long addCase(long sectionId, String title) throws IOException, APIException {
        Map data = new HashMap();
        data.put("title", title);
        return (long) ((JSONObject) CLIENT.sendPost(String.format("add_case/%s", sectionId), data)).get("id");
    }

    public static void addAttachmentToResult(long resultId, String fileName) throws IOException, APIException {
        String path = TestRailManage.class.getClassLoader().getResource(String.format("%s.%s", fileName, ConfigFileReader.getFormatScreenShot())).getFile();
        CLIENT.sendPost(String.format("add_attachment_to_result/%s", resultId), path);
    }

    public static long getResultsIdForCase(long runId, long caseId) throws IOException, APIException {
        JSONArray arr = (JSONArray) CLIENT.sendGet(String.format("get_results_for_case/%s/%s", runId, caseId));
        JSONObject obj = (JSONObject) arr.get(0);
        return (long) obj.get("id");
    }

    public static void logIn() {
        CLIENT = new APIClient(ConfigFileReader.getURLTestRail());
        CLIENT.setUser(ConfigFileReader.getLoginTestRail());
        CLIENT.setPassword(ConfigFileReader.getPasswordTestRail());
    }
}