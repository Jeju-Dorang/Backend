package JejuDorang.JejuDorang.question.repository;

import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.question.data.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long>  {
    List<Question> findAllByOrderByDateDesc();
}
