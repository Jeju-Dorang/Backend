package JejuDorang.JejuDorang.member.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import JejuDorang.JejuDorang.diary.data.Diary;
import JejuDorang.JejuDorang.streak.data.Streak;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member implements UserDetails {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	private String email;

	private String content;

	private String homeAddress;

	private String image;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
	private List<Streak> streaks = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
	private List<Diary> diaryList = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
	private List<MemberAchievement> memberAchievementList = new ArrayList<>();

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
}
