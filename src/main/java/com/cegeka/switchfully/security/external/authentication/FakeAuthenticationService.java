package com.cegeka.switchfully.security.external.authentication;

import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class FakeAuthenticationService {

    private List<ExternalAuthentication> externalAuthentications = newArrayList(
            ExternalAuthentication.externalAuthenticaton().withUsername("ZWANETTA").withPassword("WORST").withRoles(newArrayList("CIVILIAN")),
            ExternalAuthentication.externalAuthenticaton().withUsername("JMILLER").withPassword("THANKS").withRoles(newArrayList("PRIVATE")),
            ExternalAuthentication.externalAuthenticaton().withUsername("UNCLE").withPassword("SAM").withRoles(newArrayList("HUMAN_RELATIONSHIPS")),
            ExternalAuthentication.externalAuthenticaton().withUsername("GENNY").withPassword("RALLY").withRoles(newArrayList("GENERAL"))
    );

    public ExternalAuthentication getUser(String username, String password) {
        return externalAuthentications.stream()
                .filter(externalAuthentication -> externalAuthentication.getUsername().equals(username))
                .filter(externalAuthentication -> externalAuthentication.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
