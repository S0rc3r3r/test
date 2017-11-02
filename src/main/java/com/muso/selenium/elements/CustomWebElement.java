package com.muso.selenium.elements;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.selenium.base.waits.WebDriverWaitManager;
import com.muso.selenium.base.waits.conditions.ExtExpectedConditions;
import com.muso.utils.thread.ThreadHandler;

public class CustomWebElement implements WebElement, Locatable {

    private final WebDriver driver;
    private WebElement underlyingElement;
    private final Locator locator;
    private final WebDriverWaitManager waitManager = WebDriverWaitManager.getInstance();
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomWebElement.class);

    // Fields dedicated to retry functionality when a WebDriverException occurs
    private final static int WD_EXCEPTION_RETRY_COUNT = 10;
    // We use a thread local variable because when a method (click) is called
    // recursively a new instance of CustomWebElement is created and an instance
    // variable won't help
    private static final ThreadLocal<Integer> retryCount = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    protected CustomWebElement(WebDriver driver, By byLoc) {
        this.driver = driver;
        underlyingElement = this.waitManager.explicitLongWaitUntil(driver,
                ExtExpectedConditions.presenceOfElementLocated(byLoc));
        locator = new FindElementLocator(driver, byLoc);
    }

    protected CustomWebElement(WebDriver driver, WebElement element, Locator locator) {
        this.driver = driver;
        underlyingElement = element;
        this.locator = locator;
    }

    public static WebElement wrap(WebDriver driver, By byLoc) {
        LOGGER.debug("Wrapping webElement {}", byLoc.toString());
        return new CustomWebElement(driver, byLoc);
    }

    @Override
    public WebElement findElement(By byLoc) {
        try {
            return new CustomWebElement(driver, underlyingElement.findElement(byLoc),
                    new FindElementLocator(this, byLoc));
        } catch (StaleElementReferenceException sere) {
            againLocate();
            return findElement(byLoc);
        }
    }

    @Override
    public void click() {
        try {
            underlyingElement.click();
        } catch (StaleElementReferenceException sere) {
            againLocate();
            click();
        }
        // If WebDriverException occurs while trying to click we retry only 10
        // times
        catch (WebDriverException wde) {
            if (retryCount.get() == WD_EXCEPTION_RETRY_COUNT) {
                LOGGER.error(
                        "Maximum number of click retries ({}) is reached. Returning from click method ",
                        WD_EXCEPTION_RETRY_COUNT);
                // Resetting counter for next retries
                retryCount.set(0);
            } else {
                retryCount.set(retryCount.get() + 1);
                ThreadHandler.sleep(500);
                againLocate();
                LOGGER.debug("WebDriver Exception occured while trying to click. Retry {}",
                        retryCount.get());
                click();
            }
        }
    }

    @Override
    public void clear() {
        try {
            underlyingElement.clear();
        } catch (StaleElementReferenceException sere) {
            againLocate();
            clear();
        }

    }

    @Override
    public List<WebElement> findElements(By byLoc) {
        try {
            return wrapElementsList(driver, underlyingElement.findElements(byLoc), this, byLoc);
        } catch (StaleElementReferenceException sere) {
            againLocate();
            return findElements(byLoc);
        }
    }

    @Override
    public String getAttribute(String arg0) {
        try {
            return underlyingElement.getAttribute(arg0);
        } catch (StaleElementReferenceException sere) {
            againLocate();
            return getAttribute(arg0);
        }
    }

    @Override
    public String getCssValue(String arg0) {
        try {
            return underlyingElement.getCssValue(arg0);
        } catch (StaleElementReferenceException sere) {
            againLocate();
            return getCssValue(arg0);
        }
    }

    @Override
    public Point getLocation() {
        try {
            return underlyingElement.getLocation();
        } catch (StaleElementReferenceException sere) {
            againLocate();
            return getLocation();
        }
    }

    @Override
    public Dimension getSize() {
        try {
            return underlyingElement.getSize();
        } catch (StaleElementReferenceException sere) {
            againLocate();
            return getSize();
        }
    }

    @Override
    public String getTagName() {
        try {
            return underlyingElement.getTagName();
        } catch (StaleElementReferenceException sere) {
            againLocate();
            return getTagName();
        }
    }

    @Override
    public String getText() {
        try {
            return underlyingElement.getText();
        } catch (StaleElementReferenceException sere) {
            againLocate();
            return getText();
        }
    }

    @Override
    public boolean isDisplayed() {
        try {
            return underlyingElement.isDisplayed();
        } catch (StaleElementReferenceException sere) {
            againLocate();
            return isDisplayed();
        }
    }

    @Override
    public boolean isEnabled() {
        try {
            return underlyingElement.isEnabled();
        } catch (StaleElementReferenceException sere) {
            againLocate();
            return isEnabled();
        }
    }

    @Override
    public boolean isSelected() {
        try {
            return underlyingElement.isSelected();
        } catch (StaleElementReferenceException sere) {
            againLocate();
            return isSelected();
        }
    }

    @Override
    public void sendKeys(CharSequence... arg0) {
        try {
            underlyingElement.sendKeys(arg0);
        } catch (StaleElementReferenceException sere) {
            againLocate();
            sendKeys(arg0);
        }
    }

    @Override
    public void submit() {
        try {
            underlyingElement.submit();
        } catch (StaleElementReferenceException sere) {
            againLocate();
            submit();
        }
    }

    @Override
    public Coordinates getCoordinates() {
        return ((Locatable) underlyingElement).getCoordinates();
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> arg0) throws WebDriverException {
        if (driver instanceof TakesScreenshot) {
            return ((TakesScreenshot) driver).getScreenshotAs(arg0);
        }

        throw new UnsupportedOperationException(
                "Underlying driver instance does not support taking screenshots");
    }

    // Private and protected methods

    private static List<WebElement> wrapElementsList(WebDriver driver, List<WebElement> elements,
        SearchContext searchContext, By byLoc) {
        final List<WebElement> ret = new ArrayList<WebElement>(elements.size());
        for (int i = 0; i < elements.size(); i++) {
            final WebElement element = elements.get(i);
            ret.add(new CustomWebElement(driver, element, new FindElementsLocator(searchContext,
                    byLoc, i)));
        }
        return ret;
    }

    protected void againLocate() {
        underlyingElement = locator.locate();
    }

    @Override
    public Rectangle getRect() {
        Rectangle rectangle = new Rectangle(getLocation(), getSize());
        return rectangle;
    }
}