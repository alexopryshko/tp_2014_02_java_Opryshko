package functionality;

import com.sun.istack.internal.NotNull;
import frontend.Frontend;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;


public abstract class TestFunctional {
    protected String login;
    protected String password;
    protected final static int PORT = 8080;
    protected static Server server;

    protected static String generateString(int length){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++)
            result.append((char) (65 + Math.random() * 25));
        return result.toString();
    }

    @BeforeClass
    public static void setUp() throws Exception {
        server = new Server(PORT);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(new Frontend()), "/*");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("static");

        ContextHandler staticContextHandler = new ContextHandler();
        staticContextHandler.setContextPath("/static");
        staticContextHandler.setHandler(resource_handler);

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{staticContextHandler, context});
        server.setHandler(handlers);

        server.start();


    }

    @AfterClass
    public static void clearUp() throws Exception {
        server.stop();
    }

    protected boolean functionalTest(@NotNull String ip, @NotNull String login, @NotNull String password) {
        WebDriver driver = new HtmlUnitDriver();

        driver.get(ip);

        WebElement loginField = driver.findElement(By.name("login"));
        loginField.sendKeys(login);
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys(password);
        WebElement Button = driver.findElement(By.name("button"));
        Button.submit();

        boolean result;
        try {
            result = (new WebDriverWait(driver, 1)).until(new ExpectedCondition<Boolean>() {
                @Override
                @NotNull
                public Boolean apply(@NotNull WebDriver d) {
                    WebElement el;
                    try {
                        el = d.findElement(By.id("user"));
                    }
                    catch (NoSuchElementException e) {
                        el = null;
                    }
                    return el != null;
                }
            });
        }
        catch (Exception e) {
            result = false;
        }
        driver.quit();
        return result;
    }
}
