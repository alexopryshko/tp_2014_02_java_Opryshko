package frontend;

import account.AccountService;
import account.User;
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

    private final AccountService accountService;
    private static DateFormat formatter;

    public Frontend(){
        accountService = new AccountService();
        formatter = new SimpleDateFormat("HH.mm.ss");
    }

    public Frontend(AccountService _accountService){
        accountService  = _accountService;
        formatter = new SimpleDateFormat("HH.mm.ss");
    }

    private static String getTime() {
        return formatter.format(new Date());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        HttpSession session = request.getSession();
        String path = request.getPathInfo();
        Object ID = session.getAttribute("UserID");

        if (accountService.isAuthorized(toLong(ID))) {
            if (path.equals("/escape")) {
                logout(request, response);
            }
            else {
                renderGamePage(request, response);
            }
        }
        else {
            switch (path) {
                case "/":
                    renderPage("index.tml", response, null);
                    break;
                case "/time":
                    response.sendRedirect("/");
                    break;
                case "/registration":
                    renderPage("registration.tml", response, null);
                    break;
                default:
                    renderErrorPage(response);
                    break;
            }
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Object ID = session.getAttribute("UserID");

        if (accountService.isAuthorized(toLong(ID))) {
            response.sendRedirect("/time");
        }
        else {
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            String path = request.getPathInfo();
            Long userID;

            switch (path) {
                case "/login":
                    userID = accountService.userAuthentication(login, password);
                    if (userID != -1) {
                        session.setAttribute("UserID", userID);
                        response.sendRedirect("/time");
                    }
                    else {
                        renderPage("index.tml", response, "Error");
                    }
                    break;
                case "/registration":
                    if (accountService.userRegistration(login, password)) {
                        userID = accountService.userAuthentication(login, password);
                        session.setAttribute("UserID", userID);
                        response.sendRedirect("/time");
                    }
                    else {
                        renderPage("registration.tml", response, "error");
                    }
                    break;
                default:
                    response.sendRedirect("/error");
                    break;
            }

        }
    }

    public static Long toLong(Object value) {
        try {
            return (long)value;
        }
        catch (Exception e) {
            return null;
        }
    }

    private void renderGamePage(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        Long userID = toLong(session.getAttribute("UserID"));
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("UserID", userID);
        User renderUser = accountService.getAuthorizeUserByID(userID);
        if (renderUser != null) {
            pageVariables.put("user", renderUser.getUsername());
        }
        else {
            pageVariables.put("user", "test");
        }
        pageVariables.put("serverTime", getTime());
        response.getWriter().println(PageGenerator.getPage("time.tml", pageVariables));
    }


    private void renderPage(String tmlFile, HttpServletResponse response, String result)
            throws ServletException, IOException
    {
        Map<String, Object> map = new HashMap<>();
        map.put("error", result);
        response.getWriter().println(PageGenerator.getPage(tmlFile, map));
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
        Object ID = session.getAttribute("UserID");
        if (accountService.deAuthorizeUserByID(toLong(ID))) {
            session.removeAttribute("UserID");
            response.sendRedirect("/");
        }
        else {
            response.sendRedirect("/error");
        }

    }
}
