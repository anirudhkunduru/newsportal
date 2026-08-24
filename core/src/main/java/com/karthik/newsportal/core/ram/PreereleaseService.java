package com.karthik.newsportal.core.ram;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true)


public class PreereleaseService {
	
	private static final Logger Log = LoggerFactory.getLogger(PreereleaseService.class);
	
	@Reference
	SriService service;
	
	@Activate
	public void activate(){
		String ar = service.getarticle();
		Log.info("PreereleaseService -- Inside the Activated Method");
		Log.info("Response --{}",ar);
		
	}
	
	@Deactivate
	public void deactivate(){
		Log.info("PreereleaseService -- Inside the DeActivated Method");
		
	}
	
	@Modified
	public void update(){
		Log.info("PreereleaseService -- Inside the update Method");
		
	}

}