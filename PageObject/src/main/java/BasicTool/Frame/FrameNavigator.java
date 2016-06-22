package BasicTool.Frame;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.apache.log4j.Logger;

import BasicTool.ReflectionUtils;
import BasicTool.WaitTool;
import Factory.LogFactory;
import Page.AbstractPage;

public class FrameNavigator {

    public static final String NO_SUCH_FRAME = "no-such-frame";
    private final Logger logger = LogFactory.getLogger(FrameNavigator.class);
    private final WebDriver driver;
    private final AbstractPage page;
    private final String defaultFrameName;
    private String currentFrame;

    /**
     * Construct a new frame navigator helper object.
     * 
     * @param driver
     *            a web driver
     * @param page
     *            the page for whose frames are managed. Frame management extends to any sub-class of this object.
     */
    public FrameNavigator(WebDriver driver, AbstractPage page) {
        this.driver = driver;
        this.page = page;
        this.defaultFrameName = getFrameName(page.getClass());
        this.currentFrame = NO_SUCH_FRAME;
    }

    /**
     * The current frame as tracked internally by this frame navigator.
     * 
     * @return the current frame. If unknown the static class String <code>NO_SUCH_FRAME</code> is returned.
     */
    public String getCurrentFrame() {
        return currentFrame;
    }

    /**
     * Switch to the default frame.
     * <p>
     * The default frame is inferred by inspecting the managed page class for a WebPageFrame annotation. If not found,
     * each parent class is inspected in its turn until a WebPageFrame annotation is found. If still none is found, the
     * default is a blank frame name.
     */
    public void switchFrame() {
        switchFrame(defaultFrameName);
    }

    /**
     * Switch to a named frame
     * 
     * @param newFrame
     *            the named frame
     */
    public void switchFrame(String newFrame) {
        // FWK-680 in a component framing system. This fails to work correctly.
        // It also fails in a martini-perf-api environment as there the perf
        // might change frames.
        // if (!currentFrame.equals(newFrame)) {
        logger.info("Switching to frame: " + newFrame);
        /*
         * if (driver instanceof InternetExplorerDriver) { switchFrame(""); }
         */
        switchFrame(driver, newFrame);
        currentFrame = newFrame;
        // }
    }

    /**
     * Service method for direct switching of frames inside page object toolkit
     * 
     * @param driver
     *            the web driver to use
     * @param frameName
     *            the named frame
     */
    public static void switchFrame(WebDriver driver, String frameName) {
        if (frameName == null) {
            return;
        }
        driver.switchTo().defaultContent();
        if (frameName.equals(NO_SUCH_FRAME)) {
            return;
        }
        if (!"".equals(frameName)) {
            if (frameName.indexOf('|') != -1) {
                String[] subFrames = frameName.split("[|]");
                for (String subFrame : subFrames) {
                    WaitTool.waitFor(driver,
                            ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector(subFrame)),
                            WaitTool.getDefaultWait4Page());
                }
            } else {
                String[] subFrames = frameName.split("[.]");
                for (String subFrame : subFrames) {
                    WaitTool.waitFor(driver, ExpectedConditions.frameToBeAvailableAndSwitchToIt(subFrame),
                            WaitTool.getDefaultWait4Page());
                }
            }
        }
    }

    /**
     * Switch to a frame based on a Page Object's <code>Frame</code> annotation.
     * 
     * @param clazz
     *            the page object representing the frame. The page object must be annotated with the WebPageFrame
     *            annotation.
     */
    public void switchFrame(Class<? extends AbstractPage> clazz) {
        switchFrame(getFrameName(clazz));
    }

    /**
     * Switch to the frame containing an element.
     * <p>
     * The class explicitly declaring the element as a field is located. The target frame is inferred by inspecting the
     * class found for a WebPageFrame annotation. If not found, each parent class is inspected in its turn until a
     * WebPageFrame annotation is found. If still none is found, the default is a blank frame name, which is the same as
     * the root context.
     * 
     * @param element
     *            the contained element list.
     */
    public void switchFrame(List<WebElement> element) {
        switchFrame(ReflectionUtils.findFieldContainingClassByValue(page, element));
    }

    /**
     * Switch to the frame containing an element.
     * <p>
     * The class explicitly declaring the element as a field is located. The target frame is inferred by inspecting the
     * class found for a WebPageFrame annotation. If not found, each parent class is inspected in its turn until a
     * WebPageFrame annotation is found. If still none is found, the default is a blank frame name, which is the same as
     * the root context.
     * 
     * @param element
     *            the contained element
     */
    public void switchFrame(WebElement element) {
        switchFrame(ReflectionUtils.findFieldContainingClassByValue(page, element));
    }

    /**
     * Get the name attribute of the Frame annotation of a Page Object. The annotation may be on a parent object.
     * 
     * @param clazz
     *            the page object to analyze.
     * @return the name attribute of the <code>Frame</code> annotation of the page object or one of its ancestors, or
     *         <code>null</code>, if there is no <code>Frame</code> annotation.
     */
    public static String getFrameName(final Class<?> clazz) {
        IFrame frameAnnontation = ReflectionUtils.getAnnotation(clazz, IFrame.class);
        if (frameAnnontation == null) {
            return NO_SUCH_FRAME;
        }
        if (!frameAnnontation.name().equals(NO_SUCH_FRAME)) {
            return frameAnnontation.name();
        }
        if (!frameAnnontation.id().equals(NO_SUCH_FRAME)) {
            return frameAnnontation.id();
        }
        if (!frameAnnontation.cssLocator().equals(NO_SUCH_FRAME)) {
            return frameAnnontation.cssLocator();
        }
        return NO_SUCH_FRAME;
    }

}
