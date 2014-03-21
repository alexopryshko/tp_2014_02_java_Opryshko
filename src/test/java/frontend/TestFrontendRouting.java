package frontend;

import account.AccountService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static frontend.Frontend.toLong;
import java.io.IOException;
import static org.mockito.Mockito.*;


public class TestFrontendRouting extends BasedFrontend {
    private AccountService accountService = mock(AccountService.class);
    private Frontend frontend = new Frontend(accountService);

    @Before
    public void setUp() throws IOException {
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
    public void testRoutingErrorURL() throws Exception {
        when(request.getPathInfo()).thenReturn("/error");
        frontend.doGet(request, response);
        Assert.assertTrue(stringWrite.toString().contains("404"));
    }

    @Test
    public void testRoutingAnyURLWithAuthorized() throws Exception {
        when(accountService.isAuthorized(toLong(session.getAttribute("UserID")))).thenReturn(true);
        when(request.getPathInfo()).thenReturn("/");
        frontend.doGet(request, response);
        Assert.assertTrue(stringWrite.toString().contains("Timer"));
    }

    @Test
    public void testRoutingURLEscapeWithAuthorized() throws Exception {
        when(accountService.isAuthorized(toLong(session.getAttribute("UserID")))).thenReturn(true);
        when(accountService.deAuthorizeUserByID(toLong(session.getAttribute("UserID")))).thenReturn(true);
        when(request.getPathInfo()).thenReturn("/escape");
        frontend.doGet(request, response);
        verify(response, atLeastOnce()).sendRedirect("/");
    }
}
