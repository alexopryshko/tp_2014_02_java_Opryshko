package frontend;
import account.AccountService;
import org.junit.Before;
import org.junit.Test;
import static frontend.Frontend.toLong;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class TestPostRouting extends BasedFrontend {
  /*  private AccountService accountService = mock(AccountService.class);
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
    }*/
}
