package JejuDorang.JejuDorang.member.service;

import JejuDorang.JejuDorang.achievement.dto.AchievementDto;
import JejuDorang.JejuDorang.achievement.enums.AchievementStatus;
import JejuDorang.JejuDorang.achievement.repository.AchievementRepository;
import JejuDorang.JejuDorang.diary.dto.DiaryIdDto;
import JejuDorang.JejuDorang.diary.repository.DiaryRepository;
import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.member.dto.MemberDetailResponseDto;
import JejuDorang.JejuDorang.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

	@Mock
	MemberRepository memberRepository;

	@Mock
	DiaryRepository diaryRepository;

	@Mock
	AchievementRepository achievementRepository;

	@InjectMocks
	MemberService memberService;

	@Nested
	@DisplayName("getMemberDetail")
	public class GetMemberDetailTest {

		@Test
		@DisplayName("회원 상세 정보 조회(마이 페이지)")
		public void getMemberDetail() {
			// given
			Member member = Member.builder()
				.keyCode("testKeyCode")
				.name("testName")
				.build();

			List<DiaryIdDto> mockDiaries = Arrays.asList(
				new DiaryIdDto(1L),
				new DiaryIdDto(2L),
				new DiaryIdDto(3L)
			);

			List<AchievementDto> mockAchievements = Arrays.asList(
				new AchievementDto("icon1", "Achievement 1", "Comment 1", 100L, 50L, AchievementStatus.YET),
				new AchievementDto("icon2", "Achievement 2", "Comment 2", 200L, 150L, AchievementStatus.DONE)
			);

			when(diaryRepository.findTop3ByMemberId(member.getId())).thenReturn(mockDiaries);
			when(achievementRepository.findAllByMemberId(member.getId())).thenReturn(mockAchievements);

			// when
			MemberDetailResponseDto result = memberService.getMemberDetail(member);

			// then
			assertEquals(member.getId(), result.getId());
			assertEquals(member.getName(), result.getMemberName());
			assertEquals(mockDiaries.size(), result.getDiaries().size());
			assertEquals(mockAchievements.size(), result.getAchievements().size());

			// 추가적으로 반환된 Dto의 필드 값도 확인 가능
			assertEquals(mockAchievements.get(0).getAchievementName(), result.getAchievements().get(0).getAchievementName());
			assertEquals(mockAchievements.get(1).getAchievementStatus(), result.getAchievements().get(1).getAchievementStatus());
		}
	}
}
