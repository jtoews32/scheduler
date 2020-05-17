package com.scheduler.modules;

import java.util.*;
import java.lang.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.math.BigDecimal;

@RestController
public class EventController {
	@Autowired EventService eventService;

	@GetMapping("/events")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<EventDAO> getAllEvents() {
        return eventService.requestAll()  ;
  }

  // (hour(:end) >= 17 and (hour(:end) = 17 and minute(:end) = 0) ) or (hour(:start) < 8) or 

  @PostMapping("/events")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public EventDAO newEvent(@RequestBody EventDAO newEventDAO) {

    EventDAO eventDAO = eventService.setEndTime(newEventDAO);
	//	BigDecimal overlaps = eventService.findOverlaps(newEventDAO.getStart(), newEventDAO.getEnd() );
   // System.out.println( "Total Overlaps " + overlaps.toString() + " " + newEventDAO.getStart() + " " + newEventDAO.getEnd() );
  
 		if ( !eventService.hasTimeOverlap(eventDAO) && eventService.isEndTimeValid(eventDAO.getEnd() ) && eventService.isStartTimeValid(eventDAO.getStart()) ) {
  		return eventService.create(eventDAO);
  	} 

  	return null;
  }
 
/*
curl -X POST localhost:8080/events -H 'Content-type:application/json' -d '{"id": "1",  "resourceId": "2", "title": "Samwise Gamgee", "start": "2015-07-05T22:16:18Z",  "end": "2015-07-05T22:16:18Z"}' 

*/
	// curl -v -X DELETE localhost:8080/events/1
  @DeleteMapping("/events/{id}")
	public ResponseEntity<?> deleteEvent(@PathVariable String id) {
		eventService.remove(id);
  		return ResponseEntity.noContent().build();
	}
}

