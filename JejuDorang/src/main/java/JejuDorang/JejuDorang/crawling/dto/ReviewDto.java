package JejuDorang.JejuDorang.crawling.dto;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewDto {
	private String name;
	private String profileUrl;
	private String text;
	private String relativeTimeDescription;
	private double rating;
	private Instant time;
}
