package com.scheduler.modules;

import java.util.*;
import java.lang.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

public interface EventService {
    List<EventDAO> requestAll();
    EventDAO create(EventDAO eventDAO);
    void remove(String id);
    EventDAO setEndTime(EventDAO eventDAO);
    boolean hasTimeOverlap(EventDAO eventDAO);
    boolean isEndTimeValid(Timestamp end );
    boolean isStartTimeValid(Timestamp start);
}
