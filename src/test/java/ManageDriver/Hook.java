package ManageDriver;

import Util.Configuration;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class Hook {
    public  final ThreadLocal<WebDriver> driver = ThreadLocal.withInitial(() -> {
        return null;
    });

    public Hook() {
    }

    public  WebDriver getInstance(String browser) {
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
        driver.get().manage().timeouts().pageLoadTimeout(Duration.ofMillis(Configuration.PAGE_LOAD_TIME));
        return driver.get();
    }

    public WebDriver getWebdriver() {
        return driver.get();

    }

    public void quit() {
            System.out.println("name thread close == "+ Thread.currentThread().getName());
            System.out.println("SessionID close  == "+ Thread.currentThread().getId());
        driver.get().quit();
            driver.get().quit();
            driver.remove();
    }
}
