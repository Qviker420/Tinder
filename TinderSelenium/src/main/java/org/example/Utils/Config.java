package org.example.Utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.util.Arrays;

public class Config {
    public WebDriver SetUpDriver() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();


        //options.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");

        //options.addArguments("--enable-automation");
        //options.addArguments(Arrays.asList("--no-sandbox","--enable-automation"));

//        options.addArguments("--user-data-dir=D:\\.ADSPOWER_GLOBAL\\cache\\jcb2sn0_ho0tud");
//        options.addArguments("--profile-directory=Default");


        DesiredCapabilities capabilities = new DesiredCapabilities();


        WebDriver driver = new ChromeDriver();
        RemoteWebDriver remoteDriver = (RemoteWebDriver) driver;
        HttpCommandExecutor executor = (HttpCommandExecutor) remoteDriver.getCommandExecutor();

        String url = executor.getAddressOfRemoteServer().toString();
        String sessionId = remoteDriver.getSessionId().toString();
        System.out.println(url + sessionId);
        capabilities.setCapability("webdriver.remote.sessionid", sessionId);


        WebDriver newDriver = new RemoteWebDriver(new java.net.URL(url), capabilities);



        return newDriver;
    }

}
