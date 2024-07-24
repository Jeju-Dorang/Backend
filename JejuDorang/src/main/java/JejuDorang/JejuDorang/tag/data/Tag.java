package JejuDorang.JejuDorang.tag.data;

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
public class Tag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "tag")
    private List<AchievementTag> achievementTagList = new ArrayList<>();

    @OneToMany(mappedBy = "tag")
    private List<DiaryTag> diaryTagList = new ArrayList<>();
}
