import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import javax.servlet.http.*;

import account.User;
import frontend.Frontend;
import frontend.PageGenerator;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


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
    public void testDoGetCase3() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        PrintWriter writer = new PrintWriter("static/testFile4.txt");
        Long lg = (long)1;

        Map<Long, User> users = new HashMap<>();
        User user = new User(1, "test", "test");
        users.put(lg, user);

        when(request.getPathInfo()).thenReturn("/");
        when(request.getSession()).thenReturn(session);

        when(session.getAttribute("UserID")).thenReturn((long)1);
        when(response.getWriter()).thenReturn(writer);

        Frontend frontend = new Frontend();

        //frontend.setUsers(users);

        frontend.doGet(request, response);


        writer.flush();
        assertTrue(FileUtils.readFileToString(new File("static/testFile4.txt"), "UTF-8").equals(""));
    }

    @Test
    public void testDoGetCase4() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        PrintWriter writer = new PrintWriter("static/testFile4.txt");
        Long lg = (long)1;

        Map<Long, User> users = new HashMap<>();
        User user = new User(1, "test", "test");
        users.put(lg, user);

        when(request.getPathInfo()).thenReturn("/time");
        when(request.getSession()).thenReturn(session);

        when(session.getAttribute("UserID")).thenReturn((long)1);
        when(response.getWriter()).thenReturn(writer);

        Frontend frontend = new Frontend();

        //frontend.setUsers(users);

        frontend.doGet(request, response);


        writer.flush();
        assertFalse(FileUtils.readFileToString(new File("static/testFile4.txt"), "UTF-8").equals(""));
    }

    @Test
    public void testDoGetCase5() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        PrintWriter writer = new PrintWriter("static/testFile4.txt");
        Long lg = (long)1;

        Map<Long, User> users = new HashMap<>();
        User user = new User(1, "test", "test");
        users.put(lg, user);

        when(request.getPathInfo()).thenReturn("/escape");
        when(request.getSession()).thenReturn(session);

        when(session.getAttribute("UserID")).thenReturn((long)1);
        when(response.getWriter()).thenReturn(writer);

        Frontend frontend = new Frontend();

        //frontend.setUsers(users);

        frontend.doGet(request, response);


        writer.flush();
        assertTrue(FileUtils.readFileToString(new File("static/testFile4.txt"), "UTF-8").equals(""));
    }

    @Test
    public void testDoGetCase6() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        PrintWriter writer = new PrintWriter("static/testFile4.txt");

        when(request.getPathInfo()).thenReturn("/escape");
        when(request.getSession()).thenReturn(session);

        when(session.getAttribute("UserID")).thenReturn((long)1);
        when(response.getWriter()).thenReturn(writer);

        new Frontend().doGet(request, response);

        writer.flush();
        assertTrue(FileUtils.readFileToString(new File("static/testFile4.txt"), "UTF-8").equals(""));
    }

    @Test
    public void testDoGetCase7() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        PrintWriter writer = new PrintWriter("static/testFile4.txt");

        when(request.getPathInfo()).thenReturn("/error");
        when(request.getSession()).thenReturn(session);

        when(response.getWriter()).thenReturn(writer);

        new Frontend().doGet(request, response);

        writer.flush();
        assertTrue(FileUtils.readFileToString(new File("static/testFile4.txt"), "UTF-8").
        contains(PageGenerator.getPage("404.tml", new HashMap<String, Object>())));
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

    @Test
    public void testDoPostCase3() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        PrintWriter writer = new PrintWriter("static/testFile5.txt");
        Long lg = (long)1;

        Map<Long, User> users = new HashMap<>();
        User user = new User(1, "test", "test");
        users.put(lg, user);

        when(request.getPathInfo()).thenReturn("/time");
        when(request.getSession()).thenReturn(session);

        when(session.getAttribute("UserID")).thenReturn((long)1);
        when(response.getWriter()).thenReturn(writer);

        Frontend frontend = new Frontend();

        //frontend.setUsers(users);

        frontend.doPost(request, response);


        writer.flush();
        assertTrue(FileUtils.readFileToString(new File("static/testFile5.txt"), "UTF-8").equals(""));
    }

    @Test
    public void testDoPostCase4() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        PrintWriter writer = new PrintWriter("static/testFile5.txt");


        when(request.getPathInfo()).thenReturn("/time");
        when(request.getSession()).thenReturn(session);

        when(session.getAttribute("UserID")).thenReturn(null);
        when(response.getWriter()).thenReturn(writer);


        new Frontend().doPost(request, response);


        writer.flush();
        assertTrue(FileUtils.readFileToString(new File("static/testFile5.txt"), "UTF-8").equals(""));
    }

}
