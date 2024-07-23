package JejuDorang.JejuDorang.achievement.data;

import JejuDorang.JejuDorang.achievement.enums.AchievementType;
import JejuDorang.JejuDorang.item.data.Item;
import JejuDorang.JejuDorang.member.data.MemberAchievement;
import JejuDorang.JejuDorang.tag.data.AchievementTag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class Achievement {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "achievement_id")
    private Long id;

    private String name;

    private String content;

    private Long maxAchieve;

    private String image;

    @Enumerated(EnumType.STRING)
    private AchievementType category;

    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @OneToMany(mappedBy = "achievement")
    private List<AchievementTag> achievementTagList = new ArrayList<>();

    @OneToMany(mappedBy = "achievement")
    private List<MemberAchievement> memberAchievementList = new ArrayList<>();
}
