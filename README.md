[![Stories in Ready](https://badge.waffle.io/restfb/restfb.png?label=ready&title=Ready)](https://waffle.io/restfb/restfb)
[![Build Status](https://travis-ci.org/restfb/restfb.svg)](https://travis-ci.org/restfb/restfb)
[![Join the chat at https://gitter.im/restfb/restfb](https://badges.gitter.im/restfb/restfb.svg)](https://gitter.im/restfb/restfb?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

# RestFB (http://restfb.com)
## What it is

RestFB is a pure Java Facebook Graph API client with no external dependencies.

It was created by [Mark Allen](http://revetkn.com) and is maintained by [Norbert Bartels](http://www.phpmonkeys.de/) along with a worldwide team of contributors.

## Licensing

RestFB uses other open-source software - see the `LICENSE.*.txt` files. 
RestFB itself is open source software released under the terms of the MIT License.

## Installation

RestFB is a single JAR - just drop it into your app and you're ready to go. Download it from Maven Central:

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.restfb/restfb/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.restfb/restfb/)

## Building it Yourself

Just type

    ant dist
    
...and you're done.

## Usage examples

The following paragraphs show only a subset of the possibilities you have, if you use RestFB. To get a complete 
overview you should check the examples in the [documentation](http://restfb.com/documentation/). The code samples there
are commented and have a lot of additional information that are very useful.

### Initialization 
`DefaultFacebookClient` is the `FacebookClient` implementation
that ships with RestFB. You can customize it by passing in
custom `JsonMapper` and `WebRequestor` implementations, or simply
write your own `FacebookClient` instead for maximum control.

```java
FacebookClient facebookClient = new DefaultFacebookClient(MY_ACCESS_TOKEN, Version.LATEST);

// Get added security by using your app secret:
FacebookClient facebookClient = 
       new DefaultFacebookClient(MY_ACCESS_TOKEN, MY_APP_SECRET, Version.VERSION_2_8);
```

### Fetching Single Objects

[see RestFB documentation](http://restfb.com/documentation/#fetching-single-objects)

For all API calls, you need to tell RestFB how to turn the JSON
returned by Facebook into Java objects. In this case, the data
we get back should be mapped to the `User` and `Page` types, respectively.
You can write your own types too!

```java
User user = facebookClient.fetchObject("me", User.class);
Page page = facebookClient.fetchObject("cocacola", Page.class);

out.println("User name: " + user.getName());
out.println("Page likes: " + page.getLikes());
```

### Fetching Connections
[see RestFB documentation](http://restfb.com/documentation/#fetching-connections)

Connection is the name of an object list. You'll get a connection if you
try to fetch your feed for example. As you can see in this example, you can
simple iterate over the elements or access the contained data directly.

```java
Connection<Post> myFeed = facebookClient.fetchConnection("me/feed", Post.class);

out.println("First item in my feed: " + myFeed.getData().get(0));

// Connections support paging and are iterable
for (List<Post> myFeedConnectionPage : myFeed)
  for (Post post : myFeedConnectionPage)
    out.println("Post: " + post);
```

### Passing Parameters
[see RestFB documentation](http://restfb.com/documentation/#advanced-usage-passing-parameters)

You can pass along any parameters you'd like to the Facebook endpoint. 

```java
Date oneWeekAgo = new Date(currentTimeMillis() - 1000L * 60L * 60L * 24L * 7L);

Connection<Post> filteredFeed = facebookClient.fetchConnection("me/feed", Post.class,
  Parameter.with("limit", 3), Parameter.with("until", "yesterday"),
    Parameter.with("since", oneWeekAgo));

out.println("Filtered feed count: " + filteredFeed.getData().size());
```

### Selecting Specific Fields
[see RestFB documentation](http://restfb.com/documentation/#advanced-usage-selecting-fields)

With Graph API 2.4 you only get a subset of the possible fields prefilled. But you may
define which fields you really need.

```java
User user = facebookClient.fetchObject("me", User.class,
  Parameter.with("fields", "id,name,last_name"));

out.println("User name: " + user.getName());
```

### Getting Any Kind of Data as a JSON Object
[see RestFB documentation](http://restfb.com/documentation/#creating-json-objects)

Sometimes you can't know field names at compile time
so the `@Facebook` annotation can't be used.
Or maybe you'd like full control over the data that gets returned.
Either way, RestFB has you covered. Just map any API call to `JsonObject`.

```java
// Here's how to fetch a single object

JsonObject btaylor = facebookClient.fetchObject("btaylor", JsonObject.class);
out.println(btaylor.getString("name"));

// Here's how to fetch a connection

JsonObject photosConnection = facebookClient.fetchObject("me/photos", JsonObject.class);
String firstPhotoUrl = photosConnection.getJsonArray("data").getJsonObject(0).getString("source");
out.println(firstPhotoUrl);
```

### Publishing a Message
[see RestFB documentation](http://restfb.com/documentation/#publishing-message-event)

```java
// Publishing a simple message.
// FacebookType represents any Facebook Graph Object that has an ID property.

FacebookType publishMessageResponse =
  facebookClient.publish("me/feed", FacebookType.class,
    Parameter.with("message", "RestFB test"));

out.println("Published message ID: " + publishMessageResponse.getId());
```

### Publishing a Photo
[see RestFB documentation](http://restfb.com/documentation/#publishing-photo)

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

### Deleting
[see RestFB documentation](http://restfb.com/documentation/#deleting)

```java
Boolean deleted = facebookClient.deleteObject("some object ID");
out.println("Deleted object? " + deleted);
```

### Map Your Own Types
[see RestFB documentation](http://restfb.com/documentation/#json-mapping-rules)

```java
public class MyClass {
  @Facebook
  String name;

  @Facebook
  BigDecimal value;

  // If a Facebook field doesn't match your field's name, specify it explicitly

  @Facebook("lots_of_numbers")
  List<Integer> lotsOfNumbers;
  
  // You can annotate methods with @JsonMappingCompleted to perform
  // post-mapping operations.
  //
  // This is useful if you want to massage the data FB returns.
  
  @JsonMappingCompleted
  void allDone(JsonMapper jsonMapper) {   
    if(lotsOfNumbers.size() == 0)
      throw new IllegalStateException("I was expecting more numbers!");
  }
}
```