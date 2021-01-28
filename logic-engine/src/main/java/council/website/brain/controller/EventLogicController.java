package council.website.brain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import council.website.action.beans.ActionResponse;
import council.website.brain.service.EventLogicService;
import council.website.bulletin.beans.BulletinDataRequest;
import council.website.event.beans.Event;
import council.website.event.beans.EventWorkflow;

@RestController
@RequestMapping("/event")
@CrossOrigin
public class EventLogicController {

	@Autowired
	private EventLogicService eventService;
	
	@PostMapping(path = "/getEventsForRealTime", consumes = "application/json", produces = "application/json")
	public List<Event> getEventsForRealTime() {
		return eventService.getEventsForRealTime();
	}
	
	@PostMapping(path = "/getEventsForDateRange", consumes = "application/json", produces = "application/json")
	public List<Event> getEventsForDateRange(@RequestBody BulletinDataRequest request) {
		return eventService.getEventsForDateRange(request);
	}
	
	@PostMapping(path = "/getApprovedEventsForClub", consumes = "application/json", produces = "application/json")
	public List<Event> getApprovedEventsForClub(@RequestBody BulletinDataRequest request) {
		return eventService.getApprovedEventsForClub(request);
	}
	
	@PostMapping(path = "/getPendingEventsForApprover", consumes = "application/json", produces = "application/json")
	public List<EventWorkflow> getPendingEventsForApprover(@RequestBody BulletinDataRequest request) {
		return eventService.getPendingEventsForApprover(request);
	}
	
	@PostMapping(path = "/getPendingEventsForSubmitter", consumes = "application/json", produces = "application/json")
	public List<EventWorkflow> getPendingEventsForSubmitter(@RequestBody BulletinDataRequest request) {
		return eventService.getPendingEventsForSubmitter(request);
	}
	
	@PostMapping(path = "/addNewEvent", consumes = "application/json", produces = "application/json")
	public ActionResponse addNewEvent(@RequestBody EventWorkflow request) {
		return eventService.addNewEvent(request);
	}
	
	@PostMapping(path = "/updateEventWorkflow", consumes = "application/json", produces = "application/json")
	public ActionResponse updateEventWorkflow(@RequestBody EventWorkflow request) {
		return eventService.updateEventWorkflow(request);
	}
	
	@PostMapping(path = "/approveEvent", consumes = "application/json", produces = "application/json")
	public ActionResponse approveEvent(@RequestBody EventWorkflow request) {
		return eventService.approveEvent(request);
	}
	
	@PostMapping(path = "/rejectEvent", consumes = "application/json", produces = "application/json")
	public ActionResponse rejectEvent(@RequestBody EventWorkflow request) {
		return eventService.rejectEvent(request);
	}
}