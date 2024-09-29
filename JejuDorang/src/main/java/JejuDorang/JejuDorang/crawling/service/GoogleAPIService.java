package JejuDorang.JejuDorang.crawling.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.maps.GeoApiContext;
import com.google.maps.PlaceDetailsRequest;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.AddressType;
import com.google.maps.model.Photo;
import com.google.maps.model.PlaceDetails;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;

import JejuDorang.JejuDorang.crawling.dto.GoogleApiDto;
import JejuDorang.JejuDorang.crawling.dto.ReviewDto;
import JejuDorang.JejuDorang.lodging.enums.LodgingCategory;

@Service
public class GoogleAPIService {

	private GeoApiContext context;
	private String apiKey;

	public GoogleAPIService(@Value("${google.api.key}") String apiKey) {
		this.apiKey = apiKey;
		context = new GeoApiContext.Builder()
			.apiKey(apiKey)
			.build();
	}

	public GoogleApiDto getGoogleApiData(String searchTerm) {
		try {
			PlacesSearchResponse searchResponse = PlacesApi.textSearchQuery(context, searchTerm).await();
			PlacesSearchResult[] results = searchResponse.results;

			if (results.length == 0)
				return null;
			String placeId = results[0].placeId;

			return getPlaceDetails(placeId);
		} catch (Exception e) {
			return null;
		}
	}

	private String extractCategory(AddressType[] types) {
		if (types != null && types.length > 0) {
			for (AddressType type : types){
				System.out.println(type.toString());
			}
		}
		return "기타";
	}

	public GoogleApiDto getPlaceDetails(String placeId) {
		try {
			PlaceDetailsRequest request = PlacesApi.placeDetails(context, placeId).language("ko");

			request.fields(
				PlaceDetailsRequest.FieldMask.NAME,
				PlaceDetailsRequest.FieldMask.FORMATTED_ADDRESS,
				PlaceDetailsRequest.FieldMask.FORMATTED_PHONE_NUMBER,
				PlaceDetailsRequest.FieldMask.RATING,
				PlaceDetailsRequest.FieldMask.REVIEW,
				PlaceDetailsRequest.FieldMask.USER_RATINGS_TOTAL,
				PlaceDetailsRequest.FieldMask.PHOTOS,
				PlaceDetailsRequest.FieldMask.PRICE_LEVEL,
				PlaceDetailsRequest.FieldMask.TYPES,
				PlaceDetailsRequest.FieldMask.INTERNATIONAL_PHONE_NUMBER,
				PlaceDetailsRequest.FieldMask.FORMATTED_PHONE_NUMBER
			);

			// 요청 실행
			PlaceDetails placeDetails = request.await();

			String name = placeDetails.name;
			String address = placeDetails.formattedAddress;
			String phoneNumber = placeDetails.internationalPhoneNumber;
			int price = placeDetails.priceLevel != null ? placeDetails.priceLevel.ordinal() : 0;

			// 사진 정보
			Photo[] photos = placeDetails.photos;
			String image = "";
			if (photos != null) {
				Photo photo = photos[0];
				String photoReference = photo.photoReference;
				image = "https://maps.googleapis.com/maps/api/place/photo"
					+ "?maxwidth=400"
					+ "&photoreference=" + photoReference
					+ "&key=" + apiKey;
 			}

			LodgingCategory category = LodgingCategory.valueOfCategory(name);

			String description = "";

			// 리뷰 정보
			PlaceDetails.Review[] reviews = placeDetails.reviews;
			List<ReviewDto> reviewDtos = new ArrayList<>();
			float sum = 0;
			if (reviews != null) {
				for (PlaceDetails.Review review : reviews) {
					ReviewDto reviewDto = ReviewDto.builder()
						.name(review.authorName)
						.profileUrl(review.profilePhotoUrl)
						.content(review.text)
						.relativeTimeDescription(review.relativeTimeDescription)
						.rating(review.rating)
						.time(review.time)
						.build();
					reviewDtos.add(reviewDto);
					sum += review.rating;
				}
			}
			double rating = sum / reviewDtos.size();
			BigDecimal bd = new BigDecimal(Double.toString(rating));
			BigDecimal roundedRating = bd.setScale(1, RoundingMode.HALF_UP);
			GoogleApiDto googleApiDto = new GoogleApiDto(
				name,
				String.valueOf(price),
				image,
				phoneNumber,
				roundedRating.doubleValue(),
				address,
				category,
				description
			);

			googleApiDto.setReviews(reviewDtos);
			return googleApiDto;
		} catch (ApiException | InterruptedException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
