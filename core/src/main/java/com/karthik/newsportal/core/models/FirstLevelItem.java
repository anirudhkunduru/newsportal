package com.karthik.newsportal.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.Date;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FirstLevelItem {

    @ValueMapValue
    private Date dob;

    // Injects the second nested level using the name property: ./multifield2
    @ChildResource(name = "multifield2")
    private List<SecondLevelItem> secondLevelItems;

    public Date getDob() {
        return dob;
    }

    public List<SecondLevelItem> getSecondLevelItems() {
        return secondLevelItems;
    }
}