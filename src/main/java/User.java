/**
 * Created by alexander on 19.02.14.
 */
public class User {
    private long UserId;
    private String Username;
    private String Password;

    public User() {
        UserId = 0;
        Username = null;
        Password = null;
    }
    public User(long id, String username, String password) {
        UserId = id;
        Username = username;
        Password = password;
    }
    //get method
    public long getID() { return UserId; }
    public String getUsername () { return Username; }
    public String getPassword () { return Password; }
    //set method
    public void setID(long id) { UserId = id; }
    public void setUsername (String username) { Username = username; }
    public void setPassword (String password) { Password = password; }
}
