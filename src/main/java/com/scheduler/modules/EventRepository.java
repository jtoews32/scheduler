package com.scheduler.modules;

import java.util.List;
import java.lang.String;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.sql.Timestamp;

public interface EventRepository extends CrudRepository<EventDAO, String> {
    List<EventDAO> findAll();
    EventDAO findById(Long id);

	@Query("select count(*) from EventDAO e where (e.start >= :start and e.start < :end) or (:start >= e.start and :start < e.end)")
   	BigDecimal findOverlaps(@Param("start") Timestamp start, @Param("end") Timestamp end);


}

 