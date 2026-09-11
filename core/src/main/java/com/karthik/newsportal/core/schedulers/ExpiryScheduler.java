package com.karthik.newsportal.core.schedulers;

import com.day.cq.replication.Replicator;
import com.day.cq.replication.ReplicationOptions;
import com.day.cq.replication.ReplicationActionType;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component(service = Runnable.class, immediate = true)
@Designate(ocd = ExpiryScheduler.Config.class)
public class ExpiryScheduler implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(ExpiryScheduler.class);

    @ObjectClassDefinition(name = "Expiry Scheduler Configuration")
    @interface Config {
        @AttributeDefinition(name = "Cron Expression", description = "e.g. 0 0/3 * * * ? for every 3 minutes")
        String cronExpression() default "0 0/3 * * * ?";

        @AttributeDefinition(name = "Search Path", description = "Base path to scan for pages")
        String searchPath() default "/content/newsportal";

        @AttributeDefinition(name = "Property Name", description = "Page property used for expiry comparison")
        String propertyName() default "articleExpiry";
    }

    @Reference private Scheduler scheduler;
    @Reference private ResourceResolverFactory resolverFactory;
    @Reference private Replicator replicator;

    private String cronExpression;
    private String searchPath;
    private String propertyName;

    @Activate @Modified
    protected void activate(Config config) {
        scheduler.unschedule("expiry-scheduler");
        cronExpression = config.cronExpression();
        searchPath = config.searchPath();
        propertyName = config.propertyName();
        ScheduleOptions options = scheduler.EXPR(cronExpression);
        options.name("expiry-scheduler");
        options.canRunConcurrently(false);
        scheduler.schedule(this, options);
        LOG.info("Expiry Scheduler activated with cron: {}", cronExpression);
    }

    @Deactivate
    protected void deactivate() {
        scheduler.unschedule("expiry-scheduler");
    }

    @Override
    public void run() {
        Date runTime = new Date();
        LOG.info("Expiry Scheduler run started at {}", runTime);

        Map<String, Object> params = Collections.singletonMap(ResourceResolverFactory.SUBSERVICE, "anirudhsubservice");
        try (ResourceResolver resolver = resolverFactory.getServiceResourceResolver(params)) {

            Date now = new Date();

            String query = "SELECT * FROM [cq:PageContent] AS node "
                         + "WHERE ISDESCENDANTNODE([" + searchPath + "]) "
                         + "AND node.[" + propertyName + "] IS NOT NULL";

            Iterator<Resource> results = resolver.findResources(query, javax.jcr.query.Query.JCR_SQL2);
            Session session = resolver.adaptTo(Session.class);
            if (session == null) {
                LOG.error("Could not adapt the service resolver to a JCR session");
                return;
            }

            while (results.hasNext()) {
                Resource pageContent = results.next();
                try {
                    LOG.info("Checking page {} at {} for property {}", pageContent.getPath(), new Date(), propertyName);
                    Date expiryDate = pageContent.getValueMap().get(propertyName, Date.class);
                    if (expiryDate == null) {
                        String expiryText = pageContent.getValueMap().get(propertyName, String.class);
                        if (expiryText != null && !expiryText.trim().isEmpty()) {
                            try {
                                expiryDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(expiryText);
                            } catch (ParseException ignored) {
                                try {
                                    expiryDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(expiryText);
                                } catch (ParseException e2) {
                                    LOG.warn("Skipping {} because {} value '{}' is not a valid date", pageContent.getPath(), propertyName, expiryText);
                                    continue;
                                }
                            }
                        }
                    }

                    if (expiryDate == null || pageContent.getParent() == null) {
                        LOG.warn("Skipping {} because {} is not a date", pageContent.getPath(), propertyName);
                        continue;
                    }

                    String pagePath = pageContent.getParent().getPath();
                    ReplicationActionType actionType = expiryDate.before(now)
                            ? ReplicationActionType.DEACTIVATE
                            : ReplicationActionType.ACTIVATE;
                    replicator.replicate(session, actionType, pagePath, new ReplicationOptions());
                    LOG.info("{} page ({}={}): {}", actionType == ReplicationActionType.ACTIVATE
                            ? "Published" : "Unpublished", propertyName, expiryDate, pagePath);
                } catch (Exception e) {
                    LOG.error("Could not process expiry property on {}", pageContent.getPath(), e);
                }
            }
        } catch (Exception e) {
            LOG.error("Expiry Scheduler failed", e);
        }
    }
}
