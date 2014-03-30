package frontend;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import static org.mockito.Mockito.*;

public class TestUserAuthentication extends BasedFrontend {

 /*   private Frontend frontend = new Frontend();

    @Before
    public void setUp() throws IOException {
        when(request.getSession()).thenReturn(session);
        when(response.getWriter()).thenReturn(writer);
        when(request.getPathInfo()).thenReturn("/login");
    }

    @Test
    public void testCorrectUserAuthentication() throws Exception {
        when(request.getParameter("login")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("admin");
        frontend.doPost(request, response);
        verify(response, atLeastOnce()).sendRedirect("/time");
    }

    @Test
    public void testIncorrectUserAuthentication() throws Exception {
        when(request.getParameter("login")).thenReturn("IncorrectData");
        when(request.getParameter("password")).thenReturn("IncorrectData");
        frontend.doPost(request, response);
        Assert.assertTrue(stringWrite.toString().contains("Error in login or password"));
    }
    */
}
