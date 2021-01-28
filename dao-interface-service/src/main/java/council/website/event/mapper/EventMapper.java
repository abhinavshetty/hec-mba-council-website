package council.website.event.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import council.website.event.beans.Event;
import council.website.event.beans.EventWorkflow;

@Mapper
public interface EventMapper {

	public List<Event> getEventsForDateRange(@Param("clubName") String clubName,
			@Param("startTime") Date startTime, @Param("endTime") Date endTime);
	
	public List<Event> getEventsForSocialMediaShare();
	
	public List<EventWorkflow> getWorkflowEventsOfStatus(@Param("clubName") String clubName, @Param("status") String status);
	
	public int addNewEvent(@Param("workflow") EventWorkflow workflow);
	
	public int addNewEventWorkflow(@Param("workflow") EventWorkflow workflow);
	
	public int updateEventInStaging(@Param("workflow") EventWorkflow workflow);
	
	public int updateEventWorkflow(@Param("workflow") EventWorkflow workflow);

	public int moveEventPostApproval(@Param("workflow") EventWorkflow workflow);
}