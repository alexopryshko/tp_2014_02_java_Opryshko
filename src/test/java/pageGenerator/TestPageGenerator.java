package pageGenerator;

import frontend.PageGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by alexander on 15.03.14.
 */
public class TestPageGenerator {
    Map<String, Object> map = new HashMap<>();

    @Before
    public void setUp() {
        map.put("UserID", 0);
        map.put("user", "test");
        map.put("serverTime", "testTime");
    }

    @Test
    public void testGetPage() throws Exception {
        Assert.assertTrue(PageGenerator.getPage("index.tml", map).contains("Авторизация"));
        Assert.assertTrue(PageGenerator.getPage("registration.tml", map).contains("Регистрация"));
        Assert.assertTrue(PageGenerator.getPage("time.tml", map).contains("Timer"));
        Assert.assertTrue(PageGenerator.getPage("404.tml", map).contains("404"));
    }

    @Test
    public void testGetPageSuccess() throws Exception {
        File tml = new File("tml/");
        File[] tmls = tml.listFiles();

        int i = (new Random()).nextInt(tmls.length);
        String test = PageGenerator.getPage(tmls[i].getName(), map);
        Assert.assertFalse(test.equals(""));
    }

    @Test
    public void testGetPageFailure() throws Exception {
        String wrongPath = "error.tml";
        String tmpl = PageGenerator.getPage(wrongPath, map);
        Assert.assertEquals(tmpl, "");
    }
}
