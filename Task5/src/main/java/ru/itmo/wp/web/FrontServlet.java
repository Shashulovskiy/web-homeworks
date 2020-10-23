package ru.itmo.wp.web;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import freemarker.template.*;
import ru.itmo.wp.web.exception.NotFoundException;
import ru.itmo.wp.web.exception.RedirectException;
import ru.itmo.wp.web.page.IndexPage;
import ru.itmo.wp.web.page.NotFoundPage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class FrontServlet extends HttpServlet {
    private static final String BASE_PACKAGE = FrontServlet.class.getPackage().getName() + ".page";
    private static final String DEFAULT_ACTION = "action";
    private static final Set<Class<?>> ALLOWED_ACTION_PARAMETER_TYPES = Sets.newHashSet(Map.class, HttpServletRequest.class);

    private Configuration sourceConfiguration;
    private Configuration targetConfiguration;

    private Configuration newFreemarkerConfiguration(String templateDirName, boolean debug) throws ServletException {
        File templateDir = new File(templateDirName);
        if (!templateDir.isDirectory()) {
            return null;
        }

        Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
        try {
            configuration.setDirectoryForTemplateLoading(templateDir);
        } catch (IOException e) {
            throw new ServletException("Can't create freemarker configuration [templateDir=" + templateDir + "]");
        }
        configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
        configuration.setTemplateExceptionHandler(debug ? TemplateExceptionHandler.HTML_DEBUG_HANDLER :
                TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(false);
        configuration.setWrapUncheckedExceptions(true);

        return configuration;
    }

    @Override
    public void init() throws ServletException {
        sourceConfiguration = newFreemarkerConfiguration(
                getServletContext().getRealPath("/") + "../../src/main/webapp/WEB-INF/templates", true);
        targetConfiguration = newFreemarkerConfiguration(getServletContext().getRealPath("WEB-INF/templates"), false);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Route route = Route.newRoute(request);
        processLocalization(request);
        try {
            process(route, request, response);
        } catch (NotFoundException e) {
            try {
                process(Route.newNotFoundRoute(), request, response);
            } catch (NotFoundException notFoundException) {
                throw new ServletException(notFoundException);
            }
        }
    }

    private void processLocalization(HttpServletRequest request) {
        if (request.getParameter("lang") != null) {
            request.getSession().setAttribute("lang", request.getParameter("lang"));
        }
    }

    private void process(Route route, HttpServletRequest request, HttpServletResponse response) throws NotFoundException, ServletException, IOException {
        Class<?> pageClass;
        try {
            pageClass = Class.forName(route.getClassName());
        } catch (ClassNotFoundException e) {
            throw new NotFoundException();
        }

        Method method = null;
        for (Class<?> clazz = pageClass; method == null && clazz != null; clazz = clazz.getSuperclass()) {
            Method[] methods = clazz.getDeclaredMethods();
            for (Method clazzMethod : methods) {
                if (clazzMethod.getName().equals(route.getAction())) {
                    method = clazzMethod;
                    boolean found = true;
                    for (Class<?> parameterType : method.getParameterTypes()) {
                        if (!ALLOWED_ACTION_PARAMETER_TYPES.contains(parameterType)) {
                            found = false;
                        }
                    }
                    if (found) break;
                }
            }
        }

        if (method == null) {
            throw new NotFoundException();
        }

        Object page;
        try {
            page = pageClass.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new ServletException("Can't create page [pageClass=" + pageClass + "]");
        }

        Map<String, Object> view = new HashMap<>();
        method.setAccessible(true);
        try {
            List<Object> args = new ArrayList<>();
            for (Class<?> parameterType : method.getParameterTypes()) {
                if (parameterType == Map.class) {
                    args.add(view);
                } else if (parameterType == HttpServletRequest.class) {
                    args.add(request);
                } else {
                    throw new ServletException("Illegal method signature for route's action method [method=" + method + ", parameterType=" + parameterType + "]");
                }
            }
            method.invoke(page, args.toArray(new Object[0]));
        } catch (IllegalAccessException e) {
            throw new ServletException("Can't invoke action method [pageClass=" + pageClass + ", method=" + method + "]");
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RedirectException) {
                RedirectException redirectException = (RedirectException) cause;
                response.sendRedirect(redirectException.getTarget());
                return;
            } else {
                throw new ServletException("Can't invoke action method [pageClass=" + pageClass + ", method=" + method + "]", cause);
            }
        }

        Template template;
        response.setContentType("text/html");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String localization = (String) request.getSession().getAttribute("lang");
        if (localization != null && localization.length() == 2) {
            try {
                template = newTemplate(pageClass.getSimpleName() + "_" + request.getSession().getAttribute("lang") + ".ftlh");
                template.process(view, response.getWriter());
                return;
            } catch (TemplateException | ServletException ignored) {
                // No operations
            }
        }

        template = newTemplate(pageClass.getSimpleName() + ".ftlh");
        try {
            template.process(view, response.getWriter());
        } catch (TemplateException e) {
            throw new ServletException("Can't render template [pageClass=" + pageClass + ", action=" + method + "]", e);
        }
    }

    private Template newTemplate(String templateName) throws ServletException {
        Template template = null;

        if (sourceConfiguration != null) {
            try {
                template = sourceConfiguration.getTemplate(templateName);
            } catch (TemplateNotFoundException ignored) {
                // No operations.
            } catch (IOException e) {
                throw new ServletException("Can't load template [templateName=" + templateName + "]", e);
            }
        }

        if (template == null && targetConfiguration != null) {
            try {
                template = targetConfiguration.getTemplate(templateName);
            } catch (TemplateNotFoundException ignored) {
                // No operations.
            } catch (IOException e) {
                throw new ServletException("Can't load template [templateName=" + templateName + "]", e);
            }
        }

        if (template == null) {
            throw new ServletException("Can't find template [templateName=" + templateName + "]");
        }

        return template;
    }

    private static class Route {
        private final String className;
        private final String action;

        private Route(String className, String action) {
            this.className = className;
            this.action = action;
        }

        private String getClassName() {
            return className;
        }

        private String getAction() {
            return action;
        }

        private static Route newNotFoundRoute() {
            return new Route(
                    NotFoundPage.class.getName(),
                    DEFAULT_ACTION
            );
        }

        private static Route newIndexRoute() {
            return new Route(
                    IndexPage.class.getName(),
                    DEFAULT_ACTION
            );
        }

        private static Route newRoute(HttpServletRequest request) {
            String uri = request.getRequestURI();

            List<String> classNameParts = Arrays.stream(uri.split("/"))
                    .filter(part -> !Strings.isNullOrEmpty(part))
                    .collect(Collectors.toList());

            if (classNameParts.isEmpty()) {
                return newIndexRoute();
            }

            StringBuilder simpleClassName = new StringBuilder(classNameParts.get(classNameParts.size() - 1));
            int lastDotIndex = simpleClassName.lastIndexOf(".");
            simpleClassName.setCharAt(lastDotIndex + 1,
                    Character.toUpperCase(simpleClassName.charAt(lastDotIndex + 1)));
            classNameParts.set(classNameParts.size() - 1, simpleClassName.toString());

            String className = BASE_PACKAGE + "." + String.join(".", classNameParts) + "Page";

            String action = request.getParameter("action");
            if (Strings.isNullOrEmpty(action)) {
                action = DEFAULT_ACTION;
            }

            return new Route(className, action);
        }
    }
}
