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
public class SidebarModel {

    // =========================
    // Header
    // =========================

    @ValueMapValue(name = "LogoPath")
    private String logoPath;

    @ValueMapValue(name = "LogoMobileImage")
    private String logoMobileImage;

    @ValueMapValue(name = "LogoLink")
    private String logoLink;

    /*
     * Checkbox
     * Dialog name  = ./EnableSwitch
     * Dialog value = Enable Switch
     */
    @ValueMapValue(name = "EnableSwitch")
    private boolean enableSwitch;


    // =========================
    // Header Links Multifield
    // name = ./multifield
    // =========================

    @ChildResource(name = "multifield")
    private List<HeaderLinkModel> headerLinks;


    // =========================
    // Sidebar Navigation Multifield
    // name = ./multifield2
    // =========================

    @ChildResource(name = "multifield2")
    private List<SidebarNavigationModel> sidebarNavigation;


    // =========================
    // Region
    // name = ./Country
    // =========================

    @ValueMapValue(name = "Country")
    private String country;


    // =========================
    // Getters
    // =========================

    public String getLogoPath() {
        return logoPath;
    }

    public String getLogoMobileImage() {
        return logoMobileImage;
    }

    public String getLogoLink() {
        return logoLink;
    }

    public boolean getEnableSwitch() {
    return enableSwitch;
}

    public List<HeaderLinkModel> getHeaderLinks() {
        return headerLinks;
    }

    public List<SidebarNavigationModel> getSidebarNavigation() {
        return sidebarNavigation;
    }

    public String getCountry() {
        return country;
    }
}