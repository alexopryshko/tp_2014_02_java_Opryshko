
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
