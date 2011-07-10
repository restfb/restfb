import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.restfb.DefaultLegacyFacebookClient;
import com.restfb.Facebook;
import com.restfb.LegacyFacebookClient;
import com.restfb.Parameter;

public class StreamPublishExample {
  // You need to provide your access token here.
  // Instructions are available on http://restfb.com.
  private static final String MY_ACCESS_TOKEN = "";

  public static void main(String[] args) {
    LegacyFacebookClient facebookClient = new DefaultLegacyFacebookClient(MY_ACCESS_TOKEN);

    ActionLink category = new ActionLink();
    category.href = "http://bit.ly/KYbaN";
    category.text = "humor";

    Properties properties = new Properties();
    properties.category = category;
    properties.ratings = "5 stars";

    Medium medium = new Medium();
    medium.href = "http://bit.ly/187gO1";
    medium.src = "http://bit.ly/GaTlC";
    medium.type = "image";

    Attachment attachment = new Attachment();
    attachment.name = "i'm bursting with joy";
    attachment.href = "http://bit.ly/187gO1";
    attachment.caption = "{*actor*} rated the lolcat 5 stars";
    attachment.description = "a funny looking cat";
    attachment.properties = properties;
    attachment.media = Collections.singletonList(medium);

    // Send the request to Facebook.
    // We specify the session key to use to make the call, the fact that we're
    // expecting a String response, and the attachment (defined above).
    String postId =
        facebookClient.execute("stream.publish", String.class,
          Parameter.with("attachment", attachment));

    System.out.println("Post ID is " + postId);
  }

  static class ActionLink {
    @Facebook
    String text;

    @Facebook
    String href;
  }

  static class Medium {
    @Facebook
    String type;

    @Facebook
    String src;

    @Facebook
    String href;
  }

  static class Properties {
    @Facebook
    ActionLink category;

    @Facebook
    String ratings;
  }

  static class Attachment {
    @Facebook
    String name;

    @Facebook
    String href;

    @Facebook
    String caption;

    @Facebook
    String description;

    @Facebook
    Properties properties;

    @Facebook
    List<Medium> media;
  }
}