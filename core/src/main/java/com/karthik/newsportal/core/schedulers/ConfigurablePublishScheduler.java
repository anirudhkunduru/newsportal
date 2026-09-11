package com.karthik.newsportal.core.schedulers;

import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.karthik.newsportal.core.config.SchedulerConfig;
import com.karthik.newsportal.core.servicesImp.KarthikRes;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import java.util.Iterator;

@Component(immediate = true, service = Runnable.class)
@Designate(ocd = SchedulerConfig.class)
public class ConfigurablePublishScheduler implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(ConfigurablePublishScheduler.class);

    @Reference
    KarthikRes karthik;

    @Reference
    Replicator replicator;

    private String cronExpression;
    private String pagePath;
    private boolean enabled;

    @Activate
    @Modified
    protected void activate(SchedulerConfig config) {
        this.cronExpression = config.scheduler_expression();
        this.pagePath = config.page_path();
        this.enabled = config.scheduler_enabled();
        log.info("Scheduler configured. cron={}, pagePath={}, enabled={}", cronExpression, pagePath, enabled);
    }

    @Override
    public void run() {

        if (!enabled) {
            return;
        }

        log.info("========== Configurable Publish Scheduler Triggered ==========");

        try (ResourceResolver resolver = karthik.getResourceResolver()) {

            PageManager pageManager = resolver.adaptTo(PageManager.class);
            if (pageManager == null) {
                log.error("PageManager is null");
                return;
            }

            Page parentPage = pageManager.getPage(pagePath);
            if (parentPage == null) {
                log.error("Parent page not found at configured path: {}", pagePath);
                return;
            }

            Session session = resolver.adaptTo(Session.class);
            Iterator<Page> children = parentPage.listChildren();

            while (children.hasNext()) {
                Page child = children.next();

                try {
                    replicator.replicate(session, ReplicationActionType.ACTIVATE, child.getPath());
                    log.info("Published page: {}", child.getPath());
                } catch (ReplicationException e) {
                    log.error("Failed to publish page: {}", child.getPath(), e);
                }
            }

            log.info("========== Scheduler Run Complete ==========");

        } catch (Exception e) {
            log.error("Scheduler failed", e);
        }
    }
}
