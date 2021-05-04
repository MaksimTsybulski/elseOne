package finaltask.project.model;


public enum StatusTest {
    PASSED("1"),
    SKIPPED("3"),
    FAILED("5");

    private String STATUS;

    StatusTest(String status){
        STATUS = status;
    }
}
