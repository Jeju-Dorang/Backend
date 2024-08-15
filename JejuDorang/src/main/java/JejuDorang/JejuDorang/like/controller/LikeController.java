package JejuDorang.JejuDorang.like.controller;

import JejuDorang.JejuDorang.like.service.LikeService;
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
    public ResponseEntity<Void> createLikeComment(@PathVariable Long commentId) {

        likeService.createLikeComment(commentId);
        return ResponseEntity.ok().build();
    }
}
