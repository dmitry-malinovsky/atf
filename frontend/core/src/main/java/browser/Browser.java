package browser;

import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class Browser {
    private WebDriver driver;
    private long timeout;
    private long step;

    public Browser(WebDriver driver, long timeout, long step) {
        this.driver = driver;
        this.timeout = timeout;
        this.step = step;
    }

    private FluentWait initFluentWait() {
        return new FluentWait(driver)
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(step))
                .ignoring(WebDriverException.class);
    }

    public void navigateTo(String url) {
        this.driver.navigate().to(url);
    }

    public void maximize() {
        this.driver.manage().window().maximize();
    }

    public void goBack() {
        this.driver.navigate().back();
    }

    public void goForward() {
        this.driver.navigate().forward();
    }

    public void refresh() {
        this.driver.navigate().refresh();
    }

    public void quit() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }

    public void close() {
        if (this.driver != null) {
            this.driver.close();
        }
    }

    public String getPageTitle() {
        return this.driver.getTitle();
    }

    public String getPageUrl() {
        return this.driver.getCurrentUrl();
    }

    public void applyJSScript(String script, WebElement... elements) {
        ((JavascriptExecutor) this.driver).executeScript(script, elements);
    }

    public void waitForPageToLoad() {
        ExpectedCondition expectedCondition = (driver1) ->
                ((JavascriptExecutor) driver1).executeScript("return document.readyState").equals("complete");

        FluentWait fluentWait = initFluentWait();
        try {
            fluentWait.until(expectedCondition);
        } catch (Throwable e) {
            Assert.fail("[Timeout]: Failed to load the page");
        }

    }
}