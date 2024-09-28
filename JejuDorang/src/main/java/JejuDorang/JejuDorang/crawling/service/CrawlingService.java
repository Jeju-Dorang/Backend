package JejuDorang.JejuDorang.crawling.service;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import JejuDorang.JejuDorang.crawling.WebDriverUtil;
import JejuDorang.JejuDorang.crawling.dto.CrawlingDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CrawlingService {

	private WebDriver driver;
	private WebDriverWait wait;

	public void initialize() {
		driver = WebDriverUtil.getChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public CrawlingDto searchKaKaoMap(String searchTerm) {
		double rating = 0.0;
		String url = "https://www.yeogi.com/domestic-accommodations?keyword=" + searchTerm +
			"&checkIn=2024-10-23&checkOut=2024-10-24&personal=2&freeForm=false";;
		String name = "", price = "", category = "", description = "";

		try {
			System.out.println("여기어때 검색 시작");
			driver.get(url);

			// 결과 목록이 로드될 때까지 대기
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".gc-thumbnail-type-seller-card.css-wels0m")));

			Thread.sleep(3000);
			// 첫 번째 숙소 아이템 요소 가져오기
			WebElement firstAccommodationElement = driver.findElement(By.cssSelector(".gc-thumbnail-type-seller-card.css-wels0m"));

			// 숙소 이름 가져오기
			try {
				WebElement nameElement = firstAccommodationElement.findElement(By.cssSelector(".gc-thumbnail-type-seller-card-title.css-1asqkxl"));
				name = nameElement.getText();
			} catch (Exception e) {
				name = searchTerm;
			}

			// 숙소 가격 가져오기
			try {
				WebElement priceElement = firstAccommodationElement.findElement(By.className("css-5r5920"));
				price = priceElement.getText();
			} catch (Exception e) {
				System.err.println("가격 정보를 가져오는 중 오류 발생: " + e.getMessage());
				price = "가격 정보 없음";
			}
			System.out.println("숙소 이름: " + name + ", 가격: " + price);

			rating = getFirstPlaceRating();
			category = getFirstPlaceCategory();

			// 숙소 상세로 넘어가는 부분
			try {
				// 현재 창의 핸들을 저장합니다.
				String originalWindow = driver.getWindowHandle();

				// 상세 페이지로 이동하는 링크 요소를 클릭합니다.
				WebElement detailLink = firstAccommodationElement;
				detailLink.click();

				// 새로운 창이 열릴 때까지 대기합니다.
				wait.until(ExpectedConditions.numberOfWindowsToBe(2));

				// 모든 창의 핸들을 가져와서 새로운 창으로 전환합니다.
				for (String windowHandle : driver.getWindowHandles()) {
					if (!originalWindow.contentEquals(windowHandle)) {
						driver.switchTo().window(windowHandle);
						break;
					}
				}

				// 새로운 창에서 숙소 설명을 가져옵니다.
				WebElement descriptionElement = wait.until(
					ExpectedConditions.presenceOfElementLocated(By.cssSelector(".css-l22fxz"))
				);
				description = descriptionElement.getText();

				System.out.println("숙소 설명: " + description);

				// 새로운 창을 닫습니다.
				driver.close();

				// 원래 창으로 다시 전환합니다.
				driver.switchTo().window(originalWindow);

			} catch (Exception e) {
				System.err.println("숙소 상세 페이지에서 데이터 가져오는 중 오류 발생: " + e.getMessage());
			}
		} catch (Exception e) {
			System.err.println("검색 중 오류 발생: " + e.getMessage());
		}
		return new CrawlingDto(name, price, rating, category, description);
	}

	public void close() {
		WebDriverUtil.close(driver);
	}

	private double getFirstPlaceRating() {
		double rating = 0.0;
		try {
			WebElement firstItem = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".gc-thumbnail-type-seller-card.css-wels0m")));

			if (firstItem == null) {
				System.err.println("첫 번째 리스트 아이템을 찾지 못했습니다.");
				return 0;
			}

			WebElement ratingElement = firstItem.findElement(By.cssSelector(".css-9ml4lz"));
			String ratingStr = ratingElement.getText();

			// 별점 문자열을 숫자로 변환
			rating = Double.parseDouble(ratingStr);

			System.out.println("숙소의 별점: " + ratingStr);
		} catch (Exception e) {
			System.err.println("별점 정보를 가져오는 중 오류 발생: " + e.getMessage());
		}
		return rating;
	}

	private String getFirstPlaceCategory() {
		String category = "";
		try {
			// 첫 번째 숙소 아이템 요소 가져오기
			WebElement firstItem = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".gc-thumbnail-type-seller-card.css-wels0m")));

			if (firstItem == null) {
				System.err.println("첫 번째 리스트 아이템을 찾지 못했습니다.");
				return "";
			}

			// 카테고리 리스트 요소 가져오기
			WebElement categoryListElement = firstItem.findElement(By.cssSelector(".css-19akvy6"));

			// 카테고리 항목들(<li>) 가져오기
			List<WebElement> categoryItems = categoryListElement.findElements(By.tagName("li"));

			// 각 카테고리 항목의 텍스트를 이어붙이기
			StringBuilder categoryBuilder = new StringBuilder();
			for (WebElement item : categoryItems) {
				categoryBuilder.append(item.getText()).append(", ");
			}

			if (categoryBuilder.length() > 0) {
				category = categoryBuilder.substring(0, categoryBuilder.length() - 2);
			}

			System.out.println("숙소의 카테고리: " + category);
		} catch (Exception e) {
			System.err.println("카테고리 정보를 가져오는 중 오류 발생: " + e.getMessage());
		}
		return category;
	}


}
