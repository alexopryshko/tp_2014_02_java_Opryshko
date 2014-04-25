package resourcesSystem.resources;

import resourcesSystem.Resource;

public class SQLQueries implements Resource {

    private String selectUserByUsername;
    private String selectIDByUsername;
    private String insertUser;

    public String getSelectUserByUsername() {
        return selectUserByUsername;
    }

    public String getSelectIDByUsername() {
        return selectIDByUsername;
    }

    public String getInsertUser() {
        return insertUser;
    }
}
