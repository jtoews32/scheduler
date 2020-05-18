package com.scheduler.modules;

import java.util.*;
import java.lang.*;
import java.sql.Timestamp;
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

    @PostMapping("/events")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public EventDAO newEvent(@RequestBody EventDAO newEventDAO) {
        EventDAO eventDAO = eventService.setEndTime(newEventDAO);
        if ( !eventService.hasTimeOverlap(eventDAO) && eventService.isEndTimeValid(eventDAO.getEnd()) && eventService.isStartTimeValid(eventDAO.getStart())) {
            return eventService.create(eventDAO);
        } 
        return null;
    }
 
    @DeleteMapping("/events/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable String id) {
        eventService.remove(id);
        return ResponseEntity.noContent().build();
    }
}

