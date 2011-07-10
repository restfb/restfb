import java.util.List;

import com.restfb.DefaultLegacyFacebookClient;
import com.restfb.Facebook;
import com.restfb.LegacyFacebookClient;
import com.restfb.Parameter;

public class LegacyExample {
  // You need to provide your access token here.
  // Instructions are available on http://restfb.com.
  private static final String MY_ACCESS_TOKEN = "";

  public static void main(String[] args) {
    LegacyFacebookClient facebookClient = new DefaultLegacyFacebookClient(MY_ACCESS_TOKEN);

    // Last parameter specifies that this API call's result should be returned
    // to us as a Long value.
    Long uid = facebookClient.execute("users.getLoggedInUser", Long.class);

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
        facebookClient.executeForList("fql.query", User.class,
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