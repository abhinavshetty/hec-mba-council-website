package council.website.bulletin.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

import council.website.action.beans.ActionResponse;
import council.website.bulletin.beans.Bulletin;
import council.website.bulletin.beans.BulletinDataRequest;
import council.website.bulletin.beans.BulletinWorkflow;
import council.website.file.manager.service.FileResourceManagerService;

@Service
public class BulletinWebService {

	private static final String BULLETIN_LOGIC_CONTROLLER_PATH = "/bulletin";

	@Autowired
	@Qualifier("logicEngineUrl")
	private String logicEngineUrl;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private FileResourceManagerService fileService;

	private static String bulletinResourcesLocation = "bulletin//";

	public List<Bulletin> getBulletinsForRealTime() {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);

		List<Bulletin> result = restTemplate.exchange(
				logicEngineUrl + BulletinWebService.BULLETIN_LOGIC_CONTROLLER_PATH + "/getBulletinsForRealTime",
				HttpMethod.POST, requestEntity, new ParameterizedTypeReference<List<Bulletin>>() {
				}).getBody();
		return result;
	}

	public List<Bulletin> getApprovedBulletinsForClub(BulletinDataRequest request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<BulletinDataRequest> requestEntity = new HttpEntity<BulletinDataRequest>(request, headers);

		List<Bulletin> result = restTemplate.exchange(
				logicEngineUrl + BulletinWebService.BULLETIN_LOGIC_CONTROLLER_PATH + "/getApprovedBulletinsForClub",
				HttpMethod.POST, requestEntity, new ParameterizedTypeReference<List<Bulletin>>() {
				}).getBody();
		return result;
	}

	public List<BulletinWorkflow> getPendingBulletinsForSubmitter(BulletinDataRequest request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<BulletinDataRequest> requestEntity = new HttpEntity<BulletinDataRequest>(request, headers);

		List<BulletinWorkflow> result = restTemplate.exchange(
				logicEngineUrl + BulletinWebService.BULLETIN_LOGIC_CONTROLLER_PATH + "/getPendingBulletinsForSubmitter",
				HttpMethod.POST, requestEntity, new ParameterizedTypeReference<List<BulletinWorkflow>>() {
				}).getBody();
		return result;
	}

	public List<BulletinWorkflow> getPendingBulletinsForApprover() {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		BulletinDataRequest request = new BulletinDataRequest();
		HttpEntity<BulletinDataRequest> requestEntity = new HttpEntity<BulletinDataRequest>(request, headers);

		List<BulletinWorkflow> result = restTemplate.exchange(
				logicEngineUrl + BulletinWebService.BULLETIN_LOGIC_CONTROLLER_PATH + "/getPendingBulletinsForApprover",
				HttpMethod.POST, requestEntity, new ParameterizedTypeReference<List<BulletinWorkflow>>() {
				}).getBody();
		return result;
	}

	public ActionResponse addNewBulletin(BulletinWorkflow request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<BulletinWorkflow> requestEntity = new HttpEntity<BulletinWorkflow>(request, headers);

		ActionResponse result = restTemplate
				.exchange(logicEngineUrl + BulletinWebService.BULLETIN_LOGIC_CONTROLLER_PATH + "/addNewBulletin",
						HttpMethod.POST, requestEntity, ActionResponse.class)
				.getBody();
		return result;
	}

	public ActionResponse updateBulletinWorkflow(BulletinWorkflow request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<BulletinWorkflow> requestEntity = new HttpEntity<BulletinWorkflow>(request, headers);

		ActionResponse result = restTemplate.exchange(
				logicEngineUrl + BulletinWebService.BULLETIN_LOGIC_CONTROLLER_PATH + "/updateBulletinWorkflow",
				HttpMethod.POST, requestEntity, ActionResponse.class).getBody();
		return result;
	}

	public ActionResponse approveBulletin(BulletinWorkflow request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<BulletinWorkflow> requestEntity = new HttpEntity<BulletinWorkflow>(request, headers);

		ActionResponse result = restTemplate
				.exchange(logicEngineUrl + BulletinWebService.BULLETIN_LOGIC_CONTROLLER_PATH + "/approveBulletin",
						HttpMethod.POST, requestEntity, ActionResponse.class)
				.getBody();
		return result;
	}

	public ActionResponse rejectBulletin(BulletinWorkflow request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<BulletinWorkflow> requestEntity = new HttpEntity<BulletinWorkflow>(request, headers);

		ActionResponse result = restTemplate
				.exchange(logicEngineUrl + BulletinWebService.BULLETIN_LOGIC_CONTROLLER_PATH + "/rejectBulletin",
						HttpMethod.POST, requestEntity, ActionResponse.class)
				.getBody();
		return result;
	}

	public ActionResponse addOrUpdatePosterForBulletin(MultipartFile file, String bulletinTitle) throws IOException, AmazonServiceException, AmazonClientException, IllegalStateException, InterruptedException {
		fileService.saveFileToFileSystem(file, bulletinResourcesLocation, file.getOriginalFilename());
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		BulletinWorkflow request = new BulletinWorkflow();
		request.setBulletinTitle(bulletinTitle);
		request.setBulletinPosterLoc(file.getOriginalFilename());
		HttpEntity<BulletinWorkflow> requestEntity = new HttpEntity<BulletinWorkflow>(request, headers);

		ActionResponse result = restTemplate.exchange(
				logicEngineUrl + BulletinWebService.BULLETIN_LOGIC_CONTROLLER_PATH + "/updateBulletinWorkflow",
				HttpMethod.POST, requestEntity, ActionResponse.class).getBody();
		return result;
		// return null;
	}

	public ResponseEntity<Resource> getPosterForBulletin(String fileName) throws MalformedURLException, AmazonServiceException, AmazonClientException, InterruptedException {
		Resource resource = fileService.getFileFromFileSystem(bulletinResourcesLocation, fileName);
		return ResponseEntity.ok()
				// .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
				// .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
				// resource.getFilename() + "\"")
				.body(resource);
	}

}
