package ru.javaops.masterjava.web;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import ru.javaops.masterjava.xml.schema.User;
import ru.javaops.masterjava.xml.util.JaxbParser;
import ru.javaops.masterjava.xml.util.StaxStreamProcessor;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(loadOnStartup = 1, urlPatterns = "/index")
@MultipartConfig
public class ParseXmlServlet extends HttpServlet {

    private TemplateEngine templateEngine;

    @Override
    public void init() {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(getServletContext());
        templateResolver.setTemplateMode("XHTML");
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheTTLMs(3600000L);
        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        WebContext ctx = new WebContext(req, resp, getServletContext(), req.getLocale());
        templateEngine.process("index", ctx, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        final Part filePart = req.getPart("file");
        List<User> users = new ArrayList<>();
        try (StaxStreamProcessor processor = new StaxStreamProcessor(filePart.getInputStream())) {
            JaxbParser parser = new JaxbParser(User.class);

            while (processor.doUntil(XMLEvent.START_ELEMENT, "User")) {
                User user = parser.unmarshal(processor.getReader(), User.class);
                users.add(user);
            }
        } catch (XMLStreamException | JAXBException e) {
            e.printStackTrace();
        }

        WebContext ctx = new WebContext(req, resp, getServletContext(), req.getLocale());
        ctx.setVariable("users", users);
        templateEngine.process("user_table", ctx, resp.getWriter());
    }

}
