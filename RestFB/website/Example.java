import java.util.List;

import com.restfb.DefaultFacebookClient;
import com.restfb.Facebook;
import com.restfb.FacebookClient;
import com.restfb.FacebookException;
import com.restfb.Parameter;

public class Example {
  // You need to provide your own API and Secret keys here
  private static final String API_KEY = "";
  private static final String SECRET_KEY = "";

  public static void main(String[] args) throws FacebookException {
    // You need to provide your own Session key.
    String sessionKey = "";

    FacebookClient facebookClient =
        new DefaultFacebookClient(API_KEY, SECRET_KEY);

    // Last parameter specifies that this API call's result should be returned
    // to us as a Long value.
    Long uid =
        facebookClient.execute("users.getLoggedInUser", sessionKey, Long.class);

    System.out.println("My UID is " + uid);

    // FQL query which asks Facebook for your friends' names, profile picture
    // URLs, and network affiliations
    String query =
        "SELECT name, pic_big, affiliations FROM user "
            + "WHERE uid IN (SELECT uid2 FROM friend WHERE uid1=" + uid + ")";

    // Executes an API call with result mapped to a list of classes we've
    // defined. Note that you can pass in an arbitrary number of Parameters -
    // here we send along the query as well as the "give me HTTPS URLs" flag
    List<User> users =
        facebookClient.executeForList("fql.query", sessionKey, User.class,
          Parameter.with("query", query), Parameter.with(
            "return_ssl_resources", "true"));

    System.out.println("My friends and their affiliations:");

    for (User user : users)
      System.out.println(user);
  }

  public static class User {
    // By default, assumes JSON attribute name is the same as the Java field
    // name
    @Facebook
    String name;

    // If your Java field name is different than the JSON attribute name, just
    // specify the JSON attribute name
    @Facebook("pic_big")
    String pictureUrl;

    // Java doesn't remember generic type information at runtime due to type
    // erasure. So, for a List type mapping, you have to help out by specifying
    // what kind of type is contained in the List (Affiliation, in this case)
    @Facebook(contains = Affiliation.class)
    List<Affiliation> affiliations;

    public String toString() {
      return String.format("Name: %s\nProfile Image URL: %s\nAffiliations: %s",
        name, pictureUrl, affiliations);
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