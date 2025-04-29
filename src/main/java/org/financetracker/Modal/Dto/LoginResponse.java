package org.financetracker.Modal.Dto;

import java.util.UUID;

public class LoginResponse {
    private String userID;
    private String token;

    public LoginResponse(UUID id, String token) {
        this.userID = id != null ? id.toString() : null;
        this.token = token;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
