package JejuDorang.JejuDorang.member.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import JejuDorang.JejuDorang.BaseIntegrationTest;
import JejuDorang.JejuDorang.common.util.TestDataUtils;
import JejuDorang.JejuDorang.component.JwtTokenProvider;
import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.member.dto.MemberDetailResponseDto;
import JejuDorang.JejuDorang.member.service.MemberService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

public class MemberControllerTest extends BaseIntegrationTest {

	@MockBean
	private MemberService memberService;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private TestDataUtils testDataUtils;

	@Nested
	@DisplayName("Member api 테스트")
	public class MemberDetailTest {

		Member mockMember;
		String jwtToken;
		@BeforeEach
		public void setUp() {
			String keyCode = "mockKeyCode";
			// given
			mockMember = testDataUtils.saveKeyCodeMember(keyCode);
			jwtToken = jwtTokenProvider.createAccessToken(keyCode);
		}

		@Test
		@DisplayName("회원 상세 정보 조회(마이 페이지)")
		public void getMemberDetail() throws Exception {
			// given

			MemberDetailResponseDto mockResponseDto = new MemberDetailResponseDto(mockMember);
			mockResponseDto.setAchievementsAndDiaries(List.of(), List.of());  // 테스트 데이터 추가

			when(memberService.getMemberDetail(any(Member.class)))
				.thenReturn(mockResponseDto);

			// when
			ResultActions resultActions = mvc.perform(
				get("/information")
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
					.contentType(MediaType.APPLICATION_JSON)
					.requestAttr("member", mockMember)
			);

			// then
			resultActions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(mockMember.getId()))
				.andExpect(jsonPath("$.memberName").value(mockMember.getName()))
				.andExpect(jsonPath("$.diaries").isArray())
				.andExpect(jsonPath("$.achievements").isArray());
		}
	}
}
