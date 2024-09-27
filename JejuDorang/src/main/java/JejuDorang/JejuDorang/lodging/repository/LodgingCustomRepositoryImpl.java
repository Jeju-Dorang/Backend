package JejuDorang.JejuDorang.lodging.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import JejuDorang.JejuDorang.lodging.data.Lodging;
import JejuDorang.JejuDorang.lodging.data.QLodging;
import JejuDorang.JejuDorang.lodging.dto.LodgingRecommendResponseDto;
import JejuDorang.JejuDorang.lodging.enums.LodgingCategory;
import JejuDorang.JejuDorang.lodging.enums.LodgingDirection;
import JejuDorang.JejuDorang.member.data.Member;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LodgingCustomRepositoryImpl implements LodgingCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<LodgingRecommendResponseDto> findByDirectionAndCategoryAndPrice(
		Member member, LodgingDirection direction, LodgingCategory category, long price) {

		QLodging lodging = QLodging.lodging;

		List<Lodging> lodgings = jpaQueryFactory
			.selectFrom(lodging)
			.where(
				lodging.direction.eq(direction),
				lodging.category.eq(category),
				lodging.price.loe(price)
			)
			.fetch();
		double memberLatitude;
		double memberLongitude;
		if (member.getHome() != null) {
			memberLatitude = member.getHome().getLatitude();
			memberLongitude = member.getHome().getLongitude();
		} else {
			memberLatitude = 0.0;
			memberLongitude = 0.0;
		}


		return lodgings.stream()
			.map(l -> new LodgingRecommendResponseDto(
				l.getId(),
				l.getName(),
				l.getImage(),
				l.getAddress(),
				String.valueOf((int) calculateDistance(memberLatitude, memberLongitude, l.getLatitude(), l.getLongitude())) + " m",
				l.getRating(),
				l.getLatitude(),
				l.getLongitude()
			))
			.collect(Collectors.toList());
	}

	private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
		final int EARTH_RADIUS = 6371;

		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);

		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
			+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
			* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		double distanceInKm = EARTH_RADIUS * c;
		return distanceInKm * 1000;
	}
}
