package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;

public class CaptchaFilter extends HttpFilter {

    private static final String captchaSessionAttributeName = "CaptchaSolved";

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getMethod().equals("GET")) {
            HttpSession session = request.getSession();
            Object captchaSessionAttribute = session.getAttribute(captchaSessionAttributeName);
            if (captchaSessionAttribute == null) {
                sendCaptchaPage(request, response);
            } else if (!captchaSessionAttribute.equals("Solved")) {
                validateCaptcha(request, response, chain);
            } else {
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    private void validateCaptcha(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        try {
            if (session.getAttribute(captchaSessionAttributeName).equals(Integer.parseInt(request.getParameter("captcha")))) {
                session.setAttribute(captchaSessionAttributeName, "Solved");
                chain.doFilter(request, response);
            } else {
                sendCaptchaPage(request, response);
            }
        } catch (NumberFormatException ignored) {
            sendCaptchaPage(request, response);
        }
    }

    private void sendCaptchaPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int randomInteger = ThreadLocalRandom.current().nextInt(100, 1000);
        session.setAttribute(captchaSessionAttributeName, randomInteger);
        session.setAttribute("captcha-image", Base64.getEncoder().encodeToString(ImageUtils.toPng(Integer.toString(randomInteger))));
        request.getRequestDispatcher("/static/jsp/captcha.jsp").forward(request, response);
    }
}
