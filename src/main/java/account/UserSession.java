package account;


public class UserSession {

    private String name;
    private String sessionId;
    private Long userId;
    private Integer registrationStatus;

    public UserSession(String sessionId, String name) {
        this.sessionId = sessionId;
        this.name = name;
        this.registrationStatus = -1;
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

    public Integer getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(Integer registrationStatus) {
        this.registrationStatus = registrationStatus;
    }
}
