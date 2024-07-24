package JejuDorang.JejuDorang.character.data;

import JejuDorang.JejuDorang.member.data.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class Character {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_id")
    private Long id;

    private String image;

   @OneToOne
   @JoinColumn(name = "member_id")
   private Member member;

}
