package finaltask.framework.jdbc;

import finaltask.project.model.TestQA;
import finaltask.utils.Logger;
import finaltask.utils.OtherUtils;
import finaltask.utils.SQLRequestReader;
import org.testng.ITestContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLController {
    private final static String PASSED = "1";

    public static String addTest(TestQA testQA, String projectId, String env, ITestContext ctx) {
        testQA.setStatus(PASSED);
        String testId = getIncreaseMaxValueFromTable("id", "test");
        String buildNumber = (String) ctx.getAttribute("buildNumber");
        String SID = (String) ctx.getAttribute("SID");
        String sessionId = (String) ctx.getAttribute("sessionId");
        addSession(sessionId, SID, testQA.getEndTime(), buildNumber);
        testQA.setEndTime(OtherUtils.getCurrentTime());
        try {
            PreparedStatement statement = SQLManager.getPreparedStatement(SQLRequestReader.addTest());
            setPreparedStatement(
                    statement, testId, testQA.getName(), testQA.getStatus(),
                    testQA.getMethod(), projectId, sessionId, testQA.getStartTime(),
                    testQA.getEndTime(), env, (String) ctx.getAttribute("browser")).executeUpdate();
        } catch (SQLException t) {
            Logger.error("Test has not been added");
        }
        String attachmentId = getIncreaseMaxValueFromTable("id", "attachment");
        addAttachment(attachmentId, OtherUtils.pngToBase64(testQA.getMethod()), ContentType.IMAGE_PNG, testId);
        addAttachment(Integer.parseInt(attachmentId) + 1 + "", OtherUtils.getHtmlCurrentPage(), ContentType.TEXT_HTML, testId);
        String logId = getIncreaseMaxValueFromTable("id", "log");
        addLog(logId, Logger.getLogCurrentTest(), testId);
        return testId;
    }

    private static void addSession(String id, String SID, String createTime, String buildNumber) {
        try {
            PreparedStatement statement = SQLManager.getPreparedStatement(SQLRequestReader.addSession());
            setPreparedStatement(statement, id, SID, createTime, buildNumber).executeUpdate();
        } catch (SQLException t) {
            Logger.error("Session has not been added");
        }
    }

    private static void addAttachment(String id, String content, ContentType type, String testId) {
        try {
            PreparedStatement statement = SQLManager.getPreparedStatement(SQLRequestReader.addAttachment());
            setPreparedStatement(statement, id, content, type.getType(), testId).executeUpdate();
        } catch (SQLException throwables) {
            Logger.error("Attachment has not been added");
        }
    }

    private static void addLog(String id, String content, String testId) {
        try {
            PreparedStatement statement = SQLManager.getPreparedStatement(SQLRequestReader.addLog());
            setPreparedStatement(statement, id, content, testId).executeUpdate();
        } catch (SQLException t) {
            Logger.error("Log has not been added");
        }
    }

    private static PreparedStatement setPreparedStatement(PreparedStatement statement, String... param) throws SQLException {
        for (int i = 0; i < param.length; i++) {
            statement.setString(i + 1, param[i]);
        }
        return statement;
    }

    public static String getIncreaseMaxValueFromTable(String columnName, String tableNme) {
        String result = null;
        try {
            ResultSet resultSet = SQLManager.executeQuery(String.format(SQLRequestReader.getMaxvalue(), columnName, tableNme));
            if (resultSet.next()) {
                result = resultSet.getString(1);
            }
        } catch (SQLException t) {
            Logger.error(String.format("max value(%s) from %s has not been got", columnName, tableNme));
        }
        return Integer.parseInt(result) + 1 + "";
    }
}
