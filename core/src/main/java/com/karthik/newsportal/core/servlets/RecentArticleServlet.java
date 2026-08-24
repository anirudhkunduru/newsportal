package com.karthik.newsportal.core.servlets;

import java.io.IOException;
import java.util.*;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;

@Component(service = Servlet.class, immediate = true)
@SlingServletPaths(value = { "/bin/newsportal/recent/articleServlet" })
public class RecentArticleServlet extends SlingAllMethodsServlet {

    private static final String USERS_ROOT = "/content/users";
    private static final List<String> FIELDS = Arrays.asList("firstName", "lastName", "email", "phone");

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        Resource userResource = request.getResourceResolver().getResource(USERS_ROOT);
        if (userResource == null) {
            response.getWriter().write("No Users Found.");
            return;
        }

        JsonArrayBuilder userJsonList = Json.createArrayBuilder();
        for (Resource child : userResource.getChildren()) {
            ValueMap props = child.getValueMap();
            JsonObjectBuilder userJson = Json.createObjectBuilder();
            FIELDS.forEach(field -> userJson.add(field, Objects.toString(props.get(field, String.class), "")));
            userJsonList.add(userJson);
        }
        response.getWriter().write(userJsonList.build().toString());
    }

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        ResourceResolver resolver = request.getResourceResolver();
        Resource userResource = resolver.getResource(USERS_ROOT);

        if (userResource == null || validateParams(request, response, true)) return;

        String userID = request.getParameter("userID");
        if (resolver.getResource(USERS_ROOT + "/" + userID) != null) {
            response.getWriter().write("User ID already exists.");
            return;
        }

        Map<String, Object> properties = new HashMap<>();
        FIELDS.forEach(field -> properties.put(field, request.getParameter(field)));
        
        resolver.create(userResource, userID, properties);
        resolver.commit();
        response.getWriter().write("User ID successfully Created " + userID);
    }

    @Override
    protected void doPut(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String userID = request.getParameter("userID");
        if (isEmpty(userID) || validateParams(request, response, false)) {
            sendError(response, SlingHttpServletResponse.SC_BAD_REQUEST, "User ID is required or request is invalid.");
            return;
        }

        ResourceResolver resolver = request.getResourceResolver();
        Resource userResource = resolver.getResource(USERS_ROOT + "/" + userID);
        if (userResource == null) {
            sendError(response, SlingHttpServletResponse.SC_NOT_FOUND, "User ID not found.");
            return;
        }

        ModifiableValueMap mProp = userResource.adaptTo(ModifiableValueMap.class);
        if (mProp == null) {
            sendError(response, SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to update user.");
            return;
        }

        boolean updated = false;
        for (String field : FIELDS) {
            String val = request.getParameter(field);
            if (!isEmpty(val)) {
                mProp.put(field, val);
                updated = true;
            }
        }

        if (!updated) {
            sendError(response, SlingHttpServletResponse.SC_BAD_REQUEST, "Please provide at least one field to update.");
            return;
        }

        resolver.commit();
        response.getWriter().write("User ID successfully Updated " + userID);
    }

    @Override
    protected void doDelete(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String userID = request.getParameter("userID");
        if (isEmpty(userID) && request.getQueryString() != null && request.getQueryString().startsWith("userID=")) {
            userID = request.getQueryString().substring("userID=".length());
        }

        if (isEmpty(userID)) {
            response.getWriter().write("User ID is required.");
            return;
        }

        ResourceResolver resolver = request.getResourceResolver();
        Resource userResource = resolver.getResource(USERS_ROOT + "/" + userID);
        if (userResource == null) {
            response.getWriter().write("User ID not found.");
            return;
        }

        resolver.delete(userResource);
        resolver.commit();
        response.getWriter().write("User ID successfully Deleted " + userID);
    }

    // --- Helper Methods to Minimize Redundancy ---

    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    private void sendError(SlingHttpServletResponse response, int status, String msg) throws IOException {
        response.setStatus(status);
        response.getWriter().write(msg);
    }

    private boolean validateParams(SlingHttpServletRequest request, SlingHttpServletResponse response, boolean checkAllMandatory) throws IOException {
        Set<String> validParams = new HashSet<>(Arrays.asList("userID", "firstName", "lastName", "email", "phone"));
        List<String> invalidParams = new ArrayList<>();
        
        Collections.list(request.getParameterNames()).forEach(param -> {
            if (!validParams.contains(param)) invalidParams.add(param);
        });

        if (!invalidParams.isEmpty()) {
            sendError(response, SlingHttpServletResponse.SC_BAD_REQUEST, "Invalid parameter(s): " + String.join(", ", invalidParams));
            return true;
        }

        if (checkAllMandatory) {
            for (String param : validParams) {
                if (isEmpty(request.getParameter(param))) {
                    sendError(response, SlingHttpServletResponse.SC_BAD_REQUEST, "All parameters are mandatory: " + validParams);
                    return true;
                }
            }
        }
        return false;
    }
}