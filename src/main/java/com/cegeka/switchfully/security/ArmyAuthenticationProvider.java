package com.cegeka.switchfully.security;

import com.cegeka.switchfully.security.external.authentication.ExternalAuthentication;
import com.cegeka.switchfully.security.external.authentication.FakeAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Service
public class ArmyAuthenticationProvider implements AuthenticationProvider {

  @Autowired
  FakeAuthenticationService authenticationService;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    ExternalAuthentication externalAuthentication = authenticationService.getUser(authentication.getName(), authentication.getCredentials().toString());
    return ofNullable(externalAuthentication)
        .map(auth -> authenticateAuthObject(authentication, externalAuthentication))
        .orElseThrow(() -> new BadCredentialsException("Access denied"));
  }

  private Authentication authenticateAuthObject(Authentication authentication, ExternalAuthentication externalAuthentication) {
    return new UsernamePasswordAuthenticationToken(
        authentication.getPrincipal(),
        authentication.getCredentials(),
        externalAuthentication.getRoles().stream()
            .map(SimpleGrantedAuthority::new)
            .collect(toList()));
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
  }
}
