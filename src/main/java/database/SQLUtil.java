package database;


public class SQLUtil implements Connector {
    private final String type;
    private final String host;
    private final String port;
    private final String name;
    private final String login;
    private final String password;

    public SQLUtil() {
        type =     "jdbc:mysql://";
        host =     "localhost:";
        port =     "3306/";
        name =     "game?";
        login =    "user=AlexO&";
        password = "password=pwd";
    }

    public String getConnectionString() {
        return type + host + port + name + login + password;
    }
}
