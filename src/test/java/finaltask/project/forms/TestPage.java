package finaltask.project.forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.By;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestPage extends Form {
    private List<ITextBox> listInfoTest = getElementFactory().findElements(By.xpath("//p[contains(@class,'list-group-item-text')]"), ITextBox.class);
    private ITextBox log = getElementFactory().getTextBox(By.xpath("//table[contains(@class,'table')]//td"), "log");
    private IButton attachment = getElementFactory().getButton(By.xpath("//div[contains(@class,'col-md-8')]//img"), "attachment");
    private int projectName = 0;
    private int testName = 1;
    private int methodName = 2;
    private int status = 3;
    private int startTime = 4;
    private int endTime = 5;
    private int duration = 6;
    private int environment = 7;
    private int browser = 8;

    public String getLogText() {
        return log.getText();
    }

    public TestPage() {
        super(By.xpath("//div[contains(@class,'panel panel-default')]"), "test page");
    }

    public String getProjectName() {
        return listInfoTest.get(projectName).getText();
    }

    public String getTestName() {
        return listInfoTest.get(testName).getText();
    }

    public String getMethodName() {
        return listInfoTest.get(methodName).getText();
    }

    public String getStatus() {
        return listInfoTest.get(status).getText();
    }

    public String getStarTime() {
        return getTime(listInfoTest.get(startTime).getText());
    }

    public String getEndTime() {
        return getTime(listInfoTest.get(endTime).getText());
    }

    public String getDuration() {
        return getTime(listInfoTest.get(duration).getText());
    }

    public String getEnvironment() {
        return listInfoTest.get(environment).getText();
    }

    public String getBrowser() {
        return listInfoTest.get(browser).getText();
    }

    private String getTime(String time) {
        Matcher matcher = Pattern.compile("\\d.+").matcher(time);
        String result = null;
        if (matcher.find()) {
            result = matcher.group();
        }
        return result;
    }

    public boolean isOpened() {
        return getElementFactory().getTextBox(getLocator(), getName()).getElement().isDisplayed();
    }

    public String getDecodeAttachment() {
        String src = attachment.getAttribute("src");
        return new String(Base64.decodeBase64(getEncodeSrc(src)));
    }

    private String getEncodeSrc(String src) {
        String startString = "base64,";
        Matcher matcher = Pattern.compile(startString + ".+").matcher(src);
        String result = null;
        if (matcher.find())
            result = matcher.group().replaceAll(startString, "");
        return result;
    }
}
