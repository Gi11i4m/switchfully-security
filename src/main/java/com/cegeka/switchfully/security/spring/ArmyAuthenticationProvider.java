package com.cegeka.switchfully.security.spring;

import com.cegeka.switchfully.security.external.authentication.ArmyFeature;
import com.cegeka.switchfully.security.external.authentication.ArmyRole;
import com.cegeka.switchfully.security.external.authentication.FakeAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static java.util.Collections.disjoint;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;

@Component
public class ArmyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private FakeAuthenticationService fakeAuthenticationService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return ofNullable(fakeAuthenticationService.getUser(authentication.getPrincipal().toString(), authentication.getCredentials().toString()))
            .map(user -> new ArmyAuthentication(user.getUsername(), user.getPassword(), mapRolesToFeaturesFor(user.getRoles())))
            .orElseThrow(() -> new BadCredentialsException("BAD BAD NOT GOOD"));
    }

    private List<String> mapRolesToFeaturesFor(Collection<String> roles) {
        List<ArmyRole> rolesOfUser = roles.stream()
            .map(ArmyRole::valueOf)
            .collect(toList());
        return of(ArmyFeature.values())
            .filter(feature -> !disjoint(feature.getRoles(), rolesOfUser))
            .map(Enum::toString)
            .collect(toList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
