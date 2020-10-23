package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Arrays;

public class StaticServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] requestTokens = request.getRequestURI().split("\\+");
        Arrays.asList(requestTokens).forEach(System.out::println);
        for (String requestToken : requestTokens) {
            processGetRequest(requestToken, response);
        }
        response.setContentType(getContentTypeFromName(requestTokens[0]));
    }

    private void processGetRequest(String uri, HttpServletResponse response) throws IOException {
        File file = new File("/Users/artemshashulovskiy/Developer/Web/wp3/src/main/webapp/static/" + uri);
        if (!file.exists()) {
            file = new File(getServletContext().getRealPath("/static/" + uri));
        }
        if (file.isFile()) {
            OutputStream outputStream = response.getOutputStream();
            Files.copy(file.toPath(), outputStream);
            outputStream.flush();
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private String getContentTypeFromName(String name) {
        name = name.toLowerCase();

        if (name.endsWith(".png")) {
            return "image/png";
        }

        if (name.endsWith(".jpg")) {
            return "image/jpeg";
        }

        if (name.endsWith(".html")) {
            return "text/html";
        }

        if (name.endsWith(".css")) {
            return "text/css";
        }

        if (name.endsWith(".js")) {
            return "application/javascript";
        }

        throw new IllegalArgumentException("Can't find content type for '" + name + "'.");
    }
}
