package council.website.bulletin.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import council.website.bulletin.beans.Bulletin;
import council.website.bulletin.beans.BulletinWorkflow;

@Mapper
public interface BulletinMapper {

	public List<Bulletin> getBulletinsForDateRange(@Param("clubName") String clubName,
			@Param("startTime") Date startTime, @Param("endTime") Date endTime);
	
	public List<BulletinWorkflow> getWorkflowBulletinsOfStatus(@Param("clubName") String clubName, @Param("status") String status);
	
	public int addNewBulletin(@Param("workflow") BulletinWorkflow workflow);
	
	public int addNewBulletinWorkflow(@Param("workflow") BulletinWorkflow workflow);
	
	public int updateBulletinInStaging(@Param("workflow") BulletinWorkflow workflow);
	
	public int updateBulletinWorkflow(@Param("workflow") BulletinWorkflow workflow);

	public int moveBulletinPostApproval(@Param("workflow") BulletinWorkflow workflow);
}
