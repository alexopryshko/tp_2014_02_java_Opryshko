package frontend;

import account.RegistrationStatus;
import account.UserSession;
import helper.TimeHelper;
import messageSystem.*;
import resourcesSystem.ResourceFactory;
import resourcesSystem.resources.URLs;

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

    public Frontend(MessageSystem messageSystem, Map<String, UserSession> users, Map<String, UserSession> usersToRegistration ) {
        this.users = users;
        this.usersToRegistration = usersToRegistration;
        address = new Address();
        this.messageSystem = messageSystem;
        messageSystem.addService(this);
    }

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

        URLs urls = (URLs) ResourceFactory.instance().getResource("data/urls.xml");
        String path = request.getPathInfo();
        HttpSession session = request.getSession();
        UserSession userSession;
        UserSession registrationUser;
        try {
            userSession = users.get(session.getId());
        }
        catch (NullPointerException e) {
            userSession = null;
        }
        try {
            registrationUser = usersToRegistration.get(session.getId());
        }
        catch (NullPointerException e) {
            registrationUser = null;
        }


        if (userSession == null) {
            if (path.equals(urls.getMAIN())) {
                renderPage("index.tml", response, null);
            }
            else if (path.equals(urls.getTIME())) {
                response.sendRedirect(urls.getMAIN());
            }
            else if (path.equals(urls.getREGISTRATION())) {
                if (registrationUser == null) {
                    renderPage("registration.tml", response, null);
                    return;
                }
                if (registrationUser.getRegistrationStatus() == RegistrationStatus.NOT_REGISTRATED) {
                    renderPage("info.tml", response, "Wait for registration");
                    return;
                }
                if (registrationUser.getRegistrationStatus() == RegistrationStatus.USER_ALREADY_EXIST) {
                    renderPage("registration.tml", response, "Error");
                    return;
                }
                if (registrationUser.getRegistrationStatus() == RegistrationStatus.SUCCESS) {
                    usersToRegistration.remove(registrationUser.getSessionId());
                    response.sendRedirect("/");
                }
            }
            else
                renderErrorPage(response);
        }
        else {
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
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        URLs urls = (URLs) ResourceFactory.instance().getResource("data/urls.xml");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String sessionId = request.getSession().getId();
        String path = request.getPathInfo();

        if (path.equals(urls.getLOGIN())) {
            if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
                response.sendRedirect("/");
                return;
            }
            UserSession userSession = new UserSession(sessionId, login);
            users.put(sessionId, userSession);
            Address frontendAddress = getAddress();
            Address accountServiceAddress = messageSystem.getAddressService().getAddressCustomers(Customers.ACCOUNTSERVICE);
            messageSystem.sendMessage(new MsgGetUserID(frontendAddress, accountServiceAddress, login, password, sessionId));
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.sendRedirect(urls.getMAIN());
        }
        else if (path.equals(urls.getREGISTRATION())) {
            if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
                response.sendRedirect(urls.getREGISTRATION());
                return;
            }
            UserSession userSession = new UserSession(sessionId, login);
            usersToRegistration.put(sessionId, userSession);
            Address frontendAddress = getAddress();
            Address accountServiceAddress = messageSystem.getAddressService().getAddressCustomers(Customers.ACCOUNTSERVICE);
            messageSystem.sendMessage(new MsgAddNewUser(frontendAddress, accountServiceAddress, login, password, sessionId));
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.sendRedirect(urls.getREGISTRATION());
        }
        else
            response.sendRedirect(urls.getERROR());

    }


    public void setId(String sessionId, Long userId) {
        UserSession userSession = users.get(sessionId);
        if (userSession == null) {
            System.out.append("Can't find user session for: ").append(sessionId);
            return;
        }
        userSession.setRegistrationStatus(RegistrationStatus.SUCCESS);
        userSession.setUserId(userId);
    }

    public void setRegistrationStatus(String sessionID, RegistrationStatus responseStatus) {
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

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public Customers getCustomers() {
        return Customers.FRONTEND;
    }

    @Override
    public void run() {
        while (true) {
            messageSystem.execForSubscriber(this);
            TimeHelper.sleep(10);
        }
    }
}
