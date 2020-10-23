package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class MessageServlet extends HttpServlet {

    private final ArrayList<Message> messages = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        if (uri.startsWith("/message/auth")) {
            String name = request.getParameter("user");
            if (name != null) {
                request.getSession().setAttribute("user", name);
            }
            setResponseContent(name == null ? request.getSession().getAttribute("user") : name, response);
        } else if (uri.startsWith("/message/findAll")) {
            synchronized (messages) {
                setResponseContent(messages, response);
            }
        } else if (uri.startsWith("/message/add")) {
            synchronized (messages) {
                Message message = new Message((String) request.getSession().getAttribute("user"), request.getParameter("text"));
                messages.add(message);
                setResponseContent(message, response);
            }
        }
    }

    private void setResponseContent(Object responseObject, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        String json = new Gson().toJson(responseObject);
        response.getWriter().print(json);
        response.getWriter().flush();
    }

    private static class Message {
        public String user;
        public String text;

        public Message(String user, String text) {
            this.user = user;
            this.text = text;
        }
    }
}