package com.karthik.newsportal.core.models;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import lombok.Getter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Getter
@Model(
    adaptables = Resource.class, // Simplified to Resource if request context isn't strictly needed
    resourceType = "newsportal/components/VenkatRamana",
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(extensions = "json", name = "jackson")
public class VenkatRamanaModel {

    @ValueMapValue
    private String text;

    @ChildResource(name = "multifield")
    private List<FirstLevelItem> firstLevelItems;

    @Getter
    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public static class FirstLevelItem {

        @ValueMapValue
        private Calendar dob;

        @ChildResource(name = "multifield2")
        private List<SecondLevelItem> secondLevelItems;

        public String getFormattedDob() {
            return (dob != null) ? new SimpleDateFormat("yyyy-MM-dd").format(dob.getTime()) : "";
        }
    }

    @Getter
    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public static class SecondLevelItem {

        @ValueMapValue
        private String textarea;
    }
}