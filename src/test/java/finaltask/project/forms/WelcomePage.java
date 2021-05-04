package finaltask.project.forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WelcomePage extends Form {
    private ITextBox footer = getElementFactory().getTextBox(By.xpath("//p[contains(@class,'text-center')][contains(@class,'footer-text')]//span"), "version");
    private IButton nexagePage = getElementFactory().getButton(By.xpath("//div[contains(@class,'list-group')]//a[contains(text(),'Nexage')]"), "nexage button");
    private IButton addProject = getElementFactory().getButton(By.xpath("//div[contains(@class,'panel-heading')]//button[contains(@class,'btn-primary')]"), "add project");
    private String projectXpath = "//a[contains(@class,'list-group-item')][contains(text(),'%s')]";

    public WelcomePage() {
        super(By.xpath("//div[contains(@class,'container')][contains(@class,'main-container')]"), "welcome page");
    }

    public boolean isOpened() {
        return getElementFactory().getTextBox(getLocator(), getName()).getElement().isDisplayed();
    }

    public NexagePage getNexagePage() {
        return new NexagePage();
    }

    public String getFooterVariant() {
        String text = footer.getText();
        return text.substring(text.length() - 1);
    }

    public void openNexagePage() {
        nexagePage.click();
    }

    public MyProjectPage getMyProjectPage() {
        return new MyProjectPage();
    }

    public PopUpForm getPopUpForm() {
        return new PopUpForm();
    }

    public String getNexageProjectId() {
        String href = nexagePage.getAttribute("href");
        return href.substring(href.length() - 1);
    }

    public void addProjectAndOpenPopUp() {
        addProject.click();
    }

    public void openProjectByName(String projectName) {
        IButton myProject = getElementFactory().getButton(By.xpath(String.format(projectXpath, projectName)), projectName);
        myProject.click();
    }

    public String getProjectId(String projectName) {
        String projectId = null;
        String href = getElementFactory().getButton(By.xpath(String.format(projectXpath, projectName)), projectName).getAttribute("href");
        Matcher matcher = Pattern.compile("=.+").matcher(href);
        if (matcher.find()) {
            projectId = matcher.group().substring(1);
        }
        return projectId;
    }

    public boolean myProjectAdded(String projectName) {
        return getElementFactory().getButton(By.xpath(String.format(projectXpath, projectName)), projectName).getElement().isDisplayed();
    }
}
