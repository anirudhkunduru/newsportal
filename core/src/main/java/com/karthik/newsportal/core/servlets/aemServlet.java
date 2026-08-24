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
    resourceTypes = "newsportal/anirudhsreyesh/aemservlet",
    methods = {"GET", "POST", "PUT", "DELETE"}
)
public class aemServlet extends SlingAllMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request,
            SlingHttpServletResponse response)
            throws ServletException, IOException {

        response.getWriter().write("Response from Resource aemServlet -- GET");
    }

    @Override
    protected void doPost(SlingHttpServletRequest request,
            SlingHttpServletResponse response)
            throws ServletException, IOException {

        response.getWriter().write("Response from Resource aemServlet -- POST");
    }

    @Override
    protected void doPut(SlingHttpServletRequest request,
            SlingHttpServletResponse response)
            throws ServletException, IOException {

        response.getWriter().write("Response from Resource aemServlet -- PUT");
    }

    @Override
    protected void doDelete(SlingHttpServletRequest request,
            SlingHttpServletResponse response)
            throws ServletException, IOException {

        response.getWriter().write("Response from Resource aemServlet -- DELETE");
    }
}