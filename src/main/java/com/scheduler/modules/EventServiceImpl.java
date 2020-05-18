package com.scheduler.modules;

import java.util.*;
import java.lang.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.*;

public class EventServiceImpl implements EventService {
    private EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<EventDAO> requestAll() {
        return eventRepository.findAll();
    }

    public EventDAO create(EventDAO eventDAO) {
        return eventRepository.save(eventDAO);
    }

    public boolean remove(String id) {
        EventDAO eventDAO = eventRepository.findById( Long.parseLong(id) );
        eventRepository.delete(eventDAO); 
        return true;
    }

    public boolean hasTimeOverlap(EventDAO eventDAO)  {
        return eventRepository.findOverlaps(eventDAO.getStart(), eventDAO.getEnd()).compareTo(BigDecimal.ZERO) != 0;
    }

    public EventDAO setEndTime(EventDAO eventDAO) {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(eventDAO.getStart().getTime());
        if( eventDAO.getDuration() == 15) {
            calendar.add(Calendar.MINUTE, 15);
        } else {
            calendar.add(Calendar.MINUTE, 60);
        }
        eventDAO.setEnd(new Timestamp(calendar.getTime().getTime() )); 
        return eventDAO;
    }

    public boolean isEndTimeValid(Timestamp end) {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(end.getTime());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        if( hour >= 17 && !( hour == 17 && minute ==0 ) ) {
        return false;
        } 
        return true;
    }

    public boolean isStartTimeValid(Timestamp start) {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(start.getTime());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        if( hour  < 8) {
        return false;
        }
        return true;
    }
}