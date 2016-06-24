package Page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import BasicTool.WaitTool;

public class NavigationPage extends AbstractPage {

    @FindBy(css = "#common-header li a[href='/pcIndex.html']")
    private WebElement HomePageLink;

    @FindBy(css = "#common-header li a[href='https://chongzhi.jiedaibao.com']")
    private WebElement PayPageLink;

    @FindBy(css = "#common-header li a[href='/functionDetail.html']")
    private WebElement FunctionDetaillLink;

    public NavigationPage(WebDriver driver) {
        super(driver);
        switchFrame();
        WaitTool.waitFor(driver, ExpectedConditions.visibilityOf(HomePageLink), DefaultWaitElementTime4Page);
    }

    public PaymentPage clickPayPageLink() throws Exception {
        return click(PayPageLink, PaymentPage.class);
    }

    public FunctionDetailPage clickFunctionDetaillLink() throws Exception {
        return click(FunctionDetaillLink, FunctionDetailPage.class);
    }

}
