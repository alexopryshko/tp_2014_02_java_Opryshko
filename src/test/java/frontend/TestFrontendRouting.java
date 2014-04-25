package frontend;
import account.UserSession;
import main.ResourcesSystem;
import messageSystem.MessageSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.util.Map;

import static org.mockito.Mockito.*;


public class TestFrontendRouting extends BasedFrontend {

    protected Map users = mock(Map.class);
    protected Map usersToRegistration = mock(Map.class);
    private Frontend frontend = new Frontend(new MessageSystem(), users, usersToRegistration);
    private UserSession userSession = mock(UserSession.class);

    @Before
    public void setUp() throws IOException {
        ResourcesSystem.init("", "data/");
        when(request.getSession()).thenReturn(session);
        when(response.getWriter()).thenReturn(writer);
        when(session.getAttribute("UserID")).thenReturn((long) 0);
    }

    @Test
    public void testRoutingURLIndex() throws Exception {
        when(request.getPathInfo()).thenReturn("/");
        frontend.doGet(request, response);
        Assert.assertTrue(stringWrite.toString().contains("Game"));
    }

    @Test
    public void testRoutingURLTimeWithOutAuthorized() throws Exception {
        when(request.getPathInfo()).thenReturn("/time");
        frontend.doGet(request, response);
        verify(response, atLeastOnce()).sendRedirect("/");
    }

    @Test
    public void testRoutingURLEscapeWithOutAuthorized() throws Exception {
        when(request.getPathInfo()).thenReturn("/escape");
        frontend.doGet(request, response);
        Assert.assertTrue(stringWrite.toString().contains("404"));
    }

    @Test
    public void testRoutingURLRegistrationWithOutAuthorized() throws Exception {
        when(request.getPathInfo()).thenReturn("/registration");
        frontend.doGet(request, response);
        Assert.assertTrue(stringWrite.toString().contains("Registration"));
    }

    @Test
    public void testRoutingAnyURLWithOutAuthorizedYet() throws Exception {
        when(users.get(session.getId())).thenReturn(userSession);
        when(userSession.getUserId()).thenReturn(null);
        frontend.doGet(request, response);
        Assert.assertTrue(stringWrite.toString().contains("Wait for authorization"));
    }

    @Test
    public void testRoutingAnyURLWithErrorAuthorized() throws Exception {
        when(users.get(session.getId())).thenReturn(userSession);
        when(userSession.getUserId()).thenReturn(0L);
        frontend.doGet(request, response);
        Assert.assertTrue(stringWrite.toString().contains("Error"));
    }

    @Test
    public void testRoutingAnyURLWithAuthorized() throws Exception {
        when(users.get(session.getId())).thenReturn(userSession);
        when(userSession.getUserId()).thenReturn(10L);
        when(userSession.getName()).thenReturn("TestStringInRenderPage");
        frontend.doGet(request, response);
        Assert.assertTrue(stringWrite.toString().contains("TestStringInRenderPage"));
    }

    @Test
    public void testRoutingErrorURL() throws Exception {
        when(request.getPathInfo()).thenReturn("/error");
        frontend.doGet(request, response);
        Assert.assertTrue(stringWrite.toString().contains("404"));
    }

    @Test
    public void testRoutingWithIncorrectURL() throws Exception {
        when(request.getPathInfo()).thenReturn("/error");
        frontend.doPost(request, response);
        verify(response, atLeastOnce()).sendRedirect("/error");
    }

}
