package account;


public class UserSession {

    private String name;
    private String sessionId;
    private Long userId;
    private RegistrationStatus registrationStatus;

    public UserSession(String sessionId, String name) {
        this.sessionId = sessionId;
        this.name = name;
        this.registrationStatus = RegistrationStatus.NOT_REGISTRATED; //сделать статус через enum
    }

    public String getName() {
        return name;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public RegistrationStatus getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(RegistrationStatus registrationStatus) {
        this.registrationStatus = registrationStatus;
    }
}
