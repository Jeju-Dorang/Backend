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

    // 댓글 좋아요
    @PostMapping("/comment/{commentId}")
    public ResponseEntity<Void> createLikeComment(@PathVariable Long commentId, @Login Member member) {

        boolean alreayLike = likeService.alreadyLikeComment(commentId, member.getId());
        if (!alreayLike)
            likeService.createLikeComment(commentId, member);
        else
            likeService.deleteLikeComment(commentId, member);
        return ResponseEntity.ok().build();
    }

    // 일기 좋아요
    @PostMapping("/diary/{diaryId}")
    public ResponseEntity<Void> createLikeDiary(@PathVariable Long diaryId, @Login Member member) {

        boolean alreadyLike = likeService.alreadyLikeDiary(diaryId, member.getId());
        if (!alreadyLike)
            likeService.createLikeDiary(diaryId, member);
        else
            likeService.deleteLikeDiary(diaryId, member);
        return ResponseEntity.ok().build();
    }
}
