package database;


public class SQLConnector implements Connector {
    private final String type;
    private final String host;
    private final String port;
    private final String name;
    private final String login;
    private final String password;

    public SQLConnector() {
        type =     "jdbc:mysql://";
        host =     "localhost:";
        port =     "3306/";
        name =     "game?";
        login =    "user=AlexO&";
        password = "password=pwd";
    }

    @Override
    public String getConnectionString() {
        return type + host + port + name + login + password;
    }
}
