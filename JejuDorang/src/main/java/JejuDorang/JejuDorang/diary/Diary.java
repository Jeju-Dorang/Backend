package JejuDorang.JejuDorang.diary;

import JejuDorang.JejuDorang.like.LikeDiary;
import JejuDorang.JejuDorang.tag.DiaryTag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class Diary {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;

    private String title;

    private LocalDateTime date;

    private String content;

    private String image;

    @Enumerated(EnumType.STRING)
    private SecretType secret;

    @ManyToOne
    @JoinColumn("member_id")
    private Member member;

    @OneToMany(mappedBy = "diary")
    private List<DiaryTag> diaryTagList = new ArrayList<>();

    @OneToMany(mappedBy = "diary")
    private List<LikeDiary> likeDiaryList = new ArrayList<>();

}
