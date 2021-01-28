package council.website.club.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

import council.website.action.beans.ActionResponse;
import council.website.club.beans.Club;
import council.website.club.beans.ClubMetadata;
import council.website.club.service.ClubWebService;

@RestController
@RequestMapping("/club")
@CrossOrigin
public class ClubWebController {
	
	@Autowired
	private ClubWebService clubService;
	
	@PostMapping(path = "/getClubMetadata", consumes = "application/json", produces = "application/json")
	public List<ClubMetadata> getClubMetadata(@RequestBody Club club) {
		return clubService.getClubMetadata(club);
	}

	@PostMapping(path = "/addClubMetadata", consumes = "application/json", produces = "application/json")
	public ActionResponse addClubMetadata(@RequestBody ClubMetadata metadata) {
		return clubService.addClubMetadata(metadata);
	}

	@PostMapping(path = "/editClubMetadata", consumes = "application/json", produces = "application/json")
	public ActionResponse editClubMetadata(@RequestBody ClubMetadata metadata) {
		return clubService.editClubMetadata(metadata);
	}

	@PostMapping(path = "/deleteClubMetadata", consumes = "application/json", produces = "application/json")
	public ActionResponse deleteClubMetadata(@RequestBody Club club) {
		return clubService.deleteClubMetadata(club);
	}
	
	@PostMapping(path = "/getClubResources", consumes = "application/json", produces = "application/json")
	public List<Club> getClubResources(@RequestBody Club club) {
		return clubService.getClubResources(club);
	}
	
	@RequestMapping(path = "/getResourceForClub")
	public ResponseEntity<Resource> getResourceForClub(@RequestBody Club club) throws MalformedURLException, AmazonServiceException, AmazonClientException, InterruptedException {
		return clubService.getResourceForClub(club);
	}

	@PostMapping(path = "/deleteResourceForClub", consumes = "application/json", produces = "application/json")
	public ActionResponse deleteResourceForClub(@RequestBody Club club) {
		return clubService.deleteResourceForClub(club);
	}
	
	@PostMapping(path = "/addResourceForClub", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ActionResponse addResourceForClub(@RequestParam("file") MultipartFile file,
			@RequestParam("clubName") String clubName) throws IOException, AmazonServiceException, AmazonClientException, IllegalStateException, InterruptedException {
		return clubService.addResourceForClub(file, clubName);
	}
	
	@GetMapping(path = "/getAllClubs", produces = "application/json")
	public List<Club> getAllClubs() {
		return clubService.getAllClubs();
	}
	
	@PostMapping(path = "/addClub", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ActionResponse addClub(@RequestParam("file") MultipartFile file,
			@RequestParam("clubName") String clubName) throws IOException, AmazonServiceException, AmazonClientException, IllegalStateException, InterruptedException {
		return clubService.addClub(file, clubName);
	}
	
	@PostMapping(path = "/updateClub", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ActionResponse updateClub(@RequestParam("file") MultipartFile file,
			@RequestParam("clubName") String clubName) throws IOException, AmazonServiceException, AmazonClientException, IllegalStateException, InterruptedException {
		return clubService.updateClub(file, clubName);
	}
	
	@RequestMapping(path = "/getLogoForClub")
	public ResponseEntity<Resource> getLogoForClub(@RequestParam String fileName) throws MalformedURLException, AmazonServiceException, AmazonClientException, InterruptedException {
		return clubService.getLogoForClub(fileName);
	}
	
	@RequestMapping(path = "/getLogoForCouncil")
	public ResponseEntity<Resource> getLogoForCouncil() throws MalformedURLException, AmazonServiceException, AmazonClientException, InterruptedException {
		return clubService.getLogoForCouncil();
	}

}
