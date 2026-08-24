package com.karthik.newsportal.core.ram;

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

@Component(service = SriService.class ,immediate = true)
@Designate(ocd = NagaConfiguration.class)
public class SriService {
	
	private static final Logger Log = LoggerFactory.getLogger(SriService.class);
	
	private String articleRestUrl;
	
//	private Boolean status;
	
	@Activate
	public void activate(NagaConfiguration config){
		Log.info("SriService -- Inside the Activated Method");
		this.articleRestUrl = config.restAPIurl();
		
	}
	
	@Modified
	public void update(NagaConfiguration config){
		Log.info("SriService -- Inside the update Method");
		this.articleRestUrl = config.restAPIurl();
		
	}
	
	public String getarticle() {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet Request = new HttpGet(articleRestUrl);
		String result = null;
		try {
			CloseableHttpResponse response = httpclient.execute(Request);
			HttpEntity entity = response.getEntity();
			if(entity!=null) {
				
				result = EntityUtils.toString(entity);
			}
		}
			catch(IOException e) {
				e.printStackTrace();
			}
		
		return result;
		
	}

}