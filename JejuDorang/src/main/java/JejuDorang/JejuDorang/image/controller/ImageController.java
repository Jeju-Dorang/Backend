package JejuDorang.JejuDorang.image.controller;

import JejuDorang.JejuDorang.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/image/save")
    public ResponseEntity saveDiaryImage(@RequestBody MultipartFile image, Long diaryId) {
        try {
            String storedImageUrl = imageService.uploadImage(image, diaryId);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload image: " + e.getMessage());
        }

        return ResponseEntity.ok().build();
    }
}
