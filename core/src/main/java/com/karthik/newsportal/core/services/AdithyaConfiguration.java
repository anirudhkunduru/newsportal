package com.karthik.newsportal.core.services;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition
public @interface AdithyaConfiguration {
	
	@AttributeDefinition(name = "Adithya URL Path", description ="This is a sample URL path" )
	public String restAPIurl() default "https://gorest.co.in/public/v2/posts";

}