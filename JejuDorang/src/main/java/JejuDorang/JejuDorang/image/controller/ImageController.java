package JejuDorang.JejuDorang.image.controller;

import JejuDorang.JejuDorang.auth.argumentresolver.Login;
import JejuDorang.JejuDorang.image.service.ImageService;
import JejuDorang.JejuDorang.member.data.Member;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class ImageController {

    private ImageService imageService;

    @PostMapping("/image/diary")
    public void updateImageInDiary(@RequestBody MultipartFile image, Long diaryId) {

        try {
            String storedUrl = imageService.uploadImage(image);
            imageService.saveUrlInDiary(storedUrl, diaryId);
        } catch (IOException e) {
            System.out.println("s3 에러");
        }
    }

    @PostMapping("/image/profile")
    public void updateProfileImage(@RequestBody MultipartFile image, @Login Member member) {
        try {
            String storedUrl = imageService.uploadImage(image);
            imageService.saveUrlInProfile(storedUrl, member.getId());
        } catch (IOException e) {
            System.out.println("s3 에러");
        }
    }
}
