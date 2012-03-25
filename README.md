# RestFB (http://restfb.com)
## What it is

RestFB is a pure Java Facebook Graph API and Old REST API client with no external dependencies.

## Licensing

RestFB uses other open-source software - see the "licensing" directory. 
RestFB itself is open source software released under the terms of the MIT License.

## Installation

RestFB is a single JAR - just drop `restfb-1.6.9.jar` into your app and you're ready to go.

Or, if you're using [Maven](http://maven.apache.org/), you can add RestFB to your project like this:

```xml
<dependency>
  <groupId>com.restfb</groupId>
  <artifactId>restfb</artifactId>
  <version>1.6.9</version>
</dependency>
```

## Building it Yourself

Just type

    ant dist
    
...and you're done.

## Usage examples

Please see http://restfb.com for complete documentation.

#### Initialization 

```java
// DefaultFacebookClient is the FacebookClient implementation
// that ships with RestFB. You can customize it by passing in
// custom JsonMapper and WebRequestor implementations, or simply
// write your own FacebookClient instead for maximum control.

FacebookClient facebookClient = new DefaultFacebookClient(MY_ACCESS_TOKEN);

// It's also possible to create a client that can only access
// publicly-visible data - no access token required. 
// Note that many of the examples below will not work unless you supply an access token! 

FacebookClient publicOnlyFacebookClient = new DefaultFacebookClient();
```

#### Fetching Single Objects

```java
// For all API calls, you need to tell RestFB how to turn the JSON
// returned by Facebook into Java objects.  In this case, the data
// we get back should be mapped to the User and Page types, respectively.
// You can write your own types too!

User user = facebookClient.fetchObject("me", User.class);
Page page = facebookClient.fetchObject("cocacola", Page.class);

out.println("User name: " + user.getName());
out.println("Page likes: " + page.getLikes());
```

#### Fetching Multiple Objects in One Call 

```java
FetchObjectsResults fetchObjectsResults =
  facebookClient.fetchObjects(Arrays.asList("me", "cocacola"), FetchObjectsResults.class);

out.println("User name: " + fetchObjectsResults.me.getName());
out.println("Page likes: " + fetchObjectsResults.page.getLikes());

...

// Holds results from a "fetchObjects" call.
// You need to write this class yourself!

public class FetchObjectsResults {
  @Facebook
  User me;

  // If the Facebook property name doesn't match
  // the Java field name, specify the Facebook field name in the annotation.

  @Facebook("cocacola")
  Page page;
}
```

#### Fetching Connections

```java
Connection<User> myFriends = facebookClient.fetchConnection("me/friends", User.class);
Connection<Post> myFeed = facebookClient.fetchConnection("me/feed", Post.class);

out.println("Count of my friends: " + myFriends.getData().size());
out.println("First item in my feed: " + myFeed.getData().get(0));

// Connections support paging and are iterable

for (List<Post> myFeedConnectionPage : myFeed)
  for (Post post : myFeedConnectionPage)
    out.println("Post: " + post);
```

#### Searching

```java
// Searching is just a special case of fetching Connections -
// all you have to do is pass along a few extra parameters.

Connection<Post> publicSearch =
  facebookClient.fetchConnection("search", Post.class,
    Parameter.with("q", "watermelon"), Parameter.with("type", "post"));

Connection<User> targetedSearch =
  facebookClient.fetchConnection("me/home", User.class,
    Parameter.with("q", "Mark"), Parameter.with("type", "user"));

out.println("Public search: " + publicSearch.getData().get(0).getMessage());
out.println("Posts on my wall by friends named Mark: " + targetedSearch.getData().size());
```

#### Fetching Insights

```java
// Fetching Insights data is as simple as fetching a Connection

Connection<Insight> insights = facebookClient.fetchConnection("PAGE_ID/insights", Insight.class);

for (Insight insight : insights.getData())
  out.println(insight.getName());
```

#### Executing FQL Queries

```java
String query = "SELECT uid, name FROM user WHERE uid=220439 or uid=7901103";
List<FqlUser> users = facebookClient.executeQuery(query, FqlUser.class);

out.println("Users: " + users);

...

// Holds results from an "executeQuery" call.
// You need to write this class yourself!
// Be aware that FQL fields don't always map to Graph API Object fields.

public class FqlUser {
  @Facebook
  String uid;
  
  @Facebook
  String name;

  @Override
  public String toString() {
    return String.format("%s (%s)", name, uid);
  }
}
```

#### Metadata/Introspection

```java
// You can specify metadata=1 for many calls, not just this one.
// See the Facebook Graph API documentation for more details. 

User userWithMetadata =
  facebookClient.fetchObject("me", User.class, Parameter.with("metadata", 1));

out.println("User metadata: has albums? "
  + userWithMetadata.getMetadata().getConnections().hasAlbums());
```

#### Passing Parameters

```java
// You can pass along any parameters you'd like to the Facebook endpoint.

Date oneWeekAgo = new Date(currentTimeMillis() - 1000L * 60L * 60L * 24L * 7L);

Connection<Post> filteredFeed = facebookClient.fetchConnection("me/feed", Post.class,
  Parameter.with("limit", 3), Parameter.with("until", "yesterday"),
    Parameter.with("since", oneWeekAgo));

out.println("Filtered feed count: " + filteredFeed.getData().size());
```

#### Selecting Specific Fields

```java
User user = facebookClient.fetchObject("me", User.class,
  Parameter.with("fields", "id, name"));

out.println("User name: " + user.getName());
```

#### Getting Any Kind of Data as a JSON Object

```java
// Sometimes you can't know field names at compile time
// so the @Facebook annotation can't be used.
// Or maybe you'd like full control over the data that gets returned.
// Either way, RestFB has you covered.  Just map any API call to JsonObject.

// Here's how to fetch a single object

JsonObject btaylor = facebookClient.fetchObject("btaylor", JsonObject.class);
out.println(btaylor.getString("name"));

// Here's how to fetch a connection

JsonObject photosConnection = facebookClient.fetchObject("me/photos", JsonObject.class);
String firstPhotoUrl = photosConnection.getJsonArray("data").getJsonObject(0).getString("source");
out.println(firstPhotoUrl);

// Here's how to handle an FQL query

String query = "SELECT uid, name FROM user WHERE uid=220439 or uid=7901103";
List<JsonObject> queryResults = facebookClient.executeQuery(query, JsonObject.class);
out.println(queryResults.get(0).getString("name"));

// Sometimes it's helpful to use JsonMapper directly if you're working with JsonObjects.

List<String> ids = new ArrayList<String>();
ids.add("btaylor");
ids.add("http://www.imdb.com/title/tt0117500/");

// First, make the API call...

JsonObject results = facebookClient.fetchObjects(ids, JsonObject.class);

// ...then pull out raw JSON data and map each type "by hand".
// Normally your FacebookClient uses a JsonMapper internally, but
// there's nothing stopping you from using it too!

JsonMapper jsonMapper = new DefaultJsonMapper();
User user = jsonMapper.toJavaObject(results.getString("btaylor"), User.class);
Url url = jsonMapper.toJavaObject(results.getString("http://restfb.com"), Url.class);

out.println("User is " + user);
out.println("URL is " + url);
```

#### Publishing a Message and Event

```java
// Publishing a simple message.
// FacebookType represents any Facebook Graph Object that has an ID property.

FacebookType publishMessageResponse =
  facebookClient.publish("me/feed", FacebookType.class,
    Parameter.with("message", "RestFB test"));

out.println("Published message ID: " + publishMessageResponse.getId());

// Publishing an event

Date tomorrow = new Date(currentTimeMillis() + 1000L * 60L * 60L * 24L);
Date twoDaysFromNow = new Date(currentTimeMillis() + 1000L * 60L * 60L * 48L);

FacebookType publishEventResponse = facebookClient.publish("me/events", FacebookType.class,
  Parameter.with("name", "Party"), Parameter.with("start_time", tomorrow),
    Parameter.with("end_time", twoDaysFromNow));

out.println("Published event ID: " + publishEventResponse.getId());
```

#### Publishing a Photo

```java
// Publishing an image to a photo album is easy!
// Just specify the image you'd like to upload and RestFB will handle it from there.

FacebookType publishPhotoResponse = facebookClient.publish("me/photos", FacebookType.class,
  BinaryAttachment.with("cat.png", getClass().getResourceAsStream("/cat.png")),
  Parameter.with("message", "Test cat"));

out.println("Published photo ID: " + publishPhotoResponse.getId());

// Publishing a video works the same way.

facebookClient.publish("me/videos", FacebookType.class,
  BinaryAttachment.with("cat.mov", getClass().getResourceAsStream("/cat.mov")),
  Parameter.with("message", "Test cat"));
```

#### Publishing a Checkin

```java
Map<String, String> coordinates = new HashMap<String, String>();
coordinates.put("latitude", "37.06");
coordinates.put("longitude", "-95.67");
                        
FacebookType publishCheckinResponse = facebookClient.publish("me/checkins",
  FacebookType.class, Parameter.with("message", "I'm here!"),
    Parameter.with("coordinates", coordinates), Parameter.with("place", 1234)));

out.println("Published checkin ID: " + publishCheckinResponse.getId());
```

#### Deleting

```java
Boolean deleted = facebookClient.deleteObject("some object ID");
out.println("Deleted object? " + deleted);
```

#### Using the Batch Request API

```java
// The Batch API is great if you have multiple operations you'd like to
// perform in one server trip. Let's build a batch with three GET requests and
// one POST request here:

BatchRequest meRequest = new BatchRequestBuilder("me").build();
BatchRequest badRequest = new BatchRequestBuilder("this-is-a-bad-request/xxx").build();
BatchRequest m83musicRequest = new BatchRequestBuilder("m83music/feed")
  .parameters(Parameter.with("limit", 5)).build();
BatchRequest postRequest = new BatchRequestBuilder("me/feed")
  .method("POST")
  .body(Parameter.with("message", "Testing!")).build();

// ...and execute the batch.

List<BatchResponse> batchResponses =
  facebookClient.executeBatch(meRequest, badRequest, m83musicRequest, postRequest);

// Responses are ordered to match up with their corresponding requests.

BatchResponse meResponse = batchResponses.get(0);
BatchResponse badResponse = batchResponses.get(1);
BatchResponse m83musicResponse = batchResponses.get(2);
BatchResponse postResponse = batchResponses.get(3);

// Since batches can have heterogenous response types, it's up to you
// to parse the JSON into Java objects yourself. Luckily RestFB has some built-in
// support to help you with this.

JsonMapper jsonMapper = new DefaultJsonMapper();

// Here we marshal to the built-in User type.

User me = jsonMapper.toJavaObject(meResponse.getBody(), User.class);
out.println(me);

// To detect errors, check the HTTP response code.

if(badResponse.getCode() != 200)
  out.println("Batch request failed: " + badResponse);

// You can pull out connection data...

out.println("M83's feed follows");

Connection<Post> m83musicPosts =
  new Connection<Post>(facebookClient, m83musicResponse.getBody(), Post.class);

for (List<Post> m83musicPostsConnectionPage : m83musicPosts)
  for (Post post : m83musicPostsConnectionPage)
    out.println(post);

// ...or do whatever you'd like with the raw JSON.

out.println(postResponse.getBody());
```

#### Including Binary Attachments Using the Batch Request API

```java
// Per the FB Batch API documentation, attached_files is a comma-separated list
// of attachment names to include in the API call.
// RestFB will use the filename provided to your BinaryAttachment minus the file
// extension as the name of the attachment.
// For example, "cat-pic.png" must be referenced here as "cat-pic". 

List<BatchRequest> batchRequests = Arrays.asList(
  new BatchRequestBuilder("me/photos").attachedFiles("cat-pic").build(),
  new BatchRequestBuilder("me/videos")
    .attachedFiles("cat-vid, cat-vid-2")
    .body(Parameter.with("message", "This cat is hilarious"))
    .build());

// Define the list of attachments to include in the batch.

List<BinaryAttachment> binaryAttachments = Arrays.asList(
  BinaryAttachment.with("cat-pic.png", getClass().getResourceAsStream("/cat-pic.png")),
  BinaryAttachment.with("cat-vid.mov", getClass().getResourceAsStream("/cat-vid.mov")),
  BinaryAttachment.with("cat-vid-2.mov", getClass().getResourceAsStream("/cat-vid-2.mov")));

// Finally, execute the batch.

facebookClient.executeBatch(batchRequests, binaryAttachments);
```

#### Unit Testing

```java
FacebookClient facebookClient = new DefaultFacebookClient(MY_ACCESS_TOKEN,  

  // A one-off DefaultWebRequestor for testing that returns a hardcoded JSON
  // object instead of hitting the Facebook API endpoint URL

  new DefaultWebRequestor() {
    @Override
    public Response executeGet(String url) throws IOException {
      return new Response(HttpURLConnection.HTTP_OK,
        "{'id':'123456','name':'Test Person'}");
    }
  }, new DefaultJsonMapper());

// Make an API request using the mocked WebRequestor

User user = facebookClient.fetchObject("ignored", User.class); 

// Make sure we got what we were expecting

assert "123456".equals(user.getId());
assert "Test Person".equals(user.getName());
```