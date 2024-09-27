package JejuDorang.JejuDorang.config;

import JejuDorang.JejuDorang.component.S3Key;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Bean
    public S3Key s3Key() {
        Dotenv dotenv = Dotenv.configure().load();

        return new S3Key(
                dotenv.get("S3_ACCESS_KEY"),
                dotenv.get("S3_SECRET_KEY"),
                dotenv.get("S3_BUCKET_NAME"),
                dotenv.get("S3_REGION")
        );
    }

    @Bean
    public AmazonS3 amazonS3Client(S3Key s3Key) {

        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(s3Key.getAccessKey(), s3Key.getSecretKey());

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .withRegion(s3Key.getRegion())
                .build();
    }
}
