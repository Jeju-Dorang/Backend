package JejuDorang.JejuDorang.like.controller;

import JejuDorang.JejuDorang.auth.argumentresolver.Login;
import JejuDorang.JejuDorang.like.service.LikeService;
import JejuDorang.JejuDorang.member.data.Member;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts/like")
@AllArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/comment/{commentId}")
    public ResponseEntity<Void> createLikeComment(@PathVariable Long commentId, @Login Member member) {

        likeService.createLikeComment(commentId, member);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/diary/{diaryId}")
    public ResponseEntity<Void> createLikeDiary(@PathVariable Long diaryId, @Login Member member) {

        likeService.createLikeDiary(diaryId, member);
        return ResponseEntity.ok().build();
    }
}
