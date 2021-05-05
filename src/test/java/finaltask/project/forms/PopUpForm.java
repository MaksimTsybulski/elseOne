package finaltask.project.forms;

import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.RemoteWebElement;

public class PopUpForm extends Form {
    private String modalFormat = "//body[contains(@class,'modal-open')]//%s";
    private ITextBox typeProjectName = getElementFactory().getTextBox(By.xpath(String.format(modalFormat, "*[@id='projectName']")), "type project name");
    private ITextBox saveProject = getElementFactory().getTextBox(By.xpath("//button[contains(text(),'Save')][contains(text(),'Project')]"), "save project");
    private ITextBox successfullySaved = getElementFactory().getTextBox(By.xpath("//div[contains(@class,'alert-success')]"), "successfully Saved");
    private static final int millSecToSec = 1000;

    public PopUpForm() {
        super(By.xpath("//div[contains(@class,'modal-open')]"), "pop-up window");
    }

    public void typeProjectName(String name) {
        typeProjectName.clearAndType(name);
    }

    public void saveProject() {
        saveProject.click();
    }

    public boolean isSuccessfullySaved() {
        boolean result;
        try {
            result = successfullySaved.getElement().isDisplayed();
        } catch (NoSuchElementException e) {
            result = false;
        }
        return result;
    }

    public boolean modalWindowIsClosed(int sec) {
        RemoteWebElement element = saveProject.getElement();
        long currentTime = System.currentTimeMillis();
        while (element.isDisplayed()) {
            if (currentTime + sec * millSecToSec < System.currentTimeMillis()) break;
        }
        return !element.isDisplayed();
    }
}
