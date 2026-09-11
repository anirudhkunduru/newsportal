package com.karthik.newsportal.core.servlets;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.day.cq.dam.api.Asset;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;

import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Component(service = Servlet.class)
@SlingServletResourceTypes(
        resourceTypes = "newsportal/datasources/profile-images",
        methods = HttpConstants.METHOD_GET
)
public class ProfileImageServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;

    private static final String IMAGE_FOLDER = "/content/dam/images";

    @Override
    protected void doGet(
            SlingHttpServletRequest request,
            SlingHttpServletResponse response) throws IOException {

        ResourceResolver resolver = request.getResourceResolver();

        Resource imageFolder = resolver.getResource(IMAGE_FOLDER);

        List<Resource> options = new ArrayList<>();

        if (imageFolder != null) {

            // Get all resources from the images folder
            List<Resource> resources = new ArrayList<>();

            Iterator<Resource> iterator = imageFolder.listChildren();

            while (iterator.hasNext()) {
                resources.add(iterator.next());
            }

            // Sort images by year in the filename
            resources.sort((resource1, resource2) -> {

                int year1 = extractYear(resource1.getName());
                int year2 = extractYear(resource2.getName());

                return Integer.compare(year1, year2);
            });

            // Create dropdown options
            for (Resource resource : resources) {

                Asset asset = resource.adaptTo(Asset.class);

                if (asset != null
                        && asset.getMimeType() != null
                        && asset.getMimeType().startsWith("image/")) {

                    ValueMap properties =
                            new ValueMapDecorator(
                                    new HashMap<String, Object>()
                            );

                    // Text displayed in dropdown
                    properties.put("text", asset.getName());

                    // DAM path stored in ./image
                    properties.put("value", asset.getPath());

                    options.add(
                            new ValueMapResource(
                                    resolver,
                                    new ResourceMetadata(),
                                    "nt:unstructured",
                                    properties
                            )
                    );
                }
            }
        }

        // Provide options to the dropdown
        request.setAttribute(
                DataSource.class.getName(),
                new SimpleDataSource(options.iterator())
        );
    }

    /**
     * Extracts the year from filenames such as:
     *
     * iPhone 2G (2007).png
     * iPhone 3G (2008).png
     * iPhone 4 (2010).png
     * iPhone 17 series (2025).png
     */
    private int extractYear(String name) {

        java.util.regex.Matcher matcher =
                java.util.regex.Pattern
                        .compile("\\((\\d{4})\\)")
                        .matcher(name);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }

        // If no year is found, put it at the end
        return Integer.MAX_VALUE;
    }
}