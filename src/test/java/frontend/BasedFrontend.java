package frontend;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.mock;

public abstract class BasedFrontend {
    protected HttpServletRequest request = mock(HttpServletRequest.class);
    protected HttpServletResponse response = mock(HttpServletResponse.class);
    protected HttpSession session = mock(HttpSession.class);
    protected StringWriter stringWrite = new StringWriter();
    protected PrintWriter writer = new PrintWriter(stringWrite);

}
