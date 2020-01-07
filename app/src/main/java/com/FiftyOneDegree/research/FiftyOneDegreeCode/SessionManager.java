package com.FiftyOneDegree.research.FiftyOneDegreeCode;

public class SessionManager {

    private String userAgent;
    private static SessionManager instance;

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
