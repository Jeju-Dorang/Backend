package JejuDorang.JejuDorang.diary.data;

import JejuDorang.JejuDorang.diary.enums.SecretType;
import JejuDorang.JejuDorang.like.data.LikeDiary;
import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.tag.data.DiaryTag;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Builder
public class Diary {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;

    private String title;

    private LocalDate date;

    private String content;

    private String image;

    @Enumerated(EnumType.STRING)
    private SecretType secret;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "diary")
    private List<DiaryTag> diaryTagList = new ArrayList<>();

    @OneToMany(mappedBy = "diary")
    private List<LikeDiary> likeDiaryList = new ArrayList<>();

}
