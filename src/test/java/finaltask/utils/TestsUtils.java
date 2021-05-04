package finaltask.utils;

import finaltask.project.model.TestQA;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class TestsUtils {

    public static boolean isDatesDescending(List<TestQA> testsQA) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
        for (int i = 0; i < testsQA.size() - 1; i++) {
            Date currentDate;
            Date nextDate;
            try {
                currentDate = formatter.parse(testsQA.get(i).getStartTime());
                nextDate = formatter.parse(testsQA.get(i + 1).getStartTime());
                if (currentDate.before(nextDate)) return false;
            } catch (ParseException e) {
                Logger.error("Time has not been cast to format");
            }
        }
        return true;
    }

    public static boolean isAllTestsEquals(List<TestQA> testsFromPage, List<TestQA> testsFromAPI) {
        int minSize = Math.min(testsFromAPI.size(), testsFromPage.size());
        for (int i = 0; i < minSize; i++) {
            TestQA pageTest = testsFromPage.get(i);
            TestQA apiTest = testsFromAPI.get(i);
            if (!pageTest.equals(apiTest)) {
                Logger.error(String.format("these tests don't match: \n%s\n%s", pageTest, apiTest));
                return false;
            }
        }
        return true;
    }

    public static String getDuration(String start, String end) {
        SimpleDateFormat format = new SimpleDateFormat(ConfigFileReader.getDateFormat());
        Date startTime = null;
        Date endTime = null;
        try {
            endTime = format.parse(end);
            startTime = format.parse(start);
        } catch (ParseException e) {
            Logger.error("Time has not been cast to format");
        }
        Date duration = new Date(endTime.getTime() - startTime.getTime());
        format = new SimpleDateFormat(ConfigFileReader.getDurationFormat());
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format.format(duration);
    }

    public static void sortByDescendingTime(List<TestQA> testQAS) {
        Collections.sort(testQAS);
    }
}
