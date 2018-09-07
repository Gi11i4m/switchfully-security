package com.cegeka.switchfully.security;

import com.cegeka.switchfully.security.external.authentication.FakeAuthenticationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.jaas.JaasAuthenticationToken;
import org.springframework.security.core.Authentication;

import static com.cegeka.switchfully.security.external.authentication.ExternalAuthentication.externalAuthenticaton;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArmyAuthenticationProviderTest {

  @Mock
  private FakeAuthenticationService fakeAuthenticationService;

  @InjectMocks
  private ArmyAuthenticationProvider authenticationProvider;

  @Before
  public void setup() {
    when(fakeAuthenticationService.getUser("ZWANETTA", "WORST"))
        .thenReturn(externalAuthenticaton()
            .withUsername("ZWANETTA")
            .withPassword("WORST")
            .withRoles(singletonList("CIVILIAN")));
  }

  @Test
  public void authenticate_CorrectCombo_ShouldReturnAuthenticationObject() {
    Authentication authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken("ZWANETTA", "WORST"));

    assertThat(authentication.isAuthenticated()).isTrue();
  }

  @Test
  public void authenticate_IncorrectCombo_ShouldReturnNull() {
    Authentication authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken("ZWANETTA", "BEST"));

    assertThat(authentication).isNull();
  }

  @Test
  public void supports_SupportedAuthenticationType_ReturnsTrue() {
    boolean isSupported = authenticationProvider.supports(UsernamePasswordAuthenticationToken.class);

    assertThat(isSupported).isTrue();
  }

  @Test
  public void supports_SubtypeOfSupportedAuthenticationType_ReturnsTrue() {
    boolean isSupported = authenticationProvider.supports(JaasAuthenticationToken.class);

    assertThat(isSupported).isTrue();
  }

  @Test
  public void supports_UnSupportedAuthenticationType_ReturnsFalse() {
    boolean isSupported = authenticationProvider.supports(RememberMeAuthenticationToken.class);

    assertThat(isSupported).isFalse();

  }
}