package council.website.file.manager.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CopyObjectResult;
import com.amazonaws.services.s3.transfer.Download;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

@Service
public class FileResourceManagerService {

	@Autowired
	private AmazonS3 s3client;

	@Autowired
	private TransferManager transferManager;

	@Value("${aws.s3.bucket.name}")
	private String awsBucketName;

	public Boolean saveFileToFileSystem(MultipartFile file, String fileBasePath, String fileName) throws IOException,
			AmazonServiceException, AmazonClientException, IllegalStateException, InterruptedException {
		if (file != null) {
			this.putObject(file);
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	public Resource getFileFromFileSystem(String resourcePath, String resourceName)
			throws MalformedURLException, AmazonServiceException, AmazonClientException, InterruptedException {
		File resultFile = this.getObject(resourceName);
		Resource result = null;
		result = new UrlResource(resultFile.toURL());
		return result;
	}

	// uploading object
	public Boolean putObject(MultipartFile file) throws IllegalStateException, IOException, AmazonServiceException,
			AmazonClientException, InterruptedException {
		Upload upload = transferManager.upload(awsBucketName, file.getOriginalFilename(), file.getInputStream(), null);
		upload.waitForCompletion();
		return true;
	}

	// get an object
	public File getObject(String objectKey) throws AmazonServiceException, AmazonClientException, InterruptedException {
		File resultFile = new File(objectKey);
		Download result = transferManager.download(awsBucketName, objectKey, resultFile);
		result.waitForCompletion();
		return resultFile;
	}

	// copying an object
	public CopyObjectResult copyObject(String sourceBucketName, String sourceKey, String destinationBucketName,
			String destinationKey) {
		return s3client.copyObject(sourceBucketName, sourceKey, destinationBucketName, destinationKey);
	}

	// deleting an object
	public void deleteObject(String objectKey) {
		s3client.deleteObject(awsBucketName, objectKey);
	}

}
