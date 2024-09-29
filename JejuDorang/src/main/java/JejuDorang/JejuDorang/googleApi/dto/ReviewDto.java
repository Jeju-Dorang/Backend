package JejuDorang.JejuDorang.googleApi.dto;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewDto {
	private Long lodgingId;
	private String name;
	private String profileUrl;
	private String content;
	private String relativeTimeDescription;
	private double rating;
	private Instant time;
}
