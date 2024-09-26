package JejuDorang.JejuDorang.crawling;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class WebDriverUtil {
	private static String WEB_DRIVER_PATH = System.getenv("CHROMEDRIVER_PATH");

	public static WebDriver getChromeDriver() {
		if (ObjectUtils.isEmpty(System.getProperty("webdriver.chrome.driver"))) {
			System.setProperty("webdriver.chrome.driver", WEB_DRIVER_PATH);
		}

		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--lang=ko");
		chromeOptions.addArguments("--no-sandbox");
		chromeOptions.addArguments("--disable-dev-shm-usage");
		chromeOptions.addArguments("--disable-gpu");
		chromeOptions.addArguments("--headless"); // Headless 모드 추가

		WebDriver driver = new ChromeDriver(chromeOptions);
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		return driver;
	}

	public static void quit(WebDriver driver) {
		if (!ObjectUtils.isEmpty(driver)) {
			driver.quit();
		}
	}

	public static void close(WebDriver driver) {
		if (!ObjectUtils.isEmpty(driver)) {
			driver.close();
		}
	}
}
