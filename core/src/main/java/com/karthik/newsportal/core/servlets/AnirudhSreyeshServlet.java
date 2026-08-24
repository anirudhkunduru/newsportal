package com.karthik.newsportal.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;

@Component(service = Servlet.class)
@SlingServletResourceTypes(
    resourceTypes = "newsportal/anirudhsreyesh/recent",
    methods = {"GET", "POST", "PUT", "DELETE"}
)
public class AnirudhSreyeshServlet extends SlingAllMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request,
            SlingHttpServletResponse response)
            throws ServletException, IOException {

        response.getWriter().write("Response from Resource AnirudhSreyeshServlet -- GET");
    }

    @Override
    protected void doPost(SlingHttpServletRequest request,
            SlingHttpServletResponse response)
            throws ServletException, IOException {

        response.getWriter().write("Response from Resource AnirudhSreyeshServlet -- POST");
    }

    @Override
    protected void doPut(SlingHttpServletRequest request,
            SlingHttpServletResponse response)
            throws ServletException, IOException {

        response.getWriter().write("Response from Resource AnirudhSreyeshServlet -- PUT");
    }

    @Override
    protected void doDelete(SlingHttpServletRequest request,
            SlingHttpServletResponse response)
            throws ServletException, IOException {

        response.getWriter().write("Response from Resource AnirudhSreyeshServlet -- DELETE");
    }
}