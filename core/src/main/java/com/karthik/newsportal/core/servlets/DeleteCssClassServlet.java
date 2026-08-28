package com.karthik.newsportal.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.ServletResolverConstants;

import org.osgi.service.component.annotations.Component;

@Component(
    service = Servlet.class,
    property = {
        ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/deleteCssClass",
        ServletResolverConstants.SLING_SERVLET_METHODS + "=POST"
    }
)
public class DeleteCssClassServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(
            SlingHttpServletRequest request,
            SlingHttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String componentPath = request.getParameter("path");


        /*
         * Check whether path was received
         */
        if (componentPath == null || componentPath.isEmpty()) {

            response.setStatus(
                SlingHttpServletResponse.SC_BAD_REQUEST
            );

            response.getWriter().write(
                "{\"status\":\"error\",\"message\":\"Component path is missing\"}"
            );

            return;
        }


        ResourceResolver resourceResolver =
                request.getResourceResolver();


        /*
         * Get component resource
         */
        Resource componentResource =
                resourceResolver.getResource(componentPath);


        if (componentResource == null) {

            response.setStatus(
                SlingHttpServletResponse.SC_NOT_FOUND
            );

            response.getWriter().write(
                "{\"status\":\"error\",\"message\":\"Component resource not found\"}"
            );

            return;
        }


        /*
         * Get component properties
         */
        ModifiableValueMap properties =
                componentResource.adaptTo(ModifiableValueMap.class);


        if (properties == null) {

            response.setStatus(
                SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            response.getWriter().write(
                "{\"status\":\"error\",\"message\":\"Unable to modify component properties\"}"
            );

            return;
        }


        /*
         * Delete cssClass property
         */
        if (properties.containsKey("cssClass")) {

            properties.remove("cssClass");

            resourceResolver.commit();


            response.getWriter().write(
                "{\"status\":\"success\",\"message\":\"cssClass deleted successfully\"}"
            );

            return;
        }


        /*
         * Property doesn't exist
         */
        response.getWriter().write(
            "{\"status\":\"success\",\"message\":\"cssClass property does not exist\"}"
        );

    }

}