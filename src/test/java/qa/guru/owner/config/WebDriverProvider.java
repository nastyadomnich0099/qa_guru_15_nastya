package qa.guru.owner.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Objects;
import java.util.function.Supplier;

public class WebDriverProvider implements Supplier<WebDriver> {

    private final WebDriverConfig config;

    public WebDriverProvider() {
        this.config = ConfigFactory.create(WebDriverConfig.class, System.getProperties());
    }

    @Override
    public WebDriver get() {
        WebDriver driver = createDriver();
        driver.get(config.getBaseUrl());
        return driver;
    }



    private WebDriver createDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (Objects.isNull(config.getRemoteURL())) {
            switch (config.getBrowser()) {
                case CHROME: {
                    WebDriverManager.chromedriver().setup();
                    capabilities.setVersion(config.getBrowserVersion());
                    return new ChromeDriver();
                }
                case FIREFOX: {
                    WebDriverManager.firefoxdriver().setup();
                    capabilities.setVersion(config.getBrowserVersion());
                    return new FirefoxDriver();
                }
                default: {
                    throw new RuntimeException("No such driver");
                }
            }
        }
        else {
            switch (config.getBrowser()) {
                case CHROME: {
                    RemoteWebDriver remoteChromeWebDriver = new RemoteWebDriver(config.getRemoteURL(), new ChromeOptions());
                    capabilities.setVersion(config.getBrowserVersion());
                    return remoteChromeWebDriver;
                }
                case FIREFOX: {
                    RemoteWebDriver remoteFirefoxWebDriver = new RemoteWebDriver(config.getRemoteURL(), new FirefoxOptions());
                    capabilities.setVersion(config.getBrowserVersion());
                    return remoteFirefoxWebDriver;
                }
                default: {
                    throw new RuntimeException("No such driver");
                }
            }
        }
    }
}