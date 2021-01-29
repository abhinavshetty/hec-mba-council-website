package council.website.brain.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import council.website.action.beans.ActionResponse;
import council.website.action.beans.ActionStatus;
import council.website.bulletin.beans.BulletinDataRequest;
import council.website.event.beans.Event;
import council.website.event.beans.EventWorkflow;

@Service
public class EventLogicService {

	@Value("${dao.interface.service.location}")
	private String daoInterfaceUrl;

	private static final String EVENT_CONTROLLER_PATH = "/event";

	@Autowired
	private RestTemplate restTemplate;

	public List<Event> getEventsForRealTime() {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		BulletinDataRequest request = new BulletinDataRequest();
		request.setEndDate(new Date());
		HttpEntity<BulletinDataRequest> requestEntity = new HttpEntity<BulletinDataRequest>(request, headers);

		List<Event> result = restTemplate
				.exchange(daoInterfaceUrl + EventLogicService.EVENT_CONTROLLER_PATH + "/getEventsForDateRange",
						HttpMethod.POST, requestEntity, new ParameterizedTypeReference<List<Event>>() {
						})
				.getBody();
		return result;
	}
	
	public List<Event> getEventsForSocialMediaShare() {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<?> requestEntity = new HttpEntity<>( headers);

		List<Event> result = restTemplate
				.exchange(daoInterfaceUrl + EventLogicService.EVENT_CONTROLLER_PATH + "/getEventsForSocialMediaShare",
						HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Event>>() {
						})
				.getBody();
		return result;
	}

	public List<EventWorkflow> getPendingEventsForSubmitter(BulletinDataRequest request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<BulletinDataRequest> requestEntity = new HttpEntity<BulletinDataRequest>(request, headers);
		request.setStatus(ActionStatus.WORKFLOW_PENDING);
		List<EventWorkflow> result = restTemplate
				.exchange(daoInterfaceUrl + EventLogicService.EVENT_CONTROLLER_PATH + "/getWorkflowEventsOfStatus",
						HttpMethod.POST, requestEntity, new ParameterizedTypeReference<List<EventWorkflow>>() {
						})
				.getBody();
		return result;
	}

	public ActionResponse addNewEvent(EventWorkflow request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		request.setReviewStatus(ActionStatus.WORKFLOW_PENDING);
		HttpEntity<EventWorkflow> requestEntity = new HttpEntity<EventWorkflow>(request, headers);
		restTemplate
				.exchange(daoInterfaceUrl + EventLogicService.EVENT_CONTROLLER_PATH + "/addNewEvent",
						HttpMethod.POST, requestEntity, Boolean.class)
				.getBody();
		
		ActionResponse result = new ActionResponse();
		result.setResponseMessage(ActionStatus.ACTION_SUCCESS);
		result.setResponseDetail(ActionStatus.EVENT_SUBMIT_SUCCESS);
		return result;
	}
	
	public ActionResponse updateEventWorkflow(EventWorkflow request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		request.setReviewStatus(ActionStatus.WORKFLOW_PENDING);
		
		HttpEntity<EventWorkflow> requestEntity = new HttpEntity<EventWorkflow>(request, headers);
		restTemplate
				.exchange(daoInterfaceUrl + EventLogicService.EVENT_CONTROLLER_PATH + "/updateEventWorkflow",
						HttpMethod.POST, requestEntity, Boolean.class)
				.getBody();
		
		ActionResponse result = new ActionResponse();
		result.setResponseMessage(ActionStatus.ACTION_SUCCESS);
		result.setResponseDetail(ActionStatus.EVENT_UPDATE_SUCCESS);
		return result;
	}

	public List<EventWorkflow> getPendingEventsForApprover(BulletinDataRequest request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		request.setStatus(ActionStatus.WORKFLOW_PENDING);
		HttpEntity<BulletinDataRequest> requestEntity = new HttpEntity<BulletinDataRequest>(request, headers);

		List<EventWorkflow> result = restTemplate
				.exchange(daoInterfaceUrl + EventLogicService.EVENT_CONTROLLER_PATH + "/getWorkflowEventsOfStatus",
						HttpMethod.POST, requestEntity, new ParameterizedTypeReference<List<EventWorkflow>>() {
						})
				.getBody();
		return result;
	}

	public ActionResponse approveEvent(EventWorkflow request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		request.setReviewStatus(ActionStatus.WORKFLOW_APPROVED);
		HttpEntity<EventWorkflow> requestEntity = new HttpEntity<EventWorkflow>(request, headers);
		restTemplate
				.exchange(daoInterfaceUrl + EventLogicService.EVENT_CONTROLLER_PATH + "/approveEvent",
						HttpMethod.POST, requestEntity, Boolean.class)
				.getBody();
		
		ActionResponse result = new ActionResponse();
		result.setResponseMessage(ActionStatus.ACTION_SUCCESS);
		result.setResponseDetail(ActionStatus.EVENT_APPROVE_SUCCESS);
		return result;
	}

	public ActionResponse rejectEvent(EventWorkflow request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		request.setReviewStatus(ActionStatus.WORKFLOW_REJECTED);
		HttpEntity<EventWorkflow> requestEntity = new HttpEntity<EventWorkflow>(request, headers);
		restTemplate
				.exchange(daoInterfaceUrl + EventLogicService.EVENT_CONTROLLER_PATH + "/rejectEvent",
						HttpMethod.POST, requestEntity, Boolean.class)
				.getBody();
		
		ActionResponse result = new ActionResponse();
		result.setResponseMessage(ActionStatus.ACTION_SUCCESS);
		result.setResponseDetail(ActionStatus.EVENT_REJECT_SUCCESS);
		return result;
	}

	public List<Event> getApprovedEventsForClub(BulletinDataRequest request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<BulletinDataRequest> requestEntity = new HttpEntity<BulletinDataRequest>(request, headers);

		List<Event> result = restTemplate
				.exchange(daoInterfaceUrl + EventLogicService.EVENT_CONTROLLER_PATH + "/getEventsForDateRange",
						HttpMethod.POST, requestEntity, new ParameterizedTypeReference<List<Event>>() {
						})
				.getBody();
		return result;
	}

	public List<Event> getEventsForDateRange(BulletinDataRequest request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<BulletinDataRequest> requestEntity = new HttpEntity<BulletinDataRequest>(request, headers);

		List<Event> result = restTemplate.exchange(
				daoInterfaceUrl + EVENT_CONTROLLER_PATH + "/getEventsForDateRange",
				HttpMethod.POST, requestEntity, new ParameterizedTypeReference<List<Event>>() {
				}).getBody();
		return result;
	}
}
