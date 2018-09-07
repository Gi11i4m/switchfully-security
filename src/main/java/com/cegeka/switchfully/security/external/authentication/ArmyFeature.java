package com.cegeka.switchfully.security.external.authentication;

import java.util.List;

import static com.cegeka.switchfully.security.external.authentication.ArmyRole.*;
import static java.util.Arrays.asList;

public enum ArmyFeature {
  MANAGE_SOLDIERS(HUMAN_RELATIONSHIPS),
  NUKE_THE_WORLD(GENERAL),
  ACCESS_SENSITIVE_INFO(GENERAL, PRIVATE),
  ENROLL(CIVILIAN);

  public List<ArmyRole> getRoles() {
    return asList(roles);
  }

  private ArmyRole[] roles;

  ArmyFeature(ArmyRole... roles) {
    this.roles = roles;
  }
}
