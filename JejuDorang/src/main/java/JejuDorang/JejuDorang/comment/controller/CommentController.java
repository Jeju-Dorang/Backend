package JejuDorang.JejuDorang.comment.controller;

import JejuDorang.JejuDorang.auth.argumentresolver.Login;
import JejuDorang.JejuDorang.comment.dto.CommentRequestDto;
import JejuDorang.JejuDorang.comment.service.CommentService;
import JejuDorang.JejuDorang.member.data.Member;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/comment/{questionPostId}")
    public ResponseEntity createComment(
            @PathVariable Long questionPostId,
            @RequestBody CommentRequestDto commentRequestDto,
            @Login Member member) {

        commentService.createComment(questionPostId, commentRequestDto, member);
        return new ResponseEntity(HttpStatus.OK);
    }
}
