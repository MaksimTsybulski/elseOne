package finaltask.project.forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class MyProjectPage extends Form {
    private String testXpath = "//table[contains(@class,'table')]//a[@href='testInfo?testId=%s']";

    protected MyProjectPage() {
        super(By.xpath("//ol[contains(@class,'breadcrumb')]//a[contains(text(),Home)]"), "my project page");
    }

    public boolean isOpened() {
        return getElementFactory().getTextBox(getLocator(), getName()).getElement().isDisplayed();
    }

    public void openTestById(String id) {
        IButton test = getElementFactory().getButton(By.xpath(String.format(testXpath, id)), "test");
        test.click();
    }

    public TestPage getTestPage() {
        return new TestPage();
    }

    public boolean testAdded(String testId) {
        return getElementFactory().getButton(By.xpath(String.format(testXpath, testId)), "test").getElement().isDisplayed();
    }
}
