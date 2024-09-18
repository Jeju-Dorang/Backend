package JejuDorang.JejuDorang.crawling.service;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import JejuDorang.JejuDorang.crawling.WebDriverUtil;
import JejuDorang.JejuDorang.lodging.dto.KaKaoCrawlingDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CrawlingService {

	private WebDriver driver;
	private WebDriverWait wait;

	@PostConstruct
	public void initialize() {
		driver = WebDriverUtil.getChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public KaKaoCrawlingDto searchKaKaoMap(String searchTerm) {
		double rating = 0.0;
		String category = "";
		try {
			driver.get("https://map.kakao.com/");

			// 검색창 찾기 및 검색어 입력
			WebElement searchArea = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"search.keyword.query\"]")));
			searchArea.sendKeys(searchTerm);

			// 검색 버튼 클릭
			WebElement searchButton = driver.findElement(By.xpath("//*[@id=\"search.keyword.submit\"]"));
			searchButton.click();

			// 검색 결과가 로드될 때까지 대기
			Thread.sleep(3000);

			// // 장소 탭 클릭
			// WebElement placeTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"info.main.options\"]/li[2]/a")));
			// placeTab.click();
			// Thread.sleep(2000);

			// 검색 결과 리스트에서 첫 번째 아이템의 별점 가져오기
			rating = getFirstPlaceRating();
			category = getFirstPlaceCategory();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			System.err.println("검색 중 인터럽트 발생: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("검색 중 오류 발생: " + e.getMessage());
		}
		return new KaKaoCrawlingDto(rating, category);
	}

	public void close() {
		WebDriverUtil.close(driver);
	}

	private double getFirstPlaceRating() {
		double rating = 0.0;
		try {
			if (isNoPlaceFound()) {
				System.err.println("검색 결과가 없습니다.");
				return 0;
			}

			WebElement firstItem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='info.search.place.list']/li[1]")));

			if (firstItem == null) {
				System.err.println("첫 번째 리스트 아이템을 찾지 못했습니다.");
				return 0;
			}

			WebElement ratingElement = firstItem.findElement(By.xpath(".//em[@data-id='scoreNum']"));
			String ratingStr = ratingElement.getText();
			rating = Double.parseDouble(ratingStr);

			System.out.println("첫 번째 장소의 별점: " + ratingStr);
		} catch (Exception e) {
			System.err.println("별점 정보를 가져오는 중 오류 발생: " + e.getMessage());
		}
		return rating;
	}

	private String getFirstPlaceCategory() {
		String category = "";
		try {
			if (isNoPlaceFound()) {
				System.err.println("검색 결과가 없습니다.");
				return "";
			}

			WebElement firstItem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='info.search.place.list']/li[1]")));

			if (firstItem == null) {
				System.err.println("첫 번째 리스트 아이템을 찾지 못했습니다.");
				return "";
			}

			WebElement categoryElement = firstItem.findElement(By.xpath(".//span[@data-id='subcategory']"));
			category = categoryElement.getText();

			System.out.println("첫 번째 장소의 카테고리: " + category);
		} catch (Exception e) {
			System.err.println("카테고리 정보를 가져오는 중 오류 발생: " + e.getMessage());
		}
		return category;
	}


	private boolean isNoPlaceFound() {
		try {
			WebElement noPlaceElement = driver.findElement(By.id("info.noPlace"));
			String classAttribute = noPlaceElement.getAttribute("class");

			if (classAttribute != null && !classAttribute.contains("HIDDEN")) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

}
