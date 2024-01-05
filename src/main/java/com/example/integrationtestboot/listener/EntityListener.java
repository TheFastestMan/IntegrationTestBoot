package com.example.integrationtestboot.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EntityListener {
    private static final Logger logger = LoggerFactory.getLogger(EntityListener.class);

    @EventListener(condition = "#p0.accessType.name() == 'CREATE'")
    public void createEntity(EventEntity event) {
        logger.info("Create Event Triggered: " + event);

    }

    @EventListener(condition = "#p0.accessType.name() == 'READ'")
    public void readEntity(EventEntity event) {
        logger.info("Read Event Triggered: " + event);

    }

    @EventListener(condition = "#p0.accessType.name() == 'UPDATE'")
    public void updateEntity(EventEntity event) {
        logger.info("Update Event Triggered: " + event);

    }

    @EventListener(condition = "#p0.accessType.name() == 'DELETE'")
    public void deleteEntity(EventEntity event) {
        logger.info("Delete Event Triggered: " + event);

    }

}