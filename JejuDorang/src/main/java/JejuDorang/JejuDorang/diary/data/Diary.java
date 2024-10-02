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

    @Builder.Default
    private String image = "https://jejudorangs3.s3.ap-northeast-2.amazonaws.com/icon/%EC%8A%A4%ED%86%A0%EB%A6%AC_%EA%B8%B0%EB%B3%B8_%EC%9D%B4%EB%AF%B8%EC%A7%80.png";

    private String imageName;

    @Enumerated(EnumType.STRING)
    private SecretType secret;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder.Default
    @OneToMany(mappedBy = "diary", cascade = CascadeType.REMOVE)
    private List<DiaryTag> diaryTagList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "diary", cascade = CascadeType.REMOVE)
    private List<LikeDiary> likeDiaryList = new ArrayList<>();


    public void updateImage(String url) {
        this.image = url;
    }

    public void updateImageName(String name) {
        this.imageName = name;
    }

}
