package com.karthik.newsportal.core.services;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(
        name = "Article API Configuration",
        description = "Configuration for Article REST API"
)
public @interface ArticleApiConfiguration {

    @AttributeDefinition(
            name = "Article API URL",
            description = "REST endpoint used to fetch articles"
    )
    String apiUrl() default "https://gorest.co.in/public/v2/posts";

}