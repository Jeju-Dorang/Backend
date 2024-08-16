package JejuDorang.JejuDorang.view.data;

import JejuDorang.JejuDorang.diary.data.Diary;
import JejuDorang.JejuDorang.member.data.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Builder
public class View {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;
}
