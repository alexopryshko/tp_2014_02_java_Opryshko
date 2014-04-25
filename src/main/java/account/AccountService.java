package account;
import database.SQLConnector;
import database.UserDAO;
import helper.TimeHelper;
import messageSystem.Address;
import messageSystem.Customers;
import messageSystem.MessageSystem;
import messageSystem.Subscriber;
import org.mindrot.jbcrypt.BCrypt;
import resourcesSystem.Resource;
import resourcesSystem.ResourceFactory;
import resourcesSystem.resources.DatabaseConf;


public class AccountService implements Subscriber, Runnable {

    private Address address;
    private MessageSystem messageSystem;
    private UserDAO userDAO; //только connection, а DAO создавать каждый раз

    public AccountService(MessageSystem messageSystem) {
        DatabaseConf databaseConf = (DatabaseConf) ResourceFactory.instance().getResource("data/databaseConf.xml");
        userDAO = new UserDAO(new SQLConnector(
                databaseConf.getHost(),
                databaseConf.getPort(),
                databaseConf.getName(),
                databaseConf.getLogin(),
                databaseConf.getPassword()
        ));
        this.messageSystem = messageSystem;
        this.address = new Address();
        messageSystem.addService(this);
    }


    public long getUserID(String username, String password) {
        Integer temp = address.hashCode();
        System.out.append(temp.toString());

        TimeHelper.sleep(2000);
        if (userDAO.isRegistered(username, password)) {
            return userDAO.getID(username);
        }
        else {
            return 0;
        }
    }

    public boolean userRegistration(String login, String password) {
        Integer temp = address.hashCode();
        System.out.append(temp.toString());

        TimeHelper.sleep(2000);
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));
        return userDAO.addNewUser(login, hashed);
    }

    public MessageSystem getMessageSystem(){
        return messageSystem;
    }

    @Override
    public void run(){
        while(true){
            messageSystem.execForSubscriber(this);
            TimeHelper.sleep(10);
        }
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public Customers getCustomers() {
        return Customers.ACCOUNTSERVICE;
    }


}
