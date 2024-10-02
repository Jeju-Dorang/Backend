package JejuDorang.JejuDorang.question.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import JejuDorang.JejuDorang.comment.data.Comment;
import JejuDorang.JejuDorang.member.data.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Question {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	private String title;

	private LocalDateTime date;

	private String content;

	@Builder.Default
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	private List<Comment> commentList = new ArrayList<>();
}
