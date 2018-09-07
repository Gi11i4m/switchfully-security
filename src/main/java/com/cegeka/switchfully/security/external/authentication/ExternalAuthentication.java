package com.cegeka.switchfully.security.external.authentication;

import java.util.List;

public class ExternalAuthentication {
    private String username;
    private String password;
    private List<String> roles;

    public static ExternalAuthentication externalAuthenticaton(){
        return new ExternalAuthentication();
    }

    public ExternalAuthentication withUsername(String username) {
        this.username = username;
        return this;
    }

    public ExternalAuthentication withPassword(String password) {
        this.password = password;
        return this;
    }

    public ExternalAuthentication withRoles(List<String> roles) {
        this.roles = roles;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getRoles() {
        return roles;
    }
}
