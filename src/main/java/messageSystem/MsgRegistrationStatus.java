package messageSystem;
import account.RegistrationStatus;
import frontend.Frontend;

public class MsgRegistrationStatus extends MessageToFrontend {

    private String sessionID;
    private RegistrationStatus status;

    public MsgRegistrationStatus(Address from, Address to, String sessionID, RegistrationStatus status) {
        super(from, to);
        this.status = status;
        this.sessionID = sessionID;
    }

    @Override
    void exec(Frontend frontend) {
        frontend.setRegistrationStatus(sessionID, status);
    }
}
