package messageSystem;

import account.AccountService;
import frontend.Frontend;
import helper.TimeHelper;
import main.ResourcesSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FunctionalTestMS {
    private MessageSystem messageSystem = new MessageSystem();
    private Frontend frontend = new Frontend(messageSystem);
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private HttpSession session = mock(HttpSession.class);

    @Before
    public void setUp() {
        ResourcesSystem.init("", "data/");
        AccountService accountService = new AccountService(messageSystem);
        (new Thread(frontend)).start();
        (new Thread(accountService)).start();
    }

    @Test
    public void testAuthorizationWithCorrectData() throws IOException, ServletException {
        String login = "admin";
        String password = "admin";
        when(request.getParameter("login")).thenReturn(login);
        when(request.getParameter("password")).thenReturn(password);
        when(request.getSession()).thenReturn(session);
        when(session.getId()).thenReturn("someId");
        when(request.getPathInfo()).thenReturn("/login");
        frontend.doPost(request, response);
        TimeHelper.sleep(2000);
        Assert.assertTrue(frontend.getId("someId") == 36);
    }

    @Test
    public void testAuthorizationWithIncorrectData() throws IOException, ServletException {
        String login = "inc_admin";
        String password = "inc_admin";
        when(request.getParameter("login")).thenReturn(login);
        when(request.getParameter("password")).thenReturn(password);
        when(request.getSession()).thenReturn(session);
        when(session.getId()).thenReturn("someId");
        when(request.getPathInfo()).thenReturn("/login");
        frontend.doPost(request, response);
        TimeHelper.sleep(2000);
        Assert.assertFalse(frontend.getId("someId") != 0);
    }


}
