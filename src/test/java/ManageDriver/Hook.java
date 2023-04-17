package ManageDriver;

import Util.Configuration;
import com.epam.healenium.SelfHealingDriver;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

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
                    ChromeOptions ChromeOptions = new ChromeOptions();
                    if(Configuration.IS_HEADLESS){
                        ChromeOptions.addArguments("--headless", "window-size=1024,768", "--no-sandbox");
                    }
                    WebDriver delgate = new ChromeDriver(ChromeOptions);
                   SelfHealingDriver selfHealingDriver = SelfHealingDriver.create(delgate);
                    driver.set(selfHealingDriver);
                    break;
                case "FIREFOX":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if(Configuration.IS_HEADLESS){
                        firefoxOptions.addArguments("--headless", "window-size=1024,768", "--no-sandbox");
                    }
                    driver.set(new FirefoxDriver(firefoxOptions));
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
