package finaltask.project.forms;

import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import finaltask.project.model.TestQA;
import org.openqa.selenium.By;
import java.util.ArrayList;
import java.util.List;

public class NexagePage extends Form {
    private String tableFormat = "//table[contains(@class,'table')]//%s";
    private List<ITextBox> tests = getElementFactory().findElements(By.xpath(String.format(tableFormat,"tr")), ITextBox.class);
    private By linesLoc = By.xpath("//td");
    private int testName = 0;
    private int testMethod = 1;
    private int status = 2;
    private int startTime = 3;
    private int endTime = 4;
    private int duration = 5;

    public NexagePage() {
        super(By.xpath("//ol[contains(@class,'breadcrumb')]//li[contains(text(),'Nexage')]"), "Nexage page");
    }

    public boolean isOpened() {
        return getElementFactory().getTextBox(getLocator(), getName()).getElement().isDisplayed();
    }

    public List<TestQA> getListTestOnPage() {
        List<TestQA> testsQA = new ArrayList<>();
        for (int i = 1; i < tests.size(); i++) {
            List<ITextBox> lines = getElementFactory().findChildElements(tests.get(i), linesLoc, ITextBox.class);
            TestQA testQA = new TestQA(
                    lines.get(testName).getText(),
                    lines.get(testMethod).getText(),
                    lines.get(status).getText(),
                    lines.get(startTime).getText(),
                    lines.get(endTime).getText(),
                    lines.get(duration).getText());
            testsQA.add(testQA);
        }
        return testsQA;
    }
}
