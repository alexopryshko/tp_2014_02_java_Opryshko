package messageSystem;
import account.AccountService;

public class MsgAddNewUser extends MessageToAccountService{
    private String username;
    private String password;
    private String sessionId;

    public MsgAddNewUser(Class from, Class to, String name, String password, String sessionId) {
        super(from, to);
        this.username= name;
        this.password = password;
        this.sessionId = sessionId;
    }

    void exec(AccountService accountService) {
        boolean result = accountService.userRegistration(username, password);
        accountService.getMessageSystem().sendMessage(new MsgRegistrationStatus(getTo(), getFrom(), sessionId, (result) ? 1 : 0));
    }
}
