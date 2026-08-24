package com.karthik.newsportal.core.servlets;

import java.io.IOException;
import java.util.Random;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;

@Component(service = Servlet.class)
@SlingServletPaths("/bin/random")
public class RandomServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;

    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private final Random random = new Random();

    @Override
    protected void doGet(
            SlingHttpServletRequest request,
            SlingHttpServletResponse response)
            throws ServletException, IOException {

        String type = request.getParameter("type");

        if ("Number".equalsIgnoreCase(type)) {

            StringBuilder result = new StringBuilder();

            for (int i = 0; i < 6; i++) {
                result.append(random.nextInt(10));
            }

            response.getWriter().write(result.toString());

        } else if ("Letters".equalsIgnoreCase(type)) {

            StringBuilder result = new StringBuilder();

            for (int i = 0; i < 6; i++) {
                result.append(LETTERS.charAt(random.nextInt(LETTERS.length())));
            }

            response.getWriter().write(result.toString());

        } else if ("Random".equalsIgnoreCase(type)) {

            StringBuilder numbers = new StringBuilder();
            StringBuilder letters = new StringBuilder();

            // Generate 3 random numbers
            for (int i = 0; i < 3; i++) {
                numbers.append(random.nextInt(10));
            }

            // Generate 3 random letters
            for (int i = 0; i < 3; i++) {
                letters.append(
                    LETTERS.charAt(random.nextInt(LETTERS.length()))
                );
            }

            response.getWriter().write(
                numbers.toString() + letters.toString()
            );

        } else {

            response.getWriter().write(
                "Please provide type=Number, type=Letters or type=Random"
            );
        }
    }
}