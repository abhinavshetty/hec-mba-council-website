package council.website.club.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import council.website.club.beans.Club;
import council.website.club.beans.ClubMetadata;
import council.website.club.mapper.ClubMapper;

@Service
public class ClubService {

	@Autowired
	private ClubMapper clubMapper;

	public List<ClubMetadata> getClubMetadata(Club club) {
		return clubMapper.getClubMetadata(club.getClubName());
	}

	public Boolean addClubMetadata(ClubMetadata metadata) {
		clubMapper.addClubMetadata(metadata);
		return true;
	}

	public Boolean editClubMetadata(ClubMetadata metadata) {
		clubMapper.editClubMetadata(metadata);
		return true;
	}

	public Boolean deleteClubMetadata(Club club) {
		clubMapper.deleteClubMetadata(club);
		return true;
	}
	
	public List<Club> getClubResources(Club club) {
		return clubMapper.getClubResources(club.getClubName());
	}

	public Boolean addClubResource(Club club) {
		clubMapper.addClubResource(club);
		return true;
	}
	
	public Boolean deleteClubResource(Club club) {
		clubMapper.deleteClubResource(club);
		return true;
	}

	public List<Club> getAllClubs() {
		return clubMapper.getAllClubs();
	}
	
	public Boolean addClub(Club club) {
		clubMapper.addClub(club);
		return true;
	}

	public Boolean updateClub(Club club) {
		clubMapper.updateClub(club);
		return true;
	}
}
