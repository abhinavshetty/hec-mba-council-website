/**
 * 
 */
package council.website.event.workflow.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import council.website.event.beans.ApprovedEvent;
import council.website.event.beans.StagingEvent;
import council.website.workflow.beans.Workflow;

/**
 * @author Abhinav Shetty
 *
 */
@Mapper
public interface EventWorkflowMapper {

	public List<Workflow> getEventWorkflowsForStatus(@Param("reviewStatus") String reviewStatus);
	
	public int updateEventWorkflowStatus(@Param("workflow") Workflow workflow);
	
	public int addNewEventForStaging(@Param("event") StagingEvent event);
	
	public int updateEventInStaging(@Param("event") StagingEvent event);
	
	public int addNewEventPostApproval(@Param("event") ApprovedEvent event);
	
	
}
