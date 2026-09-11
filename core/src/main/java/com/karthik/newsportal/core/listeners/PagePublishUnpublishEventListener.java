package com.karthik.newsportal.core.listeners;

import com.day.cq.replication.ReplicationAction;
import com.day.cq.replication.ReplicationActionType;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
        service = EventHandler.class,
        property = EventConstants.EVENT_TOPIC + "=" + ReplicationAction.EVENT_TOPIC
)
public class PagePublishUnpublishEventListener implements EventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(PagePublishUnpublishEventListener.class);

    @Override
    public void handleEvent(Event event) {
        ReplicationAction action = ReplicationAction.fromEvent(event);
        if (action == null) {
            return;
        }

        if (ReplicationActionType.ACTIVATE.equals(action.getType())) {
            LOG.info("Published page: {}", action.getPath());
        } else if (ReplicationActionType.DEACTIVATE.equals(action.getType())) {
            LOG.info("Unpublished page: {}", action.getPath());
        }
    }
}