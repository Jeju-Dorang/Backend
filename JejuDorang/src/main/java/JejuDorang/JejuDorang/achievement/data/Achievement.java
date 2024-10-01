package JejuDorang.JejuDorang.achievement.data;

import java.util.ArrayList;
import java.util.List;

import JejuDorang.JejuDorang.achievement.enums.AchievementType;
import JejuDorang.JejuDorang.item.data.Item;
import JejuDorang.JejuDorang.member.data.MemberAchievement;
import JejuDorang.JejuDorang.tag.data.AchievementTag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class Achievement {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "achievement_id")
    private Long id;

    @NotNull(message = "업적 이름을 입력해주세요.")
    private String name;

    private String content;

    @NotNull(message = "업적 달성 최대 횟수를 입력해주세요.")
    private Long maxAchieve;

    private String image;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "업적 카테고리를 입력해주세요.")
    private AchievementType category;

    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @OneToMany(mappedBy = "achievement", cascade = CascadeType.REMOVE)
    private List<AchievementTag> achievementTagList = new ArrayList<>();

    @OneToMany(mappedBy = "achievement", cascade = CascadeType.REMOVE)
    private List<MemberAchievement> memberAchievementList = new ArrayList<>();
}
