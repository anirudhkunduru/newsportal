package com.karthik.newsportal.core.models;

import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
    adaptables = {Resource.class, SlingHttpServletRequest.class},
    resourceType = "newsportal/components/Header",
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(name = "jackson", extensions = "json")
public class HeaderModel {

    @ValueMapValue
    private String pathField;

    @ValueMapValue
    private String textField;

    @ValueMapValue
    private String checkbox;

    @ChildResource(name = "multiField")
    private List<HeaderModel1> multiField;

    public String getPathField() {
        return pathField;
    }

    public String getTextField() {
        return textField;
    }

    public String getCheckbox() {
        return checkbox;
    }

    public List<HeaderModel1> getMultiField() {
        return multiField;
    }
}