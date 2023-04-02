package ManageDriver;

import Util.Configuration;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class Hook {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    Hook() {

    }

    public static WebDriver getInstance(String browser) {
        if (driver.get() == null) {
            switch (browser) {
                case "CHROME":
                    driver.set(new ChromeDriver());
                    break;
                case "FIREFOX":
                    driver.set(new FirefoxDriver());
                    break;
                case "EDGE":
                    driver.set(new EdgeDriver());
                    break;
                default:
                    System.out.println("Not support to run browser");
                    Assert.assertTrue(false);
                    break;
            }
        }

        if (Configuration.DEFAULT_MAXIMUM) {
            driver.get().manage().window().maximize();
        }
        System.out.println("name thread == "+ Thread.currentThread().getName());
        System.out.println("SessionID  == "+ Thread.currentThread().getId());
        getWebdriver().manage().timeouts().pageLoadTimeout(Duration.ofMillis(Configuration.PAGE_LOAD_TIME));
        return getWebdriver();
    }

    public static WebDriver getWebdriver() {
        return driver.get();

    }

    public static void quit() {
        if (driver.get() != null) {
            System.out.println("name thread close == "+ Thread.currentThread().getName());
            System.out.println("SessionID close  == "+ Thread.currentThread().getId());
            driver.get().quit();
            driver.remove();
        }
    }
}
