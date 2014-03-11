import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import javax.servlet.http.*;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;


public class TestFrontend {

    @Test
    public void testDoGetCase1() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        PrintWriter writer = new PrintWriter("static/testFile.txt");

        when(request.getPathInfo()).thenReturn("/");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("UserID")).thenReturn(null);
        when(response.getWriter()).thenReturn(writer);

        new Frontend().doGet(request, response);

        writer.flush();
        assertTrue(FileUtils.readFileToString(new File("static/testFile.txt"), "UTF-8")
                .contains(PageGenerator.getPage("index.tml", new HashMap<String, Object>())));

    }

    @Test
    public void testDoGetCase2() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        PrintWriter writer = new PrintWriter("static/testFile1.txt");

        when(request.getPathInfo()).thenReturn("/registration");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("UserID")).thenReturn(null);
        when(response.getWriter()).thenReturn(writer);

        new Frontend().doGet(request, response);

        writer.flush();
        assertTrue(FileUtils.readFileToString(new File("static/testFile1.txt"), "UTF-8")
                .contains(PageGenerator.getPage("registration.tml", new HashMap<String, Object>())));
    }

    @Test
    public void testDoPostCase1() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        PrintWriter writer = new PrintWriter("static/testFile2.txt");

        when(request.getParameter("login")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("admin");
        when(request.getPathInfo()).thenReturn("/login");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("UserID")).thenReturn(null);
        when(response.getWriter()).thenReturn(writer);

        JSONObject json = new JSONObject();
        try {
            json.put("error", true);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        writer.flush();
        new Frontend().doPost(request, response);

        writer.flush();
        assertTrue(FileUtils.readFileToString(new File("static/testFile2.txt"), "UTF-8").contains(json.toString()));
    }

    @Test
    public void testDoPostCase2() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        PrintWriter writer = new PrintWriter("static/testFile3.txt");

        when(request.getParameter("login")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("admin");
        when(request.getPathInfo()).thenReturn("/registration");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("UserID")).thenReturn(null);
        when(response.getWriter()).thenReturn(writer);

        JSONObject json = new JSONObject();
        try {
            json.put("error", false);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        writer.flush();
        new Frontend().doPost(request, response);

        writer.flush();
        assertTrue(FileUtils.readFileToString(new File("static/testFile3.txt"), "UTF-8").contains(json.toString()));
    }
}
