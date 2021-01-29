package council.website.brain.service;

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
import council.website.club.beans.Club;
import council.website.club.beans.ClubMetadata;

@Service
public class ClubLogicService {
	
	@Value("${dao.interface.service.location}")
	private String daoInterfaceUrl;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String CLUB_CONTROLLER_PATH = "/club";

	public List<ClubMetadata> getClubMetadata(Club club) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<Club> requestEntity = new HttpEntity<Club>(club, headers);

		List<ClubMetadata> result = restTemplate
				.exchange(daoInterfaceUrl + CLUB_CONTROLLER_PATH + "/getClubMetadata",
						HttpMethod.POST, requestEntity, new ParameterizedTypeReference<List<ClubMetadata>>() {
						})
				.getBody();
		return result;
	}

	public ActionResponse addClubMetadata(ClubMetadata metadata) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<ClubMetadata> requestEntity = new HttpEntity<ClubMetadata>(metadata, headers);

		restTemplate
				.exchange(daoInterfaceUrl + CLUB_CONTROLLER_PATH + "/addClubMetadata",
						HttpMethod.POST, requestEntity, Boolean.class)
				.getBody();
		
		ActionResponse result = new ActionResponse();
		result.setResponseMessage(ActionStatus.ACTION_SUCCESS);
		result.setResponseDetail(ActionStatus.METADATA_ADD_SUCCESS);
		return result;
	}

	public ActionResponse editClubMetadata(ClubMetadata metadata) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<ClubMetadata> requestEntity = new HttpEntity<ClubMetadata>(metadata, headers);

		restTemplate
				.exchange(daoInterfaceUrl + CLUB_CONTROLLER_PATH + "/editClubMetadata",
						HttpMethod.POST, requestEntity, Boolean.class)
				.getBody();
		
		ActionResponse result = new ActionResponse();
		result.setResponseMessage(ActionStatus.ACTION_SUCCESS);
		result.setResponseDetail(ActionStatus.METADATA_UPDATE_SUCCESS);
		return result;
	}

	public ActionResponse deleteClubMetadata(Club club) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<Club> requestEntity = new HttpEntity<Club>(club, headers);

		restTemplate
				.exchange(daoInterfaceUrl + CLUB_CONTROLLER_PATH + "/deleteClubMetadata",
						HttpMethod.POST, requestEntity, Boolean.class)
				.getBody();
		
		ActionResponse result = new ActionResponse();
		result.setResponseMessage(ActionStatus.ACTION_SUCCESS);
		result.setResponseDetail(ActionStatus.METADATA_DELETE_SUCCESS);
		return result;
	}

	public List<Club> getClubResources(Club club) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<Club> requestEntity = new HttpEntity<Club>(club, headers);

		List<Club> result = restTemplate
				.exchange(daoInterfaceUrl + CLUB_CONTROLLER_PATH + "/getClubResources",
						HttpMethod.POST, requestEntity, new ParameterizedTypeReference<List<Club>>() {
						})
				.getBody();
		return result;
	}

	public ActionResponse addResourceForClub(Club club) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<Club> requestEntity = new HttpEntity<Club>(club, headers);

		restTemplate
				.exchange(daoInterfaceUrl + CLUB_CONTROLLER_PATH + "/addResourceForClub",
						HttpMethod.POST, requestEntity, Boolean.class)
				.getBody();
		
		ActionResponse result = new ActionResponse();
		result.setResponseMessage(ActionStatus.ACTION_SUCCESS);
		result.setResponseDetail(ActionStatus.RESOURCE_ADD_SUCCESS);
		return result;
	}

	public ActionResponse deleteResourceForClub(Club club) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<Club> requestEntity = new HttpEntity<Club>(club, headers);

		restTemplate
				.exchange(daoInterfaceUrl + CLUB_CONTROLLER_PATH + "/deleteResourceForClub",
						HttpMethod.POST, requestEntity, Boolean.class)
				.getBody();
		
		ActionResponse result = new ActionResponse();
		result.setResponseMessage(ActionStatus.ACTION_SUCCESS);
		result.setResponseDetail(ActionStatus.RESOURCE_DELETE_SUCCESS);
		
		return result;
	}

	public List<Club> getAllClubs() {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);

		List<Club> result = restTemplate
				.exchange(daoInterfaceUrl + CLUB_CONTROLLER_PATH + "/getAllClubs",
						HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Club>>() {
						})
				.getBody();
		return result;
	}

	public ActionResponse addClub(Club club) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<Club> requestEntity = new HttpEntity<Club>(club, headers);

		restTemplate
				.exchange(daoInterfaceUrl + CLUB_CONTROLLER_PATH + "/addClub",
						HttpMethod.POST, requestEntity, Boolean.class)
				.getBody();
		
		ActionResponse result = new ActionResponse();
		result.setResponseMessage(ActionStatus.ACTION_SUCCESS);
		result.setResponseDetail(ActionStatus.CLUB_ADD_SUCCESS);
		
		return result;
	}

	public ActionResponse updateClub(Club club) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("content-type", "application/json");
		HttpEntity<Club> requestEntity = new HttpEntity<Club>(club, headers);

		restTemplate
				.exchange(daoInterfaceUrl + CLUB_CONTROLLER_PATH + "/updateClub",
						HttpMethod.POST, requestEntity, Boolean.class)
				.getBody();
		
		ActionResponse result = new ActionResponse();
		result.setResponseMessage(ActionStatus.ACTION_SUCCESS);
		result.setResponseDetail(ActionStatus.CLUB_UPDATE_SUCCESS);
		
		return result;
	}

}
