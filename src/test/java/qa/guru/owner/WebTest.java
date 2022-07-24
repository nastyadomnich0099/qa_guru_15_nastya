package qa.guru.owner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import qa.guru.owner.config.WebDriverProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebTest {

    private WebDriver driver;

    @BeforeEach
    public void startDriver() {
        driver = new WebDriverProvider().get();
    }

    @Test
    public void testRabota() {
        String title = driver.getTitle();
        WebElement searchbar = driver.findElement(By.id("a11y-search-input"));
        searchbar.sendKeys("dev");
        searchbar.submit();
        Assertions.assertEquals("Работа в Минске, свежие вакансии - rabota.by", title);
    }

    @AfterEach
    public void stopDriver() {
        driver.quit();
    }

}
