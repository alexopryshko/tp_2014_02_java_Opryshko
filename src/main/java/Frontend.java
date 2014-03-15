import account.AccountService;
import account.User;
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

public class Frontend extends HttpServlet {

    private final AccountService accountService = new AccountService();

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

        if (accountService.isAuthorized(session.getAttribute("UserID"))) {
            if (request.getPathInfo().equals("/escape")) {
                logout(request, response);
            }
            else {
                renderGamePage(request, response);
            }
        }
        else if (request.getPathInfo().equals("/")) {
            renderMainPage(response);
        }
        else if (request.getPathInfo().equals("/time")) {
            response.sendRedirect("/");
        }
        else if (request.getPathInfo().equals("/escape")) {
            response.sendRedirect("/");
        }
        else if (request.getPathInfo().equals("/registration")) {
            renderRegistrationPage(response);
        }
        else {
            renderErrorPage(response);
        }


        /*

        if (request.getPathInfo().equals("/")) {
            if (!accountService.isAuthentication(users, session))
                response.getWriter().println(PageGenerator.getPage("index.tml", new HashMap<String, Object>()));
            else
                response.sendRedirect("/time");
        }

        else if (request.getPathInfo().equals("/time")) {
            if (!accountService.isAuthentication(users, session))
                response.sendRedirect("/");
            else {
                Long temp = (Long)session.getAttribute("UserID");
                Map<String, Object> pageVariables = new HashMap<>();
                pageVariables.put("UserID", temp);
                pageVariables.put("user", users.get(temp).getUsername());
                pageVariables.put("serverTime", getTime());
                response.getWriter().println(PageGenerator.getPage("time.tml", pageVariables));
            }

        }

        else if (request.getPathInfo().equals("/escape")) {
            if (!accountService.isAuthentication(users, session))
                response.sendRedirect("/");
            else {
                if (!users.isEmpty()) {
                    Long temp = (Long)session.getAttribute("UserID");
                    users.remove(temp);
                }
                session.removeAttribute("UserID");
                response.sendRedirect("/");
            }
        }

        else if (request.getPathInfo().equals("/registration")) {
            if (!accountService.isAuthentication(users, session))
                response.getWriter().println(PageGenerator.getPage("registration.tml", new HashMap<String, Object>()));
            else
                response.sendRedirect("/time");
        }

        else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().println(PageGenerator.getPage("404.tml", new HashMap<String, Object>()));
        }*/
    }

    public void setUsers (Map<Long, User> users) {
        //this.users = users;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        if (accountService.isAuthorized(session.getAttribute("UserID"))) {
            response.sendRedirect("/time");
        }
        else {
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            JSONObject json = new JSONObject();

            if (request.getPathInfo().equals("/login")) {
                Long userID = accountService.userAuthentication(login, password);
                if (userID != -1) {
                    session.setAttribute("UserID", userID);
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

            else if (request.getPathInfo().equals("/registration")) {
                if (accountService.userRegistration(login, password)) {
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
                //response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                //response.getWriter().println(PageGenerator.getPage("404.tml", new HashMap<String, Object>()));
            }
        }
    }

    private void renderGamePage(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        Long userID = (Long)session.getAttribute("UserID");
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("UserID", userID);
        pageVariables.put("user", accountService.getAuthorizeUserByID(userID).getUsername());
        pageVariables.put("serverTime", getTime());
        response.getWriter().println(PageGenerator.getPage("time.tml", pageVariables));
    }

    private void renderMainPage(HttpServletResponse response)
            throws ServletException, IOException
    {
        response.getWriter().println(PageGenerator.getPage("index.tml", new HashMap<String, Object>()));
    }

    private void renderRegistrationPage(HttpServletResponse response)
            throws ServletException, IOException
    {
        response.getWriter().println(PageGenerator.getPage("registration.tml", new HashMap<String, Object>()));
    }

    private void renderErrorPage(HttpServletResponse response)
            throws ServletException, IOException
    {
        response.getWriter().println(PageGenerator.getPage("404.tml", new HashMap<String, Object>()));
    }

    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        Long userID = (long)session.getAttribute("UserID");
        if (accountService.deAuthorizeUserByID(userID)) {
            session.removeAttribute("UserID");
            response.sendRedirect("/");
        }
        else {
            System.out.append("error");
        }

    }
}
