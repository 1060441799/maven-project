package com.newframe.web.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * Created by xm on 2016/4/25.
 */
@Component
public class MessagePublisher implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher messageEventPublisher;

    public void fireEvent(MessageEvent messageEvent) {
        this.messageEventPublisher.publishEvent(messageEvent);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.messageEventPublisher = applicationEventPublisher;
    }
}
