package finaltask.framework.jdbc;

public enum ContentType {
    IMAGE_PNG("image/png"),
    TEXT_HTML("text/html");

    private String type;

    ContentType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
