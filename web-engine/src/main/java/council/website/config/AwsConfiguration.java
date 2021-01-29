package council.website.config;

import java.util.List;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;

import council.website.user.beans.UserLoginRequest;
import council.website.user.service.UserWebService;

@Configuration
public class AwsConfiguration {
	
	@Autowired
	private UserWebService userService;
	
	@Value("${aws.s3.max.upload.threads}")
	private int maxUploadThreads;
	
	@Value("${aws.s3.max.file.size.mb}")
	private long maxFileSize;

	private AmazonS3 s3client;

	private TransferManager transferManager;
	
	@Bean
	public AmazonS3 getS3Client() {
		List<UserLoginRequest> properties = userService.getSystemCredentials();
		String accessKey = null, secretKey = null;
		for (UserLoginRequest property : properties) {
			if (property.getUserEmail().equalsIgnoreCase("aws.s3.access.key")) {
				accessKey = property.getUserPassword();
			} else if (property.getUserEmail().equalsIgnoreCase("aws.s3.secret.key")) {
				secretKey = property.getUserPassword();
			}
		}
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		if (this.s3client == null) {
			s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
					.withRegion(Regions.EU_WEST_3).build();
		}
		return this.s3client;
	}
	
	@Bean
	public TransferManager getTransferManager() {
		if (this.transferManager == null) {
			this.transferManager = TransferManagerBuilder.standard()
					.withS3Client(getS3Client())
					.withMultipartUploadThreshold(maxFileSize * 1024*1024)
					.withExecutorFactory(() -> Executors.newCachedThreadPool())
					.build();
		}
		return this.transferManager;
	}
}
