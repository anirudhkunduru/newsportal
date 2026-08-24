package com.karthik.newsportal.core.models;

import java.util.Date;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = {Resource.class, SlingHttpServletRequest.class},
        resourceType = "newsportal/components/Rajesh")
@Exporter(name = "jackson", extensions = "json")
public class RajeshModel {

    @ValueMapValue
    private String text;

    @ValueMapValue
    private String textarea;

    @ValueMapValue
    private Date dob;

    public String getText() {
        return text;
    }

    public String getTextarea() {
        return textarea;
    }

    public Date getDob() {
        return dob;
    }
}