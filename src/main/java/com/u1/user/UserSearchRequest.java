package com.u1.user;

import java.util.Objects;

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
        return Objects.isNull(startsWith) ? "" : startsWith;
    }


    public String getEndsWith() {
        return Objects.isNull(endsWith) ? "" : endsWith;
    }

    public String getContains() {
        return Objects.isNull(contains) ? "" : contains;
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
