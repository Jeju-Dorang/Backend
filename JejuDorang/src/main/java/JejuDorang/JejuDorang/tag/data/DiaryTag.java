package JejuDorang.JejuDorang.tag.data;

import JejuDorang.JejuDorang.diary.data.Diary;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class DiaryTag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;

    public DiaryTag(Tag tag, Diary diary) {
        this.tag = tag;
        this.diary = diary;
    }
}
