import java.util.List;

import com.restfb.DefaultFacebookClient;
import com.restfb.Facebook;
import com.restfb.FacebookClient;
import com.restfb.FacebookException;
import com.restfb.Parameter;

public class Example {
  private static final String API_KEY = "403a643asd785asdf500137c659";
  private static final String SECRET_KEY = "c53bebas96sd6676986aae7af77d06";

  public static void main(String[] args) throws FacebookException {
    String sessionKey = "3.rEsn5taasdf3aOs1A__.3600.1264611600-1241234115";
    FacebookClient facebookClient =
        new DefaultFacebookClient(API_KEY, SECRET_KEY);

    // Simple API call for a single result
    Long uid =
        facebookClient.execute("users.getLoggedInUser", sessionKey, Long.class);

    System.out.println("My UID is " + uid);

    // API call with result mapped to a list of classes we've defined.
    // Note that you can pass in an arbitrary number of parameters - here we
    // send along the query as well as the "give me HTTPS URLs" flag
    String query =
        "SELECT name, pic_big, affiliations FROM user "
            + "WHERE uid IN (SELECT uid2 FROM friend WHERE uid1=" + uid + ")";

    List<User> users =
        facebookClient.executeForList("fql.query", sessionKey, User.class,
          Parameter.with("query", query), Parameter.with(
            "return_ssl_resources", "true"));

    System.out.println("My friends and their affiliations:");

    for (User user : users)
      System.out.println(user);
  }

  public static class User {
    @Facebook
    String name;

    @Facebook("pic_big")
    String pictureUrl;

    @Facebook(contains = Affiliation.class)
    List<Affiliation> affiliations;

    public String toString() {
      return String.format("%s (image URL is %s, affiliations: %s)", name,
        pictureUrl, affiliations);
    }
  }

  public static class Affiliation {
    @Facebook
    String name;

    @Facebook
    String type;

    public String toString() {
      return String.format("%s (%s)", name, type);
    }
  }
}