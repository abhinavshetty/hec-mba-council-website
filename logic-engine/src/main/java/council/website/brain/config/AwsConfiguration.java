package council.website.brain.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AwsConfiguration {

	@Value("${aws.s3.access.key}")
	private String accessKey;

	@Value("${aws.s3.secret.key}")
	private String secretKey;
	
	@Value("${aws.s3.max.upload.threads}")
	private int maxUploadThreads;
	
	@Value("${aws.s3.max.file.size.mb}")
	private long maxFileSize;

	private AmazonS3 s3client;
	
	@Bean
	public AmazonS3 getS3Client() {
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		if (this.s3client == null) {
			s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
					.withRegion(Regions.EU_WEST_3).build();
		}
		return this.s3client;
	}
}
