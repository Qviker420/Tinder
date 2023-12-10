import org.example.Utils.Config;
import org.example.Utils.SMSPoolAPI;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public class MainRunner {
    Config config = new Config();
    SMSPoolAPI smsPoolAPI = new SMSPoolAPI();
    WebDriver driver;
    @BeforeTest
    public void setUp() throws MalformedURLException {
        driver = config.SetUpDriver();
    }
    @Test
    public void TestOne()
    {
        driver.get("https://lite.tinder.com");
    }


}
