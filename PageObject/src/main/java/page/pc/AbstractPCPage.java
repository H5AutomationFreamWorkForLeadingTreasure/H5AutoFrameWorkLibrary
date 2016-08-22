package page.pc;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.customize.reporter.WebReporter;

import basicTool.WaitTool;
import basicTool.frame.FrameNavigator;
import page.AbstractPage;

public class AbstractPCPage implements AbstractPage {

    protected WebDriver driver;
    public static final int DefaultWaitElementTime4Page = 60;
    protected final FrameNavigator frameNavigator;

    /**
     * Construct a Component Page Object until the page loaded.
     * 
     * @param driver
     *            the Selenium 2 WebDriver used by this class to manipulate the application
     * 
     * @throws TimeoutException
     *             if the timeout expires
     * 
     */

    protected AbstractPCPage(WebDriver driver) {
        this.driver = driver;
        this.frameNavigator = new FrameNavigator(driver, this);
        WaitTool.waitFor(driver, WaitTool.pageLoadDone());
        PageFactory.initElements(driver, this);
    }

    /**
     * Is this element currently displayed or not? This method avoids the problem of having to parse an element's
     * "style" attribute.
     * 
     * @param driver
     *            the instance of WebDriver manipulated
     * @return Whether or not the element is displayed
     */

    protected static boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
        }
        return false;
    }

    /**
     * Explicitly switch the current frame to the default frame for this page. This method is already invoked by all
     * methods in this class, where applicable.
     */
    protected void switchFrame() {
        frameNavigator.switchFrame();
    }

    /**
     * Explicitly switch the current frame to the frame of a WebElement. This method is already invoked by all methods
     * in this class, where applicable.
     * 
     * @param element
     *            the WebElement used to determine the target frame.
     */
    protected void switchFrame(WebElement element) {
        frameNavigator.switchFrame(element);
    }

    /**
     * A simple click mehtod
     * 
     * @param element
     *            You want to click element
     */
    protected void click(WebElement element) {
        switchFrame(element);
        isDisplayed(element);
        element.click();

    }

    /**
     * Click one webElement to wait next page
     * 
     * @param element
     *            click on element
     * @param nextPageClass
     *            expect pageobject's class
     * @return
     * @throws Exception
     */
    protected <W extends AbstractPCPage> W click(WebElement element, Class<W> nextPageClass) throws Exception {
        WebReporter.log(driver, driver.getTitle(), true, true);
        click(element);
        return nextPageClass.getDeclaredConstructor(WebDriver.class).newInstance(this.driver);
    }

    /**
     * Click one webElement to wait next webElement
     * 
     * @param element
     *            click on element
     * @param expectedElement
     *            except to get element
     * @return
     * @throws Exception
     */
    protected WebElement click(WebElement element, WebElement expectedElement) throws Exception {
        WebReporter.log(driver, driver.getTitle(), true, true);
        click(element);
        return ((WebElement) WaitTool.waitFor(this.driver, ExpectedConditions.visibilityOf(expectedElement),
                WaitTool.getDefaultWait4Page()));
    }

    /**
     * Set content into webElement
     * 
     * @param element
     *            need to fill content element
     * @param content
     *            want to fill content
     */
    protected void setInputText(WebElement element, String content) {
        switchFrame(element);
        element.clear();
        element.sendKeys(new CharSequence[] { content });
    }

    /**
     * 
     * @param element
     * 
     *            move to element
     * 
     * @return
     * 
     *         after page
     * 
     * @throws Exception
     */
    protected <W extends AbstractPCPage> W moveToElement(WebElement element) throws Exception {
        new Actions(driver).moveToElement(element).build().perform();
        return (W) this;
    }

    /**
     * 
     * @param element
     * 
     *            move to element and click
     * @return
     * 
     *         after page
     * @throws Exception
     */
    protected <W extends AbstractPCPage> W moveToAndClickElement(WebElement element) throws Exception {
        (new Actions(driver)).moveToElement(element).click().perform();
        return (W) this;
    }

    /**
     * 
     * @param element
     * 
     *            click element and hold on
     * @return
     * 
     *         after page
     * @throws Exception
     */
    protected <W extends AbstractPCPage> W clickAndHold(WebElement element) throws Exception {
        (new Actions(driver)).clickAndHold(element).build().perform();
        return (W) this;
    }

}
