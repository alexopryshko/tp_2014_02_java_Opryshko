/**
 * Created by alexander on 15.02.14.
 */


import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Frontend extends HttpServlet {

    Map<Long, User> users = new HashMap<Long, User>();

    private AtomicLong userIdGenerator = new AtomicLong();

    private static String getTime() {
        Date date = new Date();
        DateFormat formatter = new SimpleDateFormat("HH.mm.ss");
        return formatter.format(date);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        HttpSession session = request.getSession();

        if (request.getPathInfo().equals("/")) {
            if (!AuthUser.isAuthentication(users, session))
                response.getWriter().println(PageGenerator.getPage("index.tml", new HashMap<String, Object>()));
            else
                response.sendRedirect("/time");
        }

        else if (request.getPathInfo().equals("/time")) {
            if (!AuthUser.isAuthentication(users, session))
                response.sendRedirect("/");
            else {
                Map<String, Object> pageVariables = new HashMap<String, Object>();
                pageVariables.put("UserID", session.getAttribute("UserID"));
                pageVariables.put("user", users.get(session.getAttribute("UserID")).getUsername());
                pageVariables.put("serverTime", getTime());
                response.getWriter().println(PageGenerator.getPage("time.tml", pageVariables));
            }

        }

        else if (request.getPathInfo().equals("/escape")) {
            if (!AuthUser.isAuthentication(users, session))
                response.sendRedirect("/");
            else {
                users.remove(users.get(session.getAttribute("UserID")));
                session.removeAttribute("UserID");
                response.sendRedirect("/");
            }
        }

        else if (request.getPathInfo().equals("/registration")) {
            if (!AuthUser.isAuthentication(users, session))
                response.getWriter().println(PageGenerator.getPage("registration.tml", new HashMap<String, Object>()));
            else
                response.sendRedirect("/time");
        }

        else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().println(PageGenerator.getPage("404.tml", new HashMap<String, Object>()));
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        if (AuthUser.isAuthentication(users, session)) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.sendRedirect("/time");
        }

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (request.getPathInfo().equals("/login")) {
            if (AuthUser.isRegistered(login, password)) {
                User user = new User(userIdGenerator.getAndIncrement(), login, password);

                session.setAttribute("UserID", user.getUserId());
                users.put(user.getUserId(), user);

                response.sendRedirect("/time");
            }
            else
                response.sendRedirect("/");
        }

        else if (request.getPathInfo().equals("/registration")) {

            JSONObject json = new JSONObject();
            if (AuthUser.registration(login, password)) {
                try {
                    json.put("error", true);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                response.setContentType("application/json");
                response.getWriter().write(json.toString());
            }
            else {

                try {
                    json.put("error", false);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                response.setContentType("application/json");
                response.getWriter().write(json.toString());
            }

        }

        else {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.sendRedirect("/error");
        }


    }
}
