package JejuDorang.JejuDorang.achievement;

import JejuDorang.JejuDorang.item.Item;
import JejuDorang.JejuDorang.tag.AchievementTag;
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
}
