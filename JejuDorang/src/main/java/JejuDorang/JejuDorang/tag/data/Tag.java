package JejuDorang.JejuDorang.tag.data;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Builder
public class Tag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "tag")
    private List<AchievementTag> achievementTagList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "tag", cascade = CascadeType.REMOVE)
    private List<DiaryTag> diaryTagList = new ArrayList<>();

    public Tag(String name) {
        this.name = name;
    }
}
