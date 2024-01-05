package com.example.integrationtestboot.listener;

import org.springframework.context.ApplicationEvent;

public class EventEntity extends ApplicationEvent {
    private final String message;
    private final AccessType accessType;
    public EventEntity(Object source, AccessType accessType, String message) {
        super(source);
        this.accessType = accessType;
        this.message = message;
    }

    public AccessType getAccessType() {
        return accessType;
    }
}
