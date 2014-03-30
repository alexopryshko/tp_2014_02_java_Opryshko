package messageSystem;
import account.AccountService;

public class MsgGetUserID extends MessageToAccountService {
    private String username;
    private String password;
    private String sessionId;

    public MsgGetUserID(Address from, Address to, String name, String password, String sessionId) {
        super(from, to);
        this.username= name;
        this.password = password;
        this.sessionId = sessionId;
    }

    void exec(AccountService accountService) {
        Long id = accountService.getUserID(username, password);
        accountService.getMessageSystem().sendMessage(new MsgUpdateUserID(getTo(), getFrom(), sessionId, id));
    }
}
