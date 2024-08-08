package JejuDorang.JejuDorang.question.service;

import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.member.repository.MemberRepository;
import JejuDorang.JejuDorang.question.data.Question;
import JejuDorang.JejuDorang.question.dto.QuestionInputDto;
import JejuDorang.JejuDorang.question.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public void createQuestion(QuestionInputDto questionInputDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal();

        Question question = Question.builder()
                .member(member)
                .title(questionInputDto.getTitle())
                .content(questionInputDto.getContent())
                .date(LocalDateTime.now())
                .build();

        questionRepository.save(question);
    }
}
