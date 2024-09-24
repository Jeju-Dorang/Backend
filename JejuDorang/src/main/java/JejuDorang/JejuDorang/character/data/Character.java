package JejuDorang.JejuDorang.character.data;

import JejuDorang.JejuDorang.item.data.BackgroundItem;
import JejuDorang.JejuDorang.item.data.PetItem;
import JejuDorang.JejuDorang.item.data.StuffItem;
import JejuDorang.JejuDorang.member.data.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
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

   @OneToMany(mappedBy = "character")
   @Builder.Default
   private List<BackgroundItem> backgroundItems = new ArrayList<>();

    @OneToMany(mappedBy = "character")
    @Builder.Default
    private List<PetItem> petItems = new ArrayList<>();

    @OneToMany(mappedBy = "character")
    @Builder.Default
    private List<StuffItem> stuffItems = new ArrayList<>();
}
