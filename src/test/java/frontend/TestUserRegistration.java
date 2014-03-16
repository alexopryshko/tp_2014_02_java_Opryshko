package frontend;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestUserRegistration {
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private HttpSession session = mock(HttpSession.class);
    private StringWriter stringWrite = new StringWriter();
    private PrintWriter writer = new PrintWriter(stringWrite);
    private Frontend frontend = new Frontend();

    private static String getTime() {
        Date date = new Date();
        DateFormat formatter = new SimpleDateFormat("DD.HH.mm.ss");
        return formatter.format(date);
    }

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
        Assert.assertTrue(stringWrite.toString().contains("false"));
    }

    @Test
    public void testCorrectUserAuthentication() throws Exception {
        when(request.getParameter("login")).thenReturn("correct" + getTime());
        when(request.getParameter("password")).thenReturn("correctData");
        frontend.doPost(request, response);
        Assert.assertTrue(stringWrite.toString().contains("true"));
    }
}
