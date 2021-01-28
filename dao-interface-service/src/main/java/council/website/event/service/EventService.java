package council.website.event.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import council.website.event.beans.Event;
import council.website.event.beans.EventWorkflow;
import council.website.event.mapper.EventMapper;

@Service
public class EventService {

	@Autowired
	private EventMapper eventMapper;
	
	public List<Event> getEventsForDateRange(String clubName, Date startDate, Date endDate) {
		// makes SQL call to get the data.
		List<Event> result = eventMapper.getEventsForDateRange(clubName, startDate, endDate);
		return result;
	}
	
	public List<Event> getEventsForSocialMediaShare() {
		// makes SQL call to get the data.
		List<Event> result = eventMapper.getEventsForSocialMediaShare();
		return result;
	}

	public List<EventWorkflow> getWorkflowEventsOfStatus(String clubName, String status) {
		// gets the bulletins for different status for the submitter
		List<EventWorkflow> result = eventMapper.getWorkflowEventsOfStatus(clubName, status);
		return result;
	}

	public Boolean addNewEvent(EventWorkflow workflow) {
		// adds a new bulletin to the table, status will be pending
		workflow.setReviewComments("START");
		eventMapper.addNewEvent(workflow);
		eventMapper.addNewEventWorkflow(workflow);
		return true;
	}
	
	public Boolean approveEvent(EventWorkflow workflow) {
		// approves the bulletin in workflow, performs post approval move as well
		eventMapper.updateEventWorkflow(workflow);
		eventMapper.moveEventPostApproval(workflow);
		return true;
	}
	
	public Boolean rejectEvent(EventWorkflow workflow) {
		// rejects the submitted bulletin workflow
		// updates workflow table
		eventMapper.updateEventWorkflow(workflow);
		return true;
	}

	public Boolean updateEventWorkflow(EventWorkflow request) {
		// updates a bulletin workflow, whether by submitter or approver.
		// updates staging and workflow tables
		eventMapper.updateEventInStaging(request);
		eventMapper.updateEventWorkflow(request);
		return true;
	}

}