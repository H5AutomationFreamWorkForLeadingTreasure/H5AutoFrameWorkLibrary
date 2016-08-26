package page.app.setup;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import basicTool.WaitTool;
import dataBean.app.login.UserBean;
import io.appium.java_client.AppiumDriver;
import page.app.AbstractAppPage;
import page.app.borrowAndLend.BorrowAndLendPage;

public class LoginPage extends AbstractAppPage {

    @FindBy(xpath = "//UIAApplication/UIAWindow/UIAButton[@name='登录']")
    private WebElement loginButton;

    @FindBy(xpath = "//UIAApplication/UIAWindow/UIAButton[@name='注册']")
    private WebElement registerButton;

    @FindBy(xpath = "//UIATextField[@value='请输入手机号']")
    private WebElement userNameTextField;

    @FindBy(xpath = "//UIASecureTextField[@value='请输入借贷宝登录密码']")
    private WebElement passWordTextField;

    public LoginPage(AppiumDriver driver) {
        super(driver);
        WaitTool.waitFor(driver, ExpectedConditions.visibilityOf(loginButton), DefaultWaitElementTime4Page);
    }

    public BorrowAndLendPage login(UserBean userBean) throws Exception {
        click(loginButton, userNameTextField);
        setInputText(userNameTextField, userBean.getUserName());
        setInputText(passWordTextField, userBean.getPassWord());
        return click(loginButton, BorrowAndLendPage.class);
    }

}
