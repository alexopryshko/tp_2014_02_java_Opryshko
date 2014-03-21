package functionality;

import com.sun.istack.internal.NotNull;
import org.eclipse.jetty.server.Server;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import static main.CreateServer.createServer;


public abstract class TestFunctional {
    protected String login;
    protected String password;
    protected final static int PORT = 8080;
    protected static Server server;

    @BeforeClass
    public static void setUp() throws Exception {
        server = createServer(PORT);
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
