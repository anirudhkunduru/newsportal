package com.karthik.newsportal.core.listeners;

import com.day.cq.replication.ReplicationAction;
import com.day.cq.replication.ReplicationActionType;
import com.karthik.newsportal.core.servicesImp.KarthikRes;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.Session;

@Component(
        service = EventHandler.class,
        immediate = true,
        property = {
                EventConstants.EVENT_TOPIC + "=" + ReplicationAction.EVENT_TOPIC
        }
)
public class PagePublishEventHandler implements EventHandler {

    private static final Logger log = LoggerFactory.getLogger(PagePublishEventHandler.class);

    private static final String PROPERTY_NAME = "changed";

    @Reference
    private KarthikRes karthik;

    @Override
    public void handleEvent(Event event) {

        ReplicationAction action = ReplicationAction.fromEvent(event);

        if (action == null) {
            return;
        }

        if (ReplicationActionType.ACTIVATE.equals(action.getType())) {

            String pagePath = action.getPath();

            log.info("========== Page Publish Event Triggered ==========");
            log.info("Published Page Path : {}", pagePath);

            addChangedProperty(pagePath);
        }
    }

    private void addChangedProperty(String pagePath) {

        try (ResourceResolver resolver = karthik.getResourceResolver()) {

            Session session = resolver.adaptTo(Session.class);
            String contentPath = pagePath + "/jcr:content";

            if (session.nodeExists(contentPath)) {
                Node contentNode = session.getNode(contentPath);
                contentNode.setProperty(PROPERTY_NAME, true);
                session.save();
                log.info("Property '{}' = true set at: {}", PROPERTY_NAME, contentPath);
            } else {
                log.warn("jcr:content node not found at: {}", contentPath);
            }

        } catch (Exception e) {
            log.error("Error while setting 'changed' property", e);
        }
    }
}
