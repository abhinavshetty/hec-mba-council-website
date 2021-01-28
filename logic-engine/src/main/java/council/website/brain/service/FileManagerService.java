package council.website.brain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;

@Service
public class FileManagerService {

	@Autowired
	private AmazonS3 s3client;

	@Value("${aws.s3.bucket.name}")
	private String awsBucketName;

	// deleting an object
	public void deleteObject(String objectKey) {
		s3client.deleteObject(awsBucketName, objectKey);
	}
}
