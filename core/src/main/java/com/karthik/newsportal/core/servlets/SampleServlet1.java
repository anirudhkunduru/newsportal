package com.karthik.newsportal.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;

@Component(
        service = Servlet.class,
        immediate = true
)
@SlingServletPaths("/bin/newsportal/service/sample")
public class SampleServlet1 extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(final SlingHttpServletRequest request,
                         final SlingHttpServletResponse response)
            throws ServletException, IOException {

        writeResponse(response, "Response from SampleServlet -- GET");
    }

    @Override
    protected void doPost(final SlingHttpServletRequest request,
                          final SlingHttpServletResponse response)
            throws ServletException, IOException {

        writeResponse(response, "Response from SampleServlet -- POST");
    }

    @Override
    protected void doPut(final SlingHttpServletRequest request,
                         final SlingHttpServletResponse response)
            throws ServletException, IOException {

        writeResponse(response, "Response from SampleServlet -- PUT");
    }

    @Override
    protected void doDelete(final SlingHttpServletRequest request,
                            final SlingHttpServletResponse response)
            throws ServletException, IOException {

        writeResponse(response, "Response from SampleServlet -- DELETE");
    }

    private void writeResponse(final SlingHttpServletResponse response,
                               final String message)
            throws IOException {

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(message);
    }
}