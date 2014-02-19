/**
 * Created by alexander on 15.02.14.
 */

//import PageGenerator;
import sun.security.util.Password;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Frontend extends HttpServlet {

    private String login = "";
    private String password = "";

    private AtomicLong userIdGenerator = new AtomicLong();

    private static String getTime() {
        Date date = new Date();
        date.getTime();
        DateFormat formatter = new SimpleDateFormat("HH.mm.ss");
        return formatter.format(date);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        login = request.getParameter("login");
        password = request.getParameter("password");

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        HttpSession session = request.getSession();

        if (request.getPathInfo().equals("/")) {
            if (!AuthUser.isAuthentication(session))
                response.getWriter().println(PageGenerator.getPage("index.tml", new HashMap<String, Object>()));
            else
                response.sendRedirect("/time");
        }

        else if (request.getPathInfo().equals("/time")) {
            if (!AuthUser.isAuthentication(session))
                response.sendRedirect("/");
            else {
                Map<String, Object> pageVariables = new HashMap<>();
                pageVariables.put("user", session.getAttribute("UserID"));
                pageVariables.put("serverTime", getTime());
                response.getWriter().println(PageGenerator.getPage("time.tml", pageVariables));
            }

        }
        else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().println(PageGenerator.getPage("404.tml", new HashMap<String, Object>()));
        }

/*
        if (session.getAttribute("username") != null) {

            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("user", session.getAttribute("UserID"));
            pageVariables.put("serverTime", getTime());

            response.getWriter().println(PageGenerator.getPage("time.tml", pageVariables));
        }
        else {
            //response.sendRedirect("/");
            Map<String, Object> pageVariables = new HashMap<>();
            response.getWriter().println(PageGenerator.getPage("index.tml", pageVariables));
        }

*/


    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        HttpSession session = request.getSession();
        if (AuthUser.getUserByLogin(login, password)) {
            User user = new User(userIdGenerator.getAndIncrement(), login, password);

            session.setAttribute("UserID", user.getID());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("password", user.getPassword());

            //Map<String, Object> pageVariables = new HashMap<>();
            //pageVariables.put("user", user.getID());
            //pageVariables.put("serverTime", getTime());
            response.sendRedirect("/time");

            //response.getWriter().println(PageGenerator.getPage("time.tml", pageVariables));
        }
        else {
            response.sendRedirect("/");
            //Map<String, Object> pageVariables = new HashMap<>();
            //response.getWriter().println(PageGenerator.getPage("index.tml", pageVariables));
        }
    }
}