package council.website.event.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
import council.website.bulletin.beans.BulletinDataRequest;
import council.website.event.beans.Event;
import council.website.event.beans.EventWorkflow;
import council.website.file.manager.service.FileResourceManagerService;

@Service
public class EventWebService {

	private static final String EVENT_LOGIC_CONTROLLER_PATH = "/event";

	@Value("${logic.engine.location}")
	private String logicEngineUrl;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private FileResourceManagerService fileService;
	
	private static String eventResourcesLocation = "event//";

	public List<Event> getEventsForRealTime() {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);

		List<Event> result = restTemplate.exchange(
				logicEngineUrl + EventWebService.EVENT_LOGIC_CONTROLLER_PATH + "/getEventsForRealTime",
				HttpMethod.POST, requestEntity, new ParameterizedTypeReference<List<Event>>() {
				}).getBody();
		return result;
	}


	public List<Event> getApprovedEventsForClub(BulletinDataRequest request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<BulletinDataRequest> requestEntity = new HttpEntity<BulletinDataRequest>(request, headers);

		List<Event> result = restTemplate.exchange(
				logicEngineUrl + EventWebService.EVENT_LOGIC_CONTROLLER_PATH + "/getApprovedEventsForClub",
				HttpMethod.POST, requestEntity, new ParameterizedTypeReference<List<Event>>() {
				}).getBody();
		return result;
	}


	public List<EventWorkflow> getPendingEventsForSubmitter(BulletinDataRequest request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<BulletinDataRequest> requestEntity = new HttpEntity<BulletinDataRequest>(request, headers);

		List<EventWorkflow> result = restTemplate.exchange(
				logicEngineUrl + EventWebService.EVENT_LOGIC_CONTROLLER_PATH + "/getPendingEventsForSubmitter",
				HttpMethod.POST, requestEntity, new ParameterizedTypeReference<List<EventWorkflow>>() {
				}).getBody();
		return result;
	}


	public List<EventWorkflow> getPendingEventsForApprover() {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		BulletinDataRequest request = new BulletinDataRequest();
		HttpEntity<BulletinDataRequest> requestEntity = new HttpEntity<BulletinDataRequest>(request, headers);

		List<EventWorkflow> result = restTemplate.exchange(
				logicEngineUrl + EventWebService.EVENT_LOGIC_CONTROLLER_PATH + "/getPendingEventsForApprover",
				HttpMethod.POST, requestEntity, new ParameterizedTypeReference<List<EventWorkflow>>() {
				}).getBody();
		return result;
	}


	public ActionResponse addNewEvent(EventWorkflow request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<EventWorkflow> requestEntity = new HttpEntity<EventWorkflow>(request, headers);

		ActionResponse result = restTemplate.exchange(
				logicEngineUrl + EventWebService.EVENT_LOGIC_CONTROLLER_PATH + "/addNewEvent",
				HttpMethod.POST, requestEntity, ActionResponse.class).getBody();
		return result;
	}


	public ActionResponse addOrUpdatePosterForEvent(MultipartFile file, String eventName) throws IOException, AmazonServiceException, AmazonClientException, IllegalStateException, InterruptedException {
		fileService.saveFileToFileSystem(file, eventResourcesLocation, file.getOriginalFilename());
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		EventWorkflow request = new EventWorkflow();
		request.setEventName(eventName);
		request.setEventPosterLoc(file.getOriginalFilename());
		HttpEntity<EventWorkflow> requestEntity = new HttpEntity<EventWorkflow>(request, headers);

		ActionResponse result = restTemplate.exchange(
				logicEngineUrl + EventWebService.EVENT_LOGIC_CONTROLLER_PATH + "/updateEventWorkflow",
				HttpMethod.POST, requestEntity, ActionResponse.class).getBody();
		return result;
	}


	public ResponseEntity<Resource> getPosterForEvent(String fileName) throws MalformedURLException, AmazonServiceException, AmazonClientException, InterruptedException {
		Resource resource = fileService.getFileFromFileSystem(eventResourcesLocation, fileName);
		return ResponseEntity.ok().body(resource);
	}


	public ActionResponse updateEventWorkflow(EventWorkflow request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<EventWorkflow> requestEntity = new HttpEntity<EventWorkflow>(request, headers);

		ActionResponse result = restTemplate.exchange(
				logicEngineUrl + EventWebService.EVENT_LOGIC_CONTROLLER_PATH + "/updateEventWorkflow",
				HttpMethod.POST, requestEntity, ActionResponse.class).getBody();
		return result;
	}


	public ActionResponse approveEvent(EventWorkflow request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<EventWorkflow> requestEntity = new HttpEntity<EventWorkflow>(request, headers);

		ActionResponse result = restTemplate.exchange(
				logicEngineUrl + EventWebService.EVENT_LOGIC_CONTROLLER_PATH + "/approveEvent",
				HttpMethod.POST, requestEntity, ActionResponse.class).getBody();
		return result;
	}


	public ActionResponse rejectEvent(EventWorkflow request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<EventWorkflow> requestEntity = new HttpEntity<EventWorkflow>(request, headers);

		ActionResponse result = restTemplate.exchange(
				logicEngineUrl + EventWebService.EVENT_LOGIC_CONTROLLER_PATH + "/rejectEvent",
				HttpMethod.POST, requestEntity, ActionResponse.class).getBody();
		return result;
	}


	public List<Event> getEventsForDateRange(BulletinDataRequest request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<BulletinDataRequest> requestEntity = new HttpEntity<BulletinDataRequest>(request, headers);

		List<Event> result = restTemplate.exchange(
				logicEngineUrl + EventWebService.EVENT_LOGIC_CONTROLLER_PATH + "/getEventsForDateRange",
				HttpMethod.POST, requestEntity, new ParameterizedTypeReference<List<Event>>() {
				}).getBody();
		return result;
	}


}
