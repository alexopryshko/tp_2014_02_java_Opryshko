package frontend;

import account.AccountService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;


public class TestFrontendRouting {
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private HttpSession session = mock(HttpSession.class);
    private AccountService accountService = mock(AccountService.class);
    private StringWriter stringWrite = new StringWriter();
    private PrintWriter writer = new PrintWriter(stringWrite);
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
        verify(response, atLeastOnce()).sendRedirect("/");
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
        when(accountService.isAuthorized(session.getAttribute("UserID"))).thenReturn(true);
        when(request.getPathInfo()).thenReturn("/");
        frontend.doGet(request, response);
        Assert.assertTrue(stringWrite.toString().contains("Timer"));
    }

    @Test
    public void testRoutingURLEscapeWithAuthorized() throws Exception {
        when(accountService.deAuthorizeUserByID(session.getAttribute("UserID"))).thenReturn(true);
        when(request.getPathInfo()).thenReturn("/escape");
        frontend.doGet(request, response);
        verify(response, atLeastOnce()).sendRedirect("/");
    }
}
