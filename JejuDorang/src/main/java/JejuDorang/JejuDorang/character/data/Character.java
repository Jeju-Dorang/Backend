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

   @Builder.Default
   @OneToMany(mappedBy = "character")
   private List<BackgroundItem> backgroundItems = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "character")
    private List<PetItem> petItems = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "character")
    private List<StuffItem> stuffItems = new ArrayList<>();
}
