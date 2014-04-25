package resourcesSystem.resources;

import resourcesSystem.Resource;

public class DatabaseConf implements Resource{
    private String host;
    private int port;
    private String name;
    private String login;
    private String password;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
