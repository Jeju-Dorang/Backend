package JejuDorang.JejuDorang.character.data;

import JejuDorang.JejuDorang.member.data.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Character {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_id")
    private Long id;

    private int backgroundImage;
    private int itemImage;
    private int petImage;

    public Character(int backgroundImage, int itemImage, int petImage) {
        this.backgroundImage = backgroundImage;
        this.itemImage = itemImage;
        this.petImage = petImage;
    }

    @OneToOne
   @JoinColumn(name = "member_id")
   private Member member;

}
