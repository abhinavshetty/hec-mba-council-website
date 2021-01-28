package council.website.club.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import council.website.club.beans.Club;
import council.website.club.beans.ClubMetadata;

@Mapper
public interface ClubMapper {

	public List<Club> getAllClubs();
	
	public int addClub(@Param("club") Club club);

	public List<ClubMetadata> getClubMetadata(@Param("clubName") String clubName);

	public int addClubMetadata(@Param("metadata") ClubMetadata metadata);

	public int editClubMetadata(@Param("metadata") ClubMetadata metadata);

	public int deleteClubMetadata(@Param("club") Club club);

	public List<Club> getClubResources(@Param("clubName") String clubName);

	public int addClubResource(@Param("club") Club club);
	
	public int deleteClubResource(@Param("club") Club club);

	public int updateClub(@Param("club") Club club);
}
