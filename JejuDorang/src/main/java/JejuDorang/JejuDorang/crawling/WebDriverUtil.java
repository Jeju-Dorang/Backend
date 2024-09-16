package JejuDorang.JejuDorang.crawling;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class WebDriverUtil {
	private static String WEB_DRIVER_PATH; // WebDriver 경로

	public static WebDriver getChromeDriver() {
		if (ObjectUtils.isEmpty(System.getProperty("webdriver.chrome.driver"))) {
			// 상대 경로로 chromedriver 설정
			File chromeDriverFile = new File("/Users/sgo/Desktop/workspace/test/JejuDorang/src/main/resources/chromeDriver/chromedriver");
			System.setProperty("webdriver.chrome.driver", chromeDriverFile.getAbsolutePath());
		}

		// WebDriver 옵션 설정
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--lang=ko");
		chromeOptions.addArguments("--no-sandbox");
		chromeOptions.addArguments("--disable-dev-shm-usage");
		chromeOptions.addArguments("--disable-gpu");
		// chromeOptions.setCapability("ignoreProtectedModeSettings", true);

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
