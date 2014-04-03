package messageSystem;
import frontend.Frontend;

public class MsgUpdateUserID extends MessageToFrontend {

    private String sessionId;
    private Long id;

    public MsgUpdateUserID(Address from, Address to, String sessionId, Long id) {
        super(from, to);
        this.sessionId = sessionId;
        this.id = id;
    }

    void exec(Frontend frontend) {
        frontend.setId(sessionId, id);
    }
}
