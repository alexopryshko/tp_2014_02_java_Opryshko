/**
 * Created by alexander on 19.02.14.
 */
public class User {
    private final long userId;
    private String username;
    private String password;

    public User() {
        userId = 0;
        username = null;
        password = null;
    }
    public User(long id_set, String username_set, String password_set) {
        userId = id_set;
        username = username_set;
        password = password_set;
    }

    public long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username_set) {
        username = username_set;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password_set) {
        password = password_set;
    }
}
