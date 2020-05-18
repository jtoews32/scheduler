package com.scheduler.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.scheduler.modules.*;

@Configuration
public class BeanConfig {
    private static final Logger log = LoggerFactory.getLogger(BeanConfig.class);
    @Autowired EventRepository eventRepository;

    @Bean public EventService eventService() { 
        return new EventServiceImpl(eventRepository);
    }
}