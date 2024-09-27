package JejuDorang.JejuDorang.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class S3Key {

    private String accessKey;
    private String secretKey;
    private String bucketName;
    private String region;
}
