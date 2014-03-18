package frontend;
import account.AccountService;
import org.junit.Before;
import org.junit.Test;
import static frontend.Frontend.toLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;

public class TestPostRouting {
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
    }

    @Test
    public void testRoutingWithAuthorizedUser() throws Exception {
        when(accountService.isAuthorized(toLong(session.getAttribute("UserID")))).thenReturn(true);
        when(request.getPathInfo()).thenReturn("/");
        frontend.doPost(request, response);
        verify(response, atLeastOnce()).sendRedirect("/time");
    }

    @Test
    public void testRoutingWithIncorrectURL() throws Exception {
        when(request.getPathInfo()).thenReturn("/error");
        frontend.doPost(request, response);
        verify(response, atLeastOnce()).sendRedirect("/error");
    }
}
