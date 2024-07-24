package JejuDorang.JejuDorang.member.data;

import java.util.ArrayList;
import java.util.List;

import JejuDorang.JejuDorang.diary.data.Diary;
import JejuDorang.JejuDorang.streak.data.Streak;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {

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
}
