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
    resourceType = "newsportal/components/team",
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(extensions = "json", name = "jackson")
public class TeamModel {

    @ValueMapValue
    private String teamTitle;

    @ValueMapValue
    private String teamDesc;

    @ChildResource
    private List<TeamMemberModel> teamMembers;

    public String getTeamTitle() { return teamTitle; }
    public String getTeamDesc() { return teamDesc; }
    public List<TeamMemberModel> getTeamMembers() { return teamMembers; }

    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public static class TeamMemberModel {
        @ValueMapValue private String name;
        @ValueMapValue private String designation;
        @ValueMapValue private String photo;

        public String getName() { return name; }
        public String getDesignation() { return designation; }
        public String getPhoto() { return photo; }
    }
}