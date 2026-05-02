package org.RobustFramework.Utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;

public class ScreenshotUtils {

    public static String capture(WebDriver driver, String testName) throws IOException {

        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        String path = System.getProperty("user.dir")
                + File.separator + "reports"
                + File.separator + "screenshots"
                + File.separator + testName + ".png";

        File dest = new File(path);

        // 🔥 IMPORTANT: create directory if not exists
        dest.getParentFile().mkdirs();

        FileUtils.copyFile(source, dest);

        return path;
    }
}