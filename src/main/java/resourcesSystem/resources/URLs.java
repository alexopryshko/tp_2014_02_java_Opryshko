package resourcesSystem.resources;

import resourcesSystem.Resource;

public class URLs implements Resource {
    private String MAIN;
    private String TIME;
    private String REGISTRATION;
    private String LOGIN;
    private String ERROR;

    public final String getMAIN() {
        return MAIN;
    }

    public String getTIME() {
        return TIME;
    }

    public String getREGISTRATION() {
        return REGISTRATION;
    }

    public String getLOGIN() {
        return LOGIN;
    }

    public String getERROR() {
        return ERROR;
    }
}
