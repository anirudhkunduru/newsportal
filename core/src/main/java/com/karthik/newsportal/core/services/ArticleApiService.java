package com.karthik.newsportal.core.services;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

import org.osgi.service.metatype.annotations.Designate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = ArticleApiService.class, immediate = true)
@Designate(ocd = ArticleApiConfiguration.class)
public class ArticleApiService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ArticleApiService.class);

    private String apiUrl;

    @Activate
    @Modified
    protected void activate(ArticleApiConfiguration configuration) {

        apiUrl = configuration.apiUrl();

        LOGGER.info("=======================================");
        LOGGER.info("ArticleApiService Configuration Loaded");
        LOGGER.info("API URL : {}", apiUrl);
        LOGGER.info("=======================================");
    }

    public String fetchArticles() {

        HttpGet request = new HttpGet(apiUrl);

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(request)) {

            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {

                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    return EntityUtils.toString(entity);
                }

            } else {

                LOGGER.error("HTTP Request Failed. Status Code : {}", statusCode);
            }

        } catch (IOException exception) {

            LOGGER.error("Error while calling REST API", exception);

        }

        return "";
    }

}