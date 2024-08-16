package JejuDorang.JejuDorang.comment.repository;

import JejuDorang.JejuDorang.comment.data.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
