package com.karthik.newsportal.core.anirudh;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
    service = Ram.class,
    immediate = true
)
public class Ram {

    private static final Logger LOG = LoggerFactory.getLogger(Ram.class);

    @Activate
    protected void activate() {
        LOG.info("Ram component has been activated.");
    }

    @Modified
    protected void modified() {
        LOG.info("Ram component configuration has been modified.");
    }

    @Deactivate
    protected void deactivate() {
        LOG.info("Ram component has been deactivated.");
    }
}