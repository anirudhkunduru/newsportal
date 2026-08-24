package com.karthik.newsportal.core.models;

import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class CarouselModel1 {

    @ValueMapValue
    private String textField;

    @ValueMapValue
    private String pathField;

    @ChildResource(name = "multiField")
    private List<CarouselModel2> multiField;

    public String getTextField() {
        return textField;
    }

    public String getPathField() {
        return pathField;
    }

    public List<CarouselModel2> getMultiField() {
        return multiField;
    }
}