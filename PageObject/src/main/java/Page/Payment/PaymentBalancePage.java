package Page.Payment;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import BasicTool.WaitTool;
import Page.AbstractPage;

public class PaymentBalancePage extends AbstractPage {
	
	@FindBy(css = ".countNum.ng-binding")
	private WebElement balanceNumber;
	
	public PaymentBalancePage(WebDriver driver) {
        super(driver);
        WaitTool.waitFor(driver, ExpectedConditions.visibilityOf(balanceNumber), 60);
    }
}
