package council.website.event.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

import council.website.action.beans.ActionResponse;
import council.website.bulletin.beans.BulletinDataRequest;
import council.website.event.beans.Event;
import council.website.event.beans.EventWorkflow;
import council.website.event.service.EventWebService;

@RestController
@RequestMapping("/event")
@CrossOrigin
public class EventWebController {

	@Autowired
	private EventWebService eventService;

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

	@PostMapping(path = "/getPendingEventsForSubmitter", consumes = "application/json", produces = "application/json")
	public List<EventWorkflow> getPendingEventsForSubmitter(@RequestBody BulletinDataRequest request) {
		return eventService.getPendingEventsForSubmitter(request);
	}

	@PostMapping(path = "/getPendingEventsForApprover", consumes = "application/json", produces = "application/json")
	public List<EventWorkflow> getPendingEventsForApprover() {
		return eventService.getPendingEventsForApprover();
	}

	@PostMapping(path = "/addNewEvent", consumes = "application/json", produces = "application/json")
	public ActionResponse addNewEvent(@RequestBody EventWorkflow request) {
		return eventService.addNewEvent(request);
	}

	@PostMapping(path = "/addOrUpdatePosterForEvent", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ActionResponse addOrUpdatePosterForEvent(@RequestParam("file") MultipartFile file,
			@RequestParam("eventName") String eventName) throws IOException, AmazonServiceException, AmazonClientException, IllegalStateException, InterruptedException {
		return eventService.addOrUpdatePosterForEvent(file, eventName);
	}
	
	@RequestMapping(path = "/getPosterForEvent")
	public ResponseEntity<Resource> getPosterForEvent(@RequestParam String fileName) throws MalformedURLException, AmazonServiceException, AmazonClientException, InterruptedException {
		return eventService.getPosterForEvent(fileName);
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
