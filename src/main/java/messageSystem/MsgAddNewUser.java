package messageSystem;
import account.AccountService;
import account.RegistrationStatus;

public class MsgAddNewUser extends MessageToAccountService{

    private String username;
    private String password;
    private String sessionId;

    public MsgAddNewUser(Address from, Address to, String name, String password, String sessionId) {
        super(from, to);
        this.username= name;
        this.password = password;
        this.sessionId = sessionId;
    }

    @Override
    void exec(AccountService accountService) {
        boolean result = accountService.userRegistration(username, password);
        accountService.getMessageSystem().sendMessage(new MsgRegistrationStatus(
                getTo(),
                getFrom(),
                sessionId,
                (result) ? RegistrationStatus.SUCCESS : RegistrationStatus.USER_ALREADY_EXIST));
    }
}
