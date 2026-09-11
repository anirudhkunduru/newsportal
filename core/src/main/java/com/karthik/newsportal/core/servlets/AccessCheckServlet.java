package com.karthik.newsportal.core.servlets;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.jackrabbit.api.security.user.Group;

@Component(
    service = Servlet.class,
    property = {
        "sling.servlet.paths=/bin/accesscheck",
        "sling.servlet.methods=GET"
    }
)
public class AccessCheckServlet extends SlingAllMethodsServlet {

    private static final String GROUP_NAME = "Access";

    @Override
    protected void doGet(SlingHttpServletRequest request,
                         SlingHttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        // Step 1: Get user id from session (via resource resolver)
        ResourceResolver resolver = request.getResourceResolver();
        String userId = resolver.getUserID();

        if (userId == null || "anonymous".equals(userId)) {
            response.getWriter().println("No user logged in. Please authenticate.");
            return;
        }

        try {
            // Step 2: Get UserManager
            UserManager userManager = resolver.adaptTo(UserManager.class);
            Authorizable user = userManager.getAuthorizable(userId);
            Authorizable group = userManager.getAuthorizable(GROUP_NAME);

            // Step 3: Check membership
            boolean hasAccess = false;
            if (user != null && group instanceof Group) {
                hasAccess = ((Group) group).isMember(user);
            }

            // Step 4: Send response
            if (hasAccess) {
                response.getWriter().println(
                    "User " + userId + " logged in has access.");
            } else {
                response.getWriter().println(
                    "User " + userId + " logged in doesn't have access.");
            }

        } catch (Exception e) {
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
