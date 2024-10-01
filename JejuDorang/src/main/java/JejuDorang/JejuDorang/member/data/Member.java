package JejuDorang.JejuDorang.member.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import JejuDorang.JejuDorang.question.data.Question;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import JejuDorang.JejuDorang.character.data.Character;
import JejuDorang.JejuDorang.diary.data.Diary;
import JejuDorang.JejuDorang.lodging.data.Lodging;
import JejuDorang.JejuDorang.streak.data.Streak;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member implements UserDetails {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	private String email;

	private String content;

	private String image = "https://jejudorangs3.s3.ap-northeast-2.amazonaws.com/icon/%ED%94%84%EB%A1%9C%ED%95%84_%EA%B8%B0%EB%B3%B8_%EC%9D%B4%EB%AF%B8%EC%A7%80.png";

	private int firstQuestion = 0;   // 첫 질문글 작성

	private int questionCommentCnt = 0;   // 질문글 답장

	private int diaryContinueCnt = 0;    // 연속 일기

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lodging_id")
	private Lodging home;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.REMOVE)
	private List<Streak> streaks = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.REMOVE)
	private List<Diary> diaryList = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.REMOVE)
	private List<MemberAchievement> memberAchievementList = new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.REMOVE)
	private Character character;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Question> questions = new ArrayList<>();

	@Builder
	public Member(String keyCode, String name) {
		this.keyCode = keyCode;
		this.name = name;
	}

	// ====== UserDetails ======

	@Column(length = 100, nullable = false, unique = true)
	private String keyCode;

	@ElementCollection(fetch = FetchType.EAGER) //roles 컬렉션
	private List<String> roles = new ArrayList<>();

	@Override   //사용자의 권한 목록 리턴
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles.stream()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}

	@Override
	public String getUsername() {
		return keyCode;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return "Member{" +
			"id=" + id +
			", name='" + name + '\'' +
			", email='" + email + '\'' +
			", content='" + content + '\'' +
			'}';
	}

	public void updateName(String name) {
		this.name = name;
	}

	public void updateEmail(String memberEmail) {
		this.email = memberEmail;
	}

	public void selectLodging(Lodging lodging) {
		this.home = lodging;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public void updateImage(String image) {
		this.image = image;
	}

	public void updateContent(String content) {
		this.content = content;
	}

	public void updateLodging(Lodging newLodging) {
		this.home = newLodging;
	}

	public void increateFirstQuestion() {
		this.firstQuestion ++;
	}

	public void increaseQuestionCommentCnt() {
		this.questionCommentCnt ++;
	}

	public void increaseDiaryContinueCnt() {
		this.diaryContinueCnt ++;
	}

	public void initDiaryContinueCnt() {
		this.diaryContinueCnt = 0;
	}
}
