package com.karthik.newsportal.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
    adaptables = Resource.class,
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class HeaderLinkModel {

    @ValueMapValue(name = "textfield")
    private String textfield;

    @ValueMapValue(name = "pathfield")
    private String pathfield;


    public String getTextfield() {
        return textfield;
    }

    public String getPathfield() {
        return pathfield;
    }
}