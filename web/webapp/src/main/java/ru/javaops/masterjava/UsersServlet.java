package ru.javaops.masterjava;

import org.thymeleaf.context.WebContext;
import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.persist.dao.UserDao;
import ru.javaops.masterjava.persist.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static ru.javaops.masterjava.common.web.ThymeleafListener.engine;

@WebServlet(urlPatterns = "/")
public class UsersServlet extends HttpServlet {

    private static final int USERS_AMOUNT_TO_DISPLAY = 20;

    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        this.userDao = DBIProvider.getDao(UserDao.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final WebContext webContext = new WebContext(req, resp, req.getServletContext(), req.getLocale());
        List<User> users = userDao.getWithLimit(USERS_AMOUNT_TO_DISPLAY);
        webContext.setVariable("users", users);
        engine.process("users", webContext, resp.getWriter());
    }

}
