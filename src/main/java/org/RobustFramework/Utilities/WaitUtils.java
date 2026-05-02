package org.RobustFramework.Utilities;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class WaitUtils {

    private WebDriver driver;
    private WebDriverWait wait;

    public WaitUtils(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public WebElement waitForElementToBeVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForElementToBeClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public boolean waitForElementToDisappear(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public String waitForTitleContains(String title) {
        wait.until(ExpectedConditions.titleContains(title));
        return driver.getTitle();
    }

    public void smartClick(By locator) {

        int attempts = 0;

        while (attempts < 3) {
            try {
                WebElement element = wait.until(
                        ExpectedConditions.elementToBeClickable(locator)
                );
                assert element != null;
                element.click();
                return;

            } catch (StaleElementReferenceException |
                     ElementNotInteractableException e) {

                attempts++;

                // small wait before retry
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {}
            }
        }

        // fallback (very important for modals)
        WebElement element = wait.until(
                ExpectedConditions.presenceOfElementLocated(locator)
        );

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
    }
}
