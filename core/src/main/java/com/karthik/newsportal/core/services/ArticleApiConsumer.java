package com.karthik.newsportal.core.services;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true)
public class ArticleApiConsumer {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ArticleApiConsumer.class);

    @Reference
    private ArticleApiService articleApiService;

    @Activate
    protected void activate() {

        LOGGER.info("=======================================");
        LOGGER.info("ArticleApiConsumer Activated");
        LOGGER.info("=======================================");

        String response = articleApiService.fetchArticles();

        LOGGER.info("REST API Response:");
        LOGGER.info(response);
    }

    @Modified
    protected void modified() {

        LOGGER.info("ArticleApiConsumer Configuration Modified");

    }

    @Deactivate
    protected void deactivate() {

        LOGGER.info("ArticleApiConsumer Deactivated");

    }

}