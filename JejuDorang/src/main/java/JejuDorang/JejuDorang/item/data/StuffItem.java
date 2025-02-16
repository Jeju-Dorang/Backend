package JejuDorang.JejuDorang.item.data;

import JejuDorang.JejuDorang.character.data.Character;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class StuffItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stuff_id")
    private Long id;

    private Boolean getItem;

    private Long index;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id")
    private Character character;

    public void updateStatus() {
        this.getItem = true;
    }
}
