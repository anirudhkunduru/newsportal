package com.karthik.newsportal.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Karthik - Configurable Page Publish Scheduler", description = "Publishes child pages under a configured path at a configured cron time")
public @interface SchedulerConfig {

    @AttributeDefinition(name = "Cron Expression", description = "Cron for scheduler. Example: 0 0/2 * * * ? = every 2 minutes")
    String scheduler_expression() default "0 0/2 * * * ?";

    @AttributeDefinition(name = "Page Path", description = "Parent page path whose children will be published")
    String page_path() default "/content/newsportal/us/en/anirudh";

    @AttributeDefinition(name = "Scheduler Enabled", description = "Enable/disable the scheduler")
    boolean scheduler_enabled() default true;
}
