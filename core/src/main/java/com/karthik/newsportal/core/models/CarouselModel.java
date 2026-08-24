package com.karthik.newsportal.core.models;

import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

@Model(
        adaptables = {
                Resource.class,
                SlingHttpServletRequest.class
        },
        resourceType = "newsportal/components/Carousel",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(name = "jackson", extensions = "json")
public class CarouselModel {

    @ChildResource(name = "nestedmultiField")
    private List<CarouselModel1> nestedmultiField;

    public List<CarouselModel1> getNestedmultiField() {
        return nestedmultiField;
    }
}