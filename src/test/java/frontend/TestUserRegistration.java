package frontend;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import static frontend.Frontend.getTime;
import static org.mockito.Mockito.*;

public class TestUserRegistration extends BasedFrontend {
 /*   private Frontend frontend = new Frontend();

    @Before
    public void setUp() throws IOException {
        when(request.getSession()).thenReturn(session);
        when(response.getWriter()).thenReturn(writer);
        when(request.getPathInfo()).thenReturn("/registration");
    }

    @Test
    public void testIncorrectUserRegistration() throws Exception {
        when(request.getParameter("login")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("admin");
        frontend.doPost(request, response);
        Assert.assertTrue(stringWrite.toString().contains("Same user already exist"));
    }

    @Test
    public void testCorrectUserAuthentication() throws Exception {
        when(request.getParameter("login")).thenReturn("correct" + getTime());
        when(request.getParameter("password")).thenReturn("correctData");
        frontend.doPost(request, response);
        verify(response, atLeastOnce()).sendRedirect("/time");
    }*/
}
