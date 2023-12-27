package com.u1.user;

public class UserSearchRequest {
    private String startsWith;
    private String endsWith;
    private String contains;

    public UserSearchRequest(String startsWith, String endsWith, String contains) {
        this.startsWith = startsWith;
        this.endsWith = endsWith;
        this.contains = contains;
    }

    public String getStartsWith() {
        return startsWith == null ? "" : startsWith;
    }

    public String getEndsWith() {
        return endsWith == null ? "" : endsWith;
    }

    public String getContains() {
        return contains == null ? "" : contains;
    }

    public void setStartsWith(String startsWith) {
        this.startsWith = startsWith;
    }

    public void setEndsWith(String endsWith) {
        this.endsWith = endsWith;
    }

    public void setContains(String contains) {
        this.contains = contains;
    }
}
