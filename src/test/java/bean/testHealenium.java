package bean;

import com.epam.healenium.SelfHealingDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static java.lang.Thread.*;

public class testHealenium {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("===================Starting======================");
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        WebDriver delgate = new ChromeDriver();
//        SelfHealingDriver driver = SelfHealingDriver.create(delgate);
        delgate.manage().window().maximize();
//        driver.get("http://google.com/");
//        sleep(5000);
//        driver.findElement(By.xpath("//a[text()='Gmail']")).click();
//
//        sleep(2000);



        delgate.get("http://live.techpanda.org/index.php/customer/account/login/");
        sleep(5000);
        delgate.findElement(By.name("login[username]")).sendKeys("testsss@gmail.com");
        delgate.findElement(By.name("login[password]")).sendKeys("123456");
        sleep(1000);
        delgate.findElement(By.id("send2")).click();

        sleep(2000);

    }
}
