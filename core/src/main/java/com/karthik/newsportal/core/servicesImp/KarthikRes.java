package com.karthik.newsportal.core.servicesImp;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import java.util.*;

@Component(service = KarthikRes.class)
public class KarthikRes {
	
	@Reference
	ResourceResolverFactory factory;
	
	public ResourceResolver getResourceResolver() {
		ResourceResolver resolver = null;
		
		try {
			
			Map<String,Object> prop = new HashMap<>();
			prop.put(ResourceResolverFactory.SUBSERVICE, "anirudhsubservice");
			resolver = factory.getServiceResourceResolver(prop);
		}
		catch(org.apache.sling.api.resource.LoginException e) {
			e.printStackTrace();
			
		}
		
		
		return resolver;
	}

}