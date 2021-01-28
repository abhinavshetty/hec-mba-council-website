package council.website.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import council.website.bulletin.beans.BulletinDataRequest;
import council.website.event.beans.Event;
import council.website.event.beans.EventWorkflow;
import council.website.event.service.EventService;

@RestController
@RequestMapping("/event")
@CrossOrigin
public class EventController {
	
	@Autowired
	private EventService eventService;

	@PostMapping(path = "/getEventsForDateRange", consumes = "application/json", produces = "application/json")
	public List<Event> getEventsForDateRange(@RequestBody BulletinDataRequest request) {
		return eventService.getEventsForDateRange(request.getClubName(), request.getStartDate(), request.getEndDate());
	}
	
	@GetMapping(path = "/getEventsForSocialMediaShare", produces = "application/json")
	public List<Event> getEventsForSocialMediaShare() {
		return eventService.getEventsForSocialMediaShare();
	}

	@PostMapping(path = "/getWorkflowEventsOfStatus", consumes = "application/json", produces = "application/json")
	public List<EventWorkflow> getWorkflowEventsOfStatus(@RequestBody BulletinDataRequest request) {
		return eventService.getWorkflowEventsOfStatus(request.getClubName(), request.getStatus());
	}

	@PostMapping(path = "/addNewEvent", consumes = "application/json", produces = "application/json")
	public Boolean addNewEvent(@RequestBody EventWorkflow request) {
		return eventService.addNewEvent(request);
	}
	
	@PostMapping(path = "/updateEventWorkflow", consumes = "application/json", produces = "application/json")
	public Boolean updateEventWorkflow(@RequestBody EventWorkflow request) {
		return eventService.updateEventWorkflow(request);
	}
	
	@PostMapping(path = "/approveEvent", consumes = "application/json", produces = "application/json")
	public Boolean approveEvent(@RequestBody EventWorkflow request) {
		return eventService.approveEvent(request);
	}
	
	@PostMapping(path = "/rejectEvent", consumes = "application/json", produces = "application/json")
	public Boolean rejectEvent(@RequestBody EventWorkflow request) {
		return eventService.rejectEvent(request);
	}
}
