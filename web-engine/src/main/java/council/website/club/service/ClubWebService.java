package council.website.club.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

import council.website.action.beans.ActionResponse;
import council.website.club.beans.Club;
import council.website.club.beans.ClubMetadata;
import council.website.file.manager.service.FileResourceManagerService;

@Service
public class ClubWebService {
	
	@Autowired
	@Qualifier("logicEngineUrl")
	private String logicEngineUrl;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String CLUB_LOGIC_CONTROLLER_PATH = "/club";
	
	@Autowired
	private FileResourceManagerService fileService;

	private static String clubResourcesLocation = "club//";

	public List<ClubMetadata> getClubMetadata(Club club) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<Club> requestEntity = new HttpEntity<Club>(club, headers);

		List<ClubMetadata> result = restTemplate
				.exchange(logicEngineUrl + CLUB_LOGIC_CONTROLLER_PATH + "/getClubMetadata",
						HttpMethod.POST, requestEntity, new ParameterizedTypeReference<List<ClubMetadata>>() {
						})
				.getBody();
		return result;
	}

	public ActionResponse addClubMetadata(ClubMetadata metadata) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<ClubMetadata> requestEntity = new HttpEntity<ClubMetadata>(metadata, headers);

		ActionResponse result = restTemplate
				.exchange(logicEngineUrl + CLUB_LOGIC_CONTROLLER_PATH + "/addClubMetadata",
						HttpMethod.POST, requestEntity, ActionResponse.class)
				.getBody();
		return result;
	}

	public ActionResponse editClubMetadata(ClubMetadata metadata) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<ClubMetadata> requestEntity = new HttpEntity<ClubMetadata>(metadata, headers);

		ActionResponse result = restTemplate
				.exchange(logicEngineUrl + CLUB_LOGIC_CONTROLLER_PATH + "/editClubMetadata",
						HttpMethod.POST, requestEntity, ActionResponse.class)
				.getBody();
		return result;
	}

	public ActionResponse deleteClubMetadata(Club club) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<Club> requestEntity = new HttpEntity<Club>(club, headers);

		ActionResponse result = restTemplate
				.exchange(logicEngineUrl + CLUB_LOGIC_CONTROLLER_PATH + "/deleteClubMetadata",
						HttpMethod.POST, requestEntity, ActionResponse.class)
				.getBody();
		return result;
	}

	public List<Club> getClubResources(Club club) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<Club> requestEntity = new HttpEntity<Club>(club, headers);

		List<Club> result = restTemplate
				.exchange(logicEngineUrl + CLUB_LOGIC_CONTROLLER_PATH + "/getClubResources",
						HttpMethod.POST, requestEntity, new ParameterizedTypeReference<List<Club>>() {
						})
				.getBody();
		return result;
	}

	public ActionResponse addResourceForClub(MultipartFile file, String clubName) throws IOException, AmazonServiceException, AmazonClientException, IllegalStateException, InterruptedException {
		fileService.saveFileToFileSystem(file, this.getClubResourcesLocation(clubName), file.getOriginalFilename());
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		Club club = new Club();
		club.setClubName(clubName);
		club.setClubResource(file.getOriginalFilename());
		HttpEntity<Club> requestEntity = new HttpEntity<Club>(club, headers);

		ActionResponse result = restTemplate
				.exchange(logicEngineUrl + CLUB_LOGIC_CONTROLLER_PATH + "/addResourceForClub",
						HttpMethod.POST, requestEntity, ActionResponse.class)
				.getBody();
		return result;
	}
	
	public ResponseEntity<Resource> getResourceForClub(Club club) throws MalformedURLException, AmazonServiceException, AmazonClientException, InterruptedException {
		Resource resource = fileService.getFileFromFileSystem(this.getClubResourcesLocation(club.getClubName()), club.getClubResource());
		return ResponseEntity.ok()
				// .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
				// .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
				// resource.getFilename() + "\"")
				.body(resource);
	}

	public ActionResponse deleteResourceForClub(Club club) {
		// delete from service side file system
		fileService.deleteObject(club.getClubResource());
		// record deletion in db
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<Club> requestEntity = new HttpEntity<Club>(club, headers);

		ActionResponse result = restTemplate
				.exchange(logicEngineUrl + CLUB_LOGIC_CONTROLLER_PATH + "/deleteResourceForClub",
						HttpMethod.POST, requestEntity, ActionResponse.class)
				.getBody();
		return result;
	}

	public List<Club> getAllClubs() {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);

		List<Club> result = restTemplate
				.exchange(logicEngineUrl + CLUB_LOGIC_CONTROLLER_PATH + "/getAllClubs",
						HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Club>>() {
						})
				.getBody();
		return result;
	}

	public ActionResponse addClub(MultipartFile file, String clubName) throws IOException, AmazonServiceException, AmazonClientException, IllegalStateException, InterruptedException {
		fileService.saveFileToFileSystem(file, clubResourcesLocation, file.getOriginalFilename());
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		Club request = new Club();
		request.setClubName(clubName);
		request.setClubResource(file.getOriginalFilename());
		HttpEntity<Club> requestEntity = new HttpEntity<Club>(request, headers);

		ActionResponse result = restTemplate.exchange(
				logicEngineUrl + CLUB_LOGIC_CONTROLLER_PATH + "/addClub",
				HttpMethod.POST, requestEntity, ActionResponse.class).getBody();
		return result;
	}

	public ResponseEntity<Resource> getLogoForClub(String fileName) throws MalformedURLException, AmazonServiceException, AmazonClientException, InterruptedException {
		Resource resource = fileService.getFileFromFileSystem(clubResourcesLocation, fileName);
		return ResponseEntity.ok()
				 .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
				 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
				 resource.getFilename() + "\"")
				.body(resource);
	}
	
	private String getClubResourcesLocation(String clubName) {
		return clubResourcesLocation + clubName + "//";
	}

	public ActionResponse updateClub(MultipartFile file, String clubName) throws IOException, AmazonServiceException, AmazonClientException, IllegalStateException, InterruptedException {
		fileService.saveFileToFileSystem(file, clubResourcesLocation, file.getOriginalFilename());
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		Club request = new Club();
		request.setClubName(clubName);
		request.setClubResource(file.getOriginalFilename());
		HttpEntity<Club> requestEntity = new HttpEntity<Club>(request, headers);

		ActionResponse result = restTemplate.exchange(
				logicEngineUrl + CLUB_LOGIC_CONTROLLER_PATH + "/updateClub",
				HttpMethod.POST, requestEntity, ActionResponse.class).getBody();
		return result;
	}

	public ResponseEntity<Resource> getLogoForCouncil() throws AmazonServiceException, AmazonClientException, MalformedURLException, InterruptedException {
		List<Club> clubs = this.getAllClubs();
		clubs.removeIf(item -> !(item.getClubName().equalsIgnoreCase("MBA Council")));
		
		Resource resource = fileService.getFileFromFileSystem(clubResourcesLocation, clubs.get(0).getClubResource());
		return ResponseEntity.ok()
				 .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
				 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
				 resource.getFilename() + "\"")
				.body(resource);
	}
}
