package JejuDorang.JejuDorang.comment.data;

import java.time.LocalDateTime;
import java.util.List;

import JejuDorang.JejuDorang.like.data.LikeComment;
import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.question.data.Question;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Comment {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_id")
	private Question question;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	private LocalDateTime date;

	private String content;

	@OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
	private List<LikeComment> likeCommentList;

}
