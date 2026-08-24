package com.karthik.newsportal.core.listeners;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.replication.ReplicationAction;

@Component(service = EventHandler.class,
          property = {EventConstants.EVENT_TOPIC+"="+ReplicationAction.EVENT_TOPIC,
          EventConstants.EVENT_FILTER+"(&(type=ACTIVATE)(path=/content/newsportal/us/en/anirudh/*))"
}
		)

public class ArticleEventHandler implements EventHandler{

	private static final Logger LOG = LoggerFactory.getLogger(ArticleEventHandler.class);
	@Override
	public void handleEvent(Event event) {
		LOG.info("Inside the ArticleEventHandlerKarthik........");
		String[] Properties = event.getPropertyNames();
		for(String Property : Properties) {
			LOG.info("PropertyName -{} , PropertyValue - {}", Property,event.getProperty(Property));
		}
		
	}

}