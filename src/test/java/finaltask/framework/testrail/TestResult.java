package finaltask.framework.testrail;

public enum TestResult {
    PASSED("1"),
    FAILED("5");

    public final String STATUS;

    TestResult(String status){
        STATUS = status;
    }
}
