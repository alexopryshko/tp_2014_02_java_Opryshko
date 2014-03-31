package frontend;

import account.UserSession;
import helper.TimeHelper;
import messageSystem.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Frontend extends HttpServlet implements Subscriber, Runnable {

    private static DateFormat formatter = new SimpleDateFormat("HH.mm.ss");

    private MessageSystem messageSystem;
    private Address address;

    private Map<String, UserSession> users = new ConcurrentHashMap<>();
    private Map<String, UserSession> usersToRegistration = new ConcurrentHashMap<>();

    public Frontend(MessageSystem messageSystem){
        address = new Address();
        this.messageSystem = messageSystem;
        messageSystem.addService(this);
    }

    public static String getTime() {
        return formatter.format(new Date());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        String path = request.getPathInfo();
        HttpSession session = request.getSession();
        UserSession userSession = users.get(session.getId());
        UserSession registrationUser = usersToRegistration.get(session.getId());

        if (userSession == null) {
            switch (path) {
                case "/":
                    renderPage("index.tml", response, null);
                    break;
                case "/time":
                    response.sendRedirect("/");
                    break;
                case "/registration":
                    if (registrationUser == null) {
                        renderPage("registration.tml", response, null);
                        return;
                    }
                    if (registrationUser.getRegistrationStatus() == -1) {
                        renderPage("info.tml", response, "Wait for registration");
                        return;
                    }
                    if (registrationUser.getRegistrationStatus() == 0) {
                        renderPage("registration.tml", response, "Error");
                        return;
                    }
                    if (registrationUser.getRegistrationStatus() == 1) {
                        usersToRegistration.remove(registrationUser.getSessionId());
                        response.sendRedirect("/");
                        return;
                    }
                    break;
                default:
                    renderErrorPage(response);
                    break;
            }
            return;
        }
        if (userSession.getUserId() == null) {
            renderPage("info.tml", response, "Wait for authorization");
            return;
        }
        if (userSession.getUserId() == 0) {
            renderPage("index.tml", response, "Error");
            users.remove(userSession.getSessionId());
            return;
        }
        renderGamePage(response, userSession);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String sessionId = request.getSession().getId();

        String path = request.getPathInfo();

        switch (path) {
            case "/login":
                UserSession userSession = new UserSession(sessionId, login);
                users.put(sessionId, userSession);
                Address frontendAddress = getAddress();
                Address accountServiceAddress = messageSystem.getAddressService().getAccountService();
                messageSystem.sendMessage(new MsgGetUserID(frontendAddress, accountServiceAddress, login, password, sessionId));
                response.setContentType("text/html;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_OK);
                response.sendRedirect("/");
                break;
            case "/registration":
                userSession = new UserSession(sessionId, login);
                usersToRegistration.put(sessionId, userSession);
                frontendAddress = getAddress();
                accountServiceAddress = messageSystem.getAddressService().getAccountService();
                messageSystem.sendMessage(new MsgAddNewUser(frontendAddress, accountServiceAddress, login, password, sessionId));
                response.setContentType("text/html;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_OK);
                response.sendRedirect("/registration");
                break;
            default:
                response.sendRedirect("/error");
                break;
        }
    }


    public void setId(String sessionId, Long userId) {
        UserSession userSession = users.get(sessionId);
        if (userSession == null) {
            System.out.append("Can't find user session for: ").append(sessionId);
            return;
        }
        userSession.setRegistrationStatus(1);
        userSession.setUserId(userId);
    }

    public void setRegistrationStatus(String sessionID, Integer responseStatus) {
        UserSession userSession = usersToRegistration.get(sessionID);
        if (userSession == null) {
            System.out.append("Can't find user session for: ").append(sessionID);
            return;
        }
        userSession.setRegistrationStatus(responseStatus);
    }

    private void renderGamePage(HttpServletResponse response, UserSession userSession)
            throws ServletException, IOException
    {

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("UserID", userSession.getUserId());
        pageVariables.put("user", userSession.getName());
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

    public Address getAddress() {
        return address;
    }

    public void run() {
        while (true) {
            messageSystem.execForSubscriber(this);
            TimeHelper.sleep(100);
        }
    }
}
