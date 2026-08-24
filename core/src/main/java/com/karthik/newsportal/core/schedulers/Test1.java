package com.karthik.newsportal.core.schedulers;

import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true, service = Runnable.class, property = { "scheduler.expression=* */59 * ? * *" })
public class Test1 implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(Test.class);

    @Override
    public void run() {
        log.info("I am Inside the Sling schedulers...--Anirudh1");
    }
}