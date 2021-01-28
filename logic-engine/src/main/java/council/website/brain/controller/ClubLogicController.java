package council.website.brain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import council.website.action.beans.ActionResponse;
import council.website.brain.service.ClubLogicService;
import council.website.club.beans.Club;
import council.website.club.beans.ClubMetadata;

@RestController
@RequestMapping("/club")
@CrossOrigin
public class ClubLogicController {
	
	@Autowired
	private ClubLogicService clubService;
	
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

	@PostMapping(path = "/addResourceForClub", consumes = "application/json", produces = "application/json")
	public ActionResponse addResourceForClub(@RequestBody Club club) {
		return clubService.addResourceForClub(club);
	}

	@PostMapping(path = "/deleteResourceForClub", consumes = "application/json", produces = "application/json")
	public ActionResponse deleteResourceForClub(@RequestBody Club club) {
		return clubService.deleteResourceForClub(club);
	}
	
	@GetMapping(path = "/getAllClubs", produces = "application/json")
	public List<Club> getAllClubs() {
		return clubService.getAllClubs();
	}
	
	@PostMapping(path = "/addClub", consumes = "application/json", produces = "application/json")
	public ActionResponse addClub(@RequestBody Club club) {
		return clubService.addClub(club);
	}
	
	@PostMapping(path = "/updateClub", consumes = "application/json", produces = "application/json")
	public ActionResponse updateClub(@RequestBody Club club) {
		return clubService.updateClub(club);
	}

}
