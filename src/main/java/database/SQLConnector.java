package database;

import java.sql.*;

public class SQLConnector {
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

    public Connection create() {
        try{
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());
            return DriverManager.getConnection(type + host + port + name + login + password);

        } catch (SQLException |
                InstantiationException |
                IllegalAccessException |
                ClassNotFoundException e) {
            return  null;
        }
    }


}
