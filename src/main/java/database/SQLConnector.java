package database;

public class SQLConnector implements Connector {
    private String type;
    private String host;
    private String port;
    private String name;
    private String login;
    private String password;

    public SQLConnector(String host, Integer port, String name, String login, String pwd) {
        this.type =     "jdbc:mysql://";
        this.host =     host + ":";
        this.port =     port.toString() + "/";
        this.name =     name + "?";
        this.login =    "user=" + login +"&";
        this.password = "password=" + pwd;
    }

    @Override
    public String getConnectionString() {
        return type + host + port + name + login + password;
    }
}
