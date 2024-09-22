package JejuDorang.JejuDorang.lodging.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import JejuDorang.JejuDorang.lodging.dto.LodgingRecommendResponseDto;
import JejuDorang.JejuDorang.lodging.enums.LodgingCategory;
import JejuDorang.JejuDorang.lodging.enums.LodgingDirection;
import JejuDorang.JejuDorang.member.data.Member;

@Repository
public interface LodgingCustomRepository {
	List<LodgingRecommendResponseDto> findByDirectionAndCategoryAndPrice (
		Member member, LodgingDirection direction, LodgingCategory category, long price
	);
}
