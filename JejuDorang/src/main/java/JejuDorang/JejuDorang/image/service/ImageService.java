package JejuDorang.JejuDorang.image.service;

import JejuDorang.JejuDorang.component.S3Key;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final AmazonS3 amazonS3;
    private final String bucketName;

    @Autowired
    public ImageService(S3Key s3Key, AmazonS3 amazonS3) {
        this.bucketName = s3Key.getBucketName();
        this.amazonS3 = amazonS3;
    }

    private String changedImageName(String originName) {
        String random = UUID.randomUUID().toString();
        return random + originName;
    }

    // 이미지 업로드 메서드
    public String uploadImage(MultipartFile imageFile) throws IOException {
        // 1. 고유한 파일 이름 생성
        String originalFilename = imageFile.getOriginalFilename();
        String changedName = changedImageName(originalFilename);

        // 2. S3에 업로드할 파일의 메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(imageFile.getSize());
        metadata.setContentType(imageFile.getContentType());

        // 3. S3에 파일 업로드
        amazonS3.putObject(bucketName, changedName, imageFile.getInputStream(), metadata);

        // 4. 저장된 파일의 URL 반환
        return amazonS3.getUrl(bucketName, changedName).toString();
    }
}
