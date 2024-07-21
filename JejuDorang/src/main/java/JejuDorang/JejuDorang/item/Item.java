package JejuDorang.JejuDorang.item;

import JejuDorang.JejuDorang.achievement.Achievement;
import JejuDorang.JejuDorang.character.Character;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String image;

    private String name;

    @Enumerated(EnumType.STRING)
    private ItemType category;

    @ManyToOne
    @JoinColumn(name = "character_id")
    private Character character;

    @OneToOne(mappedBy = "item")
    private Achievement achievement;
}
