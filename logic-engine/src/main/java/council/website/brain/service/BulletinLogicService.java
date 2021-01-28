package council.website.brain.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import council.website.action.beans.ActionResponse;
import council.website.action.beans.ActionStatus;
import council.website.bulletin.beans.Bulletin;
import council.website.bulletin.beans.BulletinDataRequest;
import council.website.bulletin.beans.BulletinWorkflow;

@Service
public class BulletinLogicService {

	@Autowired
	@Qualifier("daoInterfaceUrl")
	private String daoInterfaceUrl;

	private static final String BULLETIN_CONTROLLER_PATH = "/bulletin";

	@Autowired
	private RestTemplate restTemplate;

	public List<Bulletin> getBulletinsForRealTime() {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		BulletinDataRequest request = new BulletinDataRequest();
		request.setEndDate(new Date());
		HttpEntity<BulletinDataRequest> requestEntity = new HttpEntity<BulletinDataRequest>(request, headers);

		List<Bulletin> result = restTemplate
				.exchange(daoInterfaceUrl + BulletinLogicService.BULLETIN_CONTROLLER_PATH + "/getBulletinsForDateRange",
						HttpMethod.POST, requestEntity, new ParameterizedTypeReference<List<Bulletin>>() {
						})
				.getBody();
		return result;
	}

	public List<BulletinWorkflow> getPendingBulletinsForSubmitter(BulletinDataRequest request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<BulletinDataRequest> requestEntity = new HttpEntity<BulletinDataRequest>(request, headers);
		request.setStatus(ActionStatus.WORKFLOW_PENDING);
		List<BulletinWorkflow> result = restTemplate
				.exchange(daoInterfaceUrl + BulletinLogicService.BULLETIN_CONTROLLER_PATH + "/getWorkflowBulletinsOfStatus",
						HttpMethod.POST, requestEntity, new ParameterizedTypeReference<List<BulletinWorkflow>>() {
						})
				.getBody();
		return result;
	}

	public ActionResponse addNewBulletin(BulletinWorkflow request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		request.setReviewStatus(ActionStatus.WORKFLOW_PENDING);
		HttpEntity<BulletinWorkflow> requestEntity = new HttpEntity<BulletinWorkflow>(request, headers);
		restTemplate
				.exchange(daoInterfaceUrl + BulletinLogicService.BULLETIN_CONTROLLER_PATH + "/addNewBulletin",
						HttpMethod.POST, requestEntity, Boolean.class)
				.getBody();
		
		ActionResponse result = new ActionResponse();
		result.setResponseMessage(ActionStatus.ACTION_SUCCESS);
		result.setResponseDetail(ActionStatus.BULLETIN_SUBMIT_SUCCESS);
		return result;
	}
	
	public ActionResponse updateBulletinWorkflow(BulletinWorkflow request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		request.setReviewStatus(ActionStatus.WORKFLOW_PENDING);
		
		HttpEntity<BulletinWorkflow> requestEntity = new HttpEntity<BulletinWorkflow>(request, headers);
		restTemplate
				.exchange(daoInterfaceUrl + BulletinLogicService.BULLETIN_CONTROLLER_PATH + "/updateBulletinWorkflow",
						HttpMethod.POST, requestEntity, Boolean.class)
				.getBody();
		
		ActionResponse result = new ActionResponse();
		result.setResponseMessage(ActionStatus.ACTION_SUCCESS);
		result.setResponseDetail(ActionStatus.BULLETIN_UPDATE_SUCCESS);
		return result;
	}

	public List<BulletinWorkflow> getPendingBulletinsForApprover(BulletinDataRequest request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		request.setStatus(ActionStatus.WORKFLOW_PENDING);
		HttpEntity<BulletinDataRequest> requestEntity = new HttpEntity<BulletinDataRequest>(request, headers);

		List<BulletinWorkflow> result = restTemplate
				.exchange(daoInterfaceUrl + BulletinLogicService.BULLETIN_CONTROLLER_PATH + "/getWorkflowBulletinsOfStatus",
						HttpMethod.POST, requestEntity, new ParameterizedTypeReference<List<BulletinWorkflow>>() {
						})
				.getBody();
		return result;
	}

	public ActionResponse approveBulletin(BulletinWorkflow request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		request.setReviewStatus(ActionStatus.WORKFLOW_APPROVED);
		HttpEntity<BulletinWorkflow> requestEntity = new HttpEntity<BulletinWorkflow>(request, headers);
		restTemplate
				.exchange(daoInterfaceUrl + BulletinLogicService.BULLETIN_CONTROLLER_PATH + "/approveBulletin",
						HttpMethod.POST, requestEntity, Boolean.class)
				.getBody();
		
		ActionResponse result = new ActionResponse();
		result.setResponseMessage(ActionStatus.ACTION_SUCCESS);
		result.setResponseDetail(ActionStatus.BULLETIN_APPROVE_SUCCESS);
		return result;
	}

	public ActionResponse rejectBulletin(BulletinWorkflow request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		request.setReviewStatus(ActionStatus.WORKFLOW_REJECTED);
		HttpEntity<BulletinWorkflow> requestEntity = new HttpEntity<BulletinWorkflow>(request, headers);
		restTemplate
				.exchange(daoInterfaceUrl + BulletinLogicService.BULLETIN_CONTROLLER_PATH + "/rejectBulletin",
						HttpMethod.POST, requestEntity, Boolean.class)
				.getBody();
		
		ActionResponse result = new ActionResponse();
		result.setResponseMessage(ActionStatus.ACTION_SUCCESS);
		result.setResponseDetail(ActionStatus.BULLETIN_REJECT_SUCCESS);
		return result;
	}

	public List<Bulletin> getApprovedBulletinsForClub(BulletinDataRequest request) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<BulletinDataRequest> requestEntity = new HttpEntity<BulletinDataRequest>(request, headers);

		List<Bulletin> result = restTemplate
				.exchange(daoInterfaceUrl + BulletinLogicService.BULLETIN_CONTROLLER_PATH + "/getBulletinsForDateRange",
						HttpMethod.POST, requestEntity, new ParameterizedTypeReference<List<Bulletin>>() {
						})
				.getBody();
		return result;
	}
}
