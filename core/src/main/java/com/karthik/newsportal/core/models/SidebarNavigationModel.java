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
public class SidebarNavigationModel {

    @ValueMapValue(name = "DesktopIcon")
    private String desktopIcon;

    @ValueMapValue(name = "MobileIcon")
    private String mobileIcon;


    // Nested composite multifield
    // name = ./NestedMultifield

    @ChildResource(name = "NestedMultifield")
    private List<NestedMultifieldModel> nestedMultifield;


    public String getDesktopIcon() {
        return desktopIcon;
    }

    public String getMobileIcon() {
        return mobileIcon;
    }

    public List<NestedMultifieldModel> getNestedMultifield() {
        return nestedMultifield;
    }
}