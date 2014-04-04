package account;
import database.SQLConnector;
import database.UserDAO;
import helper.TimeHelper;
import messageSystem.MessageSystem;
import messageSystem.Subscriber;
import org.mindrot.jbcrypt.BCrypt;


public class AccountService implements Subscriber, Runnable {

    private MessageSystem messageSystem;
    private UserDAO userDAO;

    public AccountService(MessageSystem messageSystem) {
        userDAO = new UserDAO(new SQLConnector());
        this.messageSystem = messageSystem;
        messageSystem.addService(this);
    }

    public long getUserID(String username, String password) {
        TimeHelper.sleep(2000);
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return 0;
        }
        if (userDAO.isRegistered(username, password)) {
            return userDAO.getID(username);
        }
        else {
            return 0;
        }
    }

    public boolean userRegistration(String login, String password) {
        TimeHelper.sleep(2000);
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));
        return userDAO.addNewUser(login, hashed);
    }

    public void run(){
        while(true){
            messageSystem.execForSubscriber(this);
            TimeHelper.sleep(10);
        }
    }

    public Class getAddress() {
        return AccountService.class;
    }

    public MessageSystem getMessageSystem(){
        return messageSystem;
    }
}
