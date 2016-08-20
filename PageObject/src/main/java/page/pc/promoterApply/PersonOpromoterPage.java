package page.pc.promoterApply;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import basicTool.WaitTool;
import page.pc.AbstractPCPage;

public class PersonOpromoterPage extends AbstractPCPage {
    @FindBy(id = "typeText")
    private WebElement titleLabel;

    public PersonOpromoterPage(WebDriver driver) {
        super(driver);
        WaitTool.waitFor(driver, ExpectedConditions.visibilityOf(titleLabel), 60);
    }

}
