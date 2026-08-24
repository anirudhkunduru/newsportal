package com.karthik.newsportal.core.schedulers;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.karthik.newsportal.core.servicesImp.KarthikRes;
import java.util.Date;
import java.util.Iterator;
import javax.jcr.Session;

@Component(immediate = true, service = Runnable.class, property = { "scheduler.expression=* */59 * ? * *" })
public class Test implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(Test.class);

    @Reference
    KarthikRes karthik;

    @Reference
    Replicator replicator;

    @Override
    public void run() {
        log.info("I am Inside the Sling schedulers...--Malyadri");

        try (ResourceResolver resolver = karthik.getResourceResolver()) {

            PageManager pagemanager = resolver.adaptTo(PageManager.class);
            Page articlepage = pagemanager.getPage("/content/newsportal/us/en/anirudh");
            Iterator<Page> childpages = articlepage.listChildren();
            Date today = new Date();

            while (childpages.hasNext()) {
                Page page = childpages.next();
                Resource contentResource = page.getContentResource();
                ValueMap properties = contentResource.getValueMap();
                Date articleExpiry = properties.get("articleExpiry", Date.class);
                Session session = resolver.adaptTo(Session.class);

                if (articleExpiry != null && articleExpiry.compareTo(today) < 0) {
                    // articleExpiry is in the past → Deactivate
                    replicator.replicate(session, ReplicationActionType.DEACTIVATE, page.getPath());
                    log.info("Deactivated expired page: {}", page.getPath());

                } else if (articleExpiry != null && articleExpiry.compareTo(today) >= 0) {
                    // articleExpiry is today or in the future → Activate
                    replicator.replicate(session, ReplicationActionType.ACTIVATE, page.getPath());
                    log.info("Activated page (expiry is future/today): {}", page.getPath());
                }
            }

        } catch (ReplicationException e) {
            e.printStackTrace();
        }
    }
}