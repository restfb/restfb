package com.restfb.types;

import com.restfb.Facebook;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the
 * <a href="https://developers.facebook.com/docs/graph-api/reference/user/permissions/"> User Permission API type</a>.
 *
 * @author Alexander Nenkov
 */
public class UserPermission extends AbstractFacebookType {

  @Getter
  @Setter
  @Facebook
  private String permission;

  @Getter
  @Setter
  @Facebook
  private String status;
}
