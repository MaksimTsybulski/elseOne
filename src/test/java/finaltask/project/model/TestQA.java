package finaltask.project.model;

import finaltask.utils.ConfigFileReader;
import finaltask.utils.Logger;
import finaltask.utils.TestsUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class TestQA implements Comparable<TestQA> {
    private String name;
    private String method;
    private String status;
    private String startTime;
    private String endTime;
    private String duration;

    public TestQA(String name, String method, String status, String startTime, String endTime, String duration) {
        this.name = name;
        this.method = method;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getMethod() {
        return method;
    }

    public String getName() {
        return name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDuration() {
        return duration;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
        setDuration(TestsUtils.getDuration(startTime, endTime));
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int compareTo(TestQA o) {
        SimpleDateFormat formatter = new SimpleDateFormat(ConfigFileReader.getDateFormat());
        Date startTime1 = null;
        Date startTime2 = null;
        try {
            startTime1 = formatter.parse(this.startTime);
            startTime2 = formatter.parse(o.startTime);
        } catch (ParseException e) {
            Logger.error("Time has not been cast to format");
        }
        return startTime2.compareTo(startTime1);
    }

    public boolean equalsStatus(String status) {
        if (status.equals("Passed") && this.status.equals("1")) return true;
        return status.equals("Failed") && this.status.equals("3");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestQA testQA = (TestQA) o;
        return name.equals(testQA.name) && method.equals(testQA.method) && status.equalsIgnoreCase(testQA.status) && startTime.equals(testQA.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, method, status, startTime, endTime, duration);
    }
}
