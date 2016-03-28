1.21.0 (March 28, 2016)

* Issue #390: `Notification` type added
* Issue #403: Missing fields in `Message Attachment`
* Issue #404: Integration test for posting a image to a group<br>
  Thanks to @kfredy for question
* Issue #405: Missing field `shares` added to `Message` type<br>
  Thanks to @ajeetsk for hint
* Issue #407: Build files updated

1.20.0 (March 4, 2016)

* Issue #377: Check the permissions
* Issue #379: Missing fields in `AppRequest` type
* Issue #380: Missing fields in `Event` type
* Issue #381: Missing fields in `Group` type
* Issue #382: Missing fields in `Message` type
* Issue #383: Missing fields in `Photo` type
* Issue #384: Missing fields in `User` type
* Issue #385: Missing fields in `Page` type
* Issue #388: Missing fields in `Conversation` type
* Issue #389: Missing fields in `PageCallToAction` type
* Issue #392: Deprecated types marked
* Issue #393: Missing fields in `Post` type
* Issue #394 and #395: `PageRatings` are outdated<br>
  Thanks to @anenkov for PR
* Issue #396 and #397: `Comments` misses `can_comment` field<br>
  Thanks to @anenkov for PR
* Issue #398: Missing `parent_id` field in `Post` type<br>
  Thanks to @vido88
* Issue #400: duplicate `type` field in several types
* Issue #401: Webhooks Fields update<br>
  Thanks to @cmmoran

1.19.0 (February 5, 2016)

* Issue #375: "private message page replies" support added
* Issue #369: `Translation` type added
* Issue #370: `PlaceTag` type added
* Issue #371: deprecation information for permissions add<br>
  Thanks to @lomon for hint
* Issue #368: `Milestone` type added
* Issue #373: `User` fields missing<br>
  Thanks to @lomon for hint
* Issue #366: `Application` type missing fields added
* Issue #358: `Payment` type added
* Issue #343: Webhook types added
* Issue #363: example code moved to repository
* Issue #362: waffle.io badge added
* Issue #356: `PageCallToAction` type added

1.18.1 (January 4, 2016)

* Issue #355: copyright year changed to 2016
* Issue #354: changelog syntax changed to markdown

1.18.0 (December 20, 2015)

* Issue #352: access to Facebook HTTP debug headers and HTTP headers in general<br>
  Thanks to Alex Conlin for input
* Issue #351: `last used time` field added to `Page`-type
* Issue #340: Parsing `yyyy-MM-dd produces wrong date
* Issue #338: wrong date format for `Work` type fields `startDate` and `endDate`<br>
  Thanks to @PetrF0X for input
* Issue #336: `Album` type - `fillPicture` method uses wrong type for mapping the image
* Issue #335: Access post likes summary `has_liked` and `can_like` values<br>
  Thanks to @jarylchong for input
* Issue #186: `Page rating` type rewrite<br>
  Thanks to Ron Heft for input

1.17.0 (November 26, 2015)

* Issue #328: Facebook Exception provides access to raw error json<br>
  Thanks to Fran Sanchez for input
* Issue #330: Bugfix: `place` field in `Event`-type changed to `Place`-type<br>
  Thanks to Matthew Gillingham for input
* Issue #331: `album` field and other fields added to `Photo`-type<br>
  Thanks to @tsurelad for input
* Issue #333: `comments` and more fields added to `Album`-type<br>
  Thanks to Paolo for input

1.16.0 (October 22, 2015)

* Issue #317: Marketing API improved
* Issue #324: `message_tags` field added to `Post`-type<br>
  Thanks to Miguel Sandim for input
* Issue #325: missing fields added to `Comment`-type<br>
  Thanks to Miguel Sandim for input
* Issue #322: missing fields added to `Video`-type<br>
  Thanks to @aonischenko for input

1.15.0 (October 8, 2015)

* Issue #315 and #316: **Graph API 2.5 support**
* Issue #122: Enum support added to `DefaultJsonMapper`<br>
  Thanks to @MusikPolice for the input
* Issue #159: Marketing API support added<br>
  Thanks to @yange for the input
* Issue #218: Value methods for `PageSettings`
* Issue #289: Osgi bundle<br>
  Thanks to @skaluva for the PR
* Issue #289: integration test for `/me/likes`
* Issue #303: LICENSE added to root
* Issue #304: Missing fields added to `Location` type
* Issue #305: `facebook-api-version` HTTP header is logged
* Issue #307: Missing fields added to `Insight` type
* Issue #308: `DefaultJsonMapper` test coverage improved
* Issue #309: Cleanup - formatting and imports
* Issue #312: `Event.getPicture()` fixed<br>
  Thanks to @OhGhie4iew
* Issue #313: `CoverPhoto` added to `User` type
* NoIssue: documentation improved on website

1.14.1 (September 1, 2015)

* Issue #252: strange events behaviour<br>
  Thanks to Paul Myburgh
* Issue #277: version enum changed - `LATEST added<br>
  Thanks to @skaluva
* Issue #286: `likes` field in `Comment` type changed<br>
  Thanks to Bogdan Neacsa

1.14.0 (July 18, 2015)

* Issue #273: Normalize license header
* Issue #271: Add `devices` field to `User`-type
* Issue #270: Graph API 2.4 - `Video`-type new fields
* Issue #267: Wrong mapping for (Feed)Targeting<br>
  Thanks to @Karnifexx
* Issue #266: general **Graph API 2.4 support**
* Issue #265: PR - Blank URL in BatchRequest<br>
  Thanks to @ehrmann
* Issue #260: `ProfilePictureSource` for `Page`-type
* Issue #246: Commens/Likes added to `Video` type (incl special count method)<br>
  Thanks to @anenkov

1.13.0 (July 8, 2015)

* Issue #261: `targeting` field missing in `Post`-type<br>
  Thanks to @Karnifexx
* Issue #262: mapping exception when using `Post` with `feed_targeting` and `target city`<br>
  Thanks to @Karnifexx
* Issue #263: `full_picture` field missing in `Post` type<br>
  Thanks to @CannyDuck

1.12.0 (July 5, 2015)

* Issue #258: Device Access Token support added
* Issue #256: Using FeedTargeting wrapper when posting<br>
  Thanks to @Karnifexx
* Issue #249 and #248: PR Post is hidden wrong place<br>
  Thanks to @2bitoperations
* Issue #247: Inefficiency in DefaultFacebookClient<br>
  Thanks to @lawrence-dol
* Issue #245: FacebookClient (interface) publish method missing<br>
  Thanks to @lawrence-dol
* Issue #243: `appsecret_proof` integration tests
* Issue #220: Webhooks (RTU) subcription high level API<br>
  Thanks to @FrancoisThareau
* Issue #207: Graph API 2.3 video playlist support

1.11.0 (May 28, 2015)

* Issue #221: Add feed_targeting object and addfield to Post type
* Issue #226: Cleanup
* Issue #227: Mark Version 1.0 as deprecated
* Issue #228: Update example code
* Issue #229: Add `scopes` field to `DebugTokenInfo`-type
* Issue #230: `username` field in `User` type is deprecated<br>
  Thanks to @ashrithg
* Issue #231: Added `hours` field to `Page` response<br>
  Thanks for the pull request to Mattias Holmqvist
* Issue #235: Add more fields to `DebugTokenInfo`-type<br>
  Thanks to @fern
* Issue #236: Bugfix: `obtainUserAccessToken` will not validate code in `fb_signed_request`<br>
  Thanks to @frett
* Issue #237: Comments regarding location and venue deprecation added<br>
  Thanks to @Blarff

1.10.1 (April 24, 2015)

* Issue #223: access token parsing for Graph API 2.3 added<br>
  Thanks to @SmokyBot
* Issue #224: javadoc improvement regarding how some fields are filled<br>
  Thanks to @munwaikong

1.10.0 (April 22, 2015)

* Issue #219: `is_hidden` and `is_published` fields added to `Post`-type<br>
  Thanks to Karnifexx
* Issue #215: FB error without "type" not handled properly<br>
  Thanks to @tonyd
* Issue #214: Connection cursor broken<br>
  Thanks to @slumx and @naneunga
* **Graph API 2.3 support**
  * Issue #200: Graph API 2.3 hint<br>
   Thanks to Aurelie Vache
  * Issue #201: add new API Version to enum
  * Issue #202: add `user_posts` permission
  * Issue #203: Graph API debug mode
  * Issue #204: add field `admin_creator` to `Post`-type (Page Post only)
  * Issue #205: new page fields
  * Issue #206: new page settings
  * Issue #208: `Page` featured videos collection
  * Issue #209: `published` field added to `Video`-type
  * Issue #210: Video custom thumbnail
  * Issue #211: Video new fields
  * Issue #212: `read_friendlists` permission is `read_custom_friendlists`
  * Issue #213: `publish_pages` permission

1.9.0 (March 25, 2015)

* Issue #196: Logout Url Builder added<br>
  Thanks to Fagner Brack
* Issue #193: `AdVideo` support merged<br>
  Thanks to @maczam
* Issue #192: ETag thread safety
* Issue #191: cursor support added<br>
  Thanks to @oferfort
* Issue #187: Findbugs and Intellij inspection fixes<br>
  Thanks to Damian Pawlowski
* Issue #106: *ETag support* added
* Issue #52: `ScopeBuilder` added<br>
  Thanks to Broc Seib for the input

1.8.0 (February 13, 2015)

* Issue #93: obtain user access token method added<br>
  Thanks to Johno Crawford
* Issue #173: http delete added<br>
  Thanks to Eric Hydrick
* Issue #176: DefaulFacebookClient - missing constructor added<br>
  Thanks to Denis Bardadym
* Issue #177: `age-range` field added to `User` type
* Issue #179 + #171: restfb website moved to gh pages<br>
  Thanks for the input to Marcel Stör
* Issue #181: binary post Integration test added<br>
  Thanks to Rick Fischer 

1.7.1 (January 16, 2015)

* Issue #149: `can_hide` and `is_hidden` fields added<br>
  Thanks for input, code snippets and review to Aurelie Vache
* Issue #154: deprecated constructors flagged with annotation
* Issue #155: version 1.7.1 prepared
* Issue #156: `Version` Enum fixed<br>
  Thanks to Aurelie Vache
* Issue #158: experimental high level API added
* Issue #160: old FQL query and multiquery method set to deprecated
* Issue #161: `object` field added to `Comment`-type
* Issue #164: additional details added to facebook exception<br>
  Thanks to Chandler
* Issue #166: Copyright updated
* Issue #168: User.Education can handle String-only year<br>
  Thanks to @suryaganeshv
* Issue #169: unittest fixed for java 8

1.7.0 (November 7, 2014)

* Issue #7: speed up date parsing, `DateFormatStrategies` added<br>
  Thanks for input and code review to @inthepocket, Marcel Stoer, Mike Hazelwood, Peter Vahlstrup
* Issue #139: performance boost section added to website
* Issue #58: `JsonArray` serializable<br>
  Thanks to @EpiXl33t, @rackom
* Issue #44: DefaulWebRequestor should not close binaryAttachment stream<br>
  Thanks to Marcel Stoer
* Issue #138: `Thread`-type added
* Issue #145, Issue #146, Issue #150: **Graph API 2.2 support** added
* Issue #133: comments and likes total count API unified
* Issue #143: API Versioning added to other endpoints<br>
  Thanks to @munwaikong
* Issue #48: make types mutable<br>
  Thanks to Courtney Robinson, Broc Seib, Marcel Stoer
* Issue #144: API check util added
* Issue #136: integration test configuration enhanced
* Issue #130: `URL`-type replaced by a current `URL`-type implementation

1.6.16 (September 22, 2014)

* Issue #131: lombok library added to generate getter automatically
* Issue #129: deleteObject bug using Graph API 2.1 fixed<br>
  Thanks to @Rekoe
* Issue #128: total count added to connection<br>
  Thanks to @alfredoivan
* Issue #99: obtaining extended access token via Http GET<br>
  Thanks to Venkateswaran S
* Issue #82: `Review`-type added<br>
  Thanks to @bhargavr
* Issue #73: Missing field added to `Group`, `Metadata`, `Page`, `Post`
* Issue #72: Likes and Comments count fixed in Post object
* Issue #71: fetchObjects fails if non of the ids exist fixed<br>
  Thanks to @oferfort
* Issue #21: `User.Picture`-type added
* Issue #19: Logging documentation improved, example refreshed
* Issue #15: empty list json issue fixed<br>
  Thanks to Shaya Potter
* Issue #12: example build.xml modified to find the restfb.jar<br>
  Thanks to Shaya Potter
* Issue #3: `DeAuth`-type added
* lot of unittests added
* integration tests added

1.6.15 (August 29, 2014)

* Issue #119: basic support for Facebook API versioning added (unversioned, v1.0, v2.0, v2.1)
* Issue #121: `category_list` field added to `Place`-type<br>
  Thanks to Quentin Rousseau
* Issue #109: `Photo.Tag` x and y changed to Double<br>
  Thanks to Peter Vahlstrup
* Issue #110: `Backdated time` and `granularity` added to `Photo`-type<br>
  Thanks to Peter Vahlstrup
* Issue #107: `website` field added to `Page`-type<br>
  Thanks to Damian Pawlowski
* unit test reorganized, mvn test is working now correctly
* some unit test added to check some issues

1.6.14 (January 27, 2014)

* Fix for regression in 1.6.13 where one DefaultFacebookClient constructor override would ignore the supplied
  WebRequestor and JsonMapper and instead use the default implementations.<br>
  Thanks to AlekseiS

1.6.13 (January 25, 2014)

* Support for `appsecret_proof` functionality.<br>
  Thanks to Daniel Piet and Mike Champion.
* Now support signed request parsing with new `FacebookClient.parseSignedRequest()`.<br>
  Thanks to Josef Gierbl for the patch.
* Support for specifying content type for binary attachments.<br>
  Thanks to UglyTroLL.  
* Added `can_comment` and `parent` fields to `Comment`-type.<br>
  BREAKING CHANGE:

  `Post.getCount()` is now `Post.getTotalCount()` per July FB API changes.<br>
  Please request '{id}/comments?summary=true' explicitly if you would like the summary field which contains the total_count<br>
  Thanks to Jens Peters for the patch.
* `FacebookGraphException` now has `getErrorCode()` and `getErrorSubcode()` in the event that FB returns these.
  Previously this was only available for `FacebookOAuthException`.<br>
  Thanks to Peter Vahlstrup.
* Fix for `Venue`-type (added new `id` field)<br>
  Thanks to sbisht and neXus1987
* Added missing fields to `Message` type.<br>
  Thanks to Felipe Kurkowski.
* Added missing fields to `Comment` type.<br>
  Thanks to Jan Schweizer.
* Added integer (0, 1) -> boolean JSON serialization.<br>
  Thanks to Tom Zellman.
* Convenience override of `FacebookClient.executeBatch()`<br>
  Thanks to loicAG.
* Added missing fields to `Event`-type

1.6.12 (March 10, 2013)

* Now support Graph API FQL endpoint with new `FacebookClient.executeFqlQuery()` and `FacebookClient.executeFqlMultiquery()`.
  `FacebookClient.executeQuery()` and `FacebookClient.executeMultiquery()` have been deprecated.<br>
  Thanks to Ofer Fort for the patch. 
* Fixed `expires` parsing in `AccessToken.fromQueryString()`.<br>
  Thanks to Chris Petersen.
* Added `can_upload` field to `Album`-type.<br>
  Thanks to Chris Petersen.
* Added `status_type` field to `Post`-type.<br>
  Thanks to Shaya Potter.
* It is possible for Facebook to return a Place's Location object as a String instead - added Place.getLocationAsString().<br>
  Thanks to Ido for finding this.
* Added `currency` field to `User`-type.<br>
  Thanks to Timo Maaranen for reporting this.
* Added `general_info` field to `Page`-type.<br>
  Thanks to Brendan Dahl for reporting this.
* Added `link`, `subject`, and `former_participants` fields to `Conversation`-type.<br>
  Thanks to spiffychick for reporting this.
* Added `shares` to `Post`-type.<br>
  Thanks to Mike Champion for the pull request.
* Added `attachments`, `updated_time`, `unread`, and `unseen` fields to `Message`-type.<br>
  Thanks to S13_Alan for the patch.
* Fix for invalid Windows filename.<br>
  Thanks to Samuel Gélineau for the patch. 

1.6.11 (September 7, 2012)

* *New feature*: you may annotate a method in a class mapped by JsonMapper with `@JsonMappingCompleted`.
  Your method may be *private* and must take *0 parameters* or *1 JsonMapper parameter*.
  This is useful for performing one-time actions after the `JsonMapper` has completed its work, e.g. massaging
  JSON data into other formats.
* Bugfix: The `message_tags` field in `Post`-type could throw an exception when JsonMapper attempted to map it.<br>
  Thanks to Shaya Potter for reporting this.

1.6.10 (September 3, 2012)

* *Potentially-breaking change*: in the event of a Graph API 404 (like fetching an object with an id that doesn't exist, e.g. /XXX)
  we now throw the correct FacebookOAuthException instead of FacebookNetworkException.<br>
  Thanks to oferiko for the bug report.
* Changed all network logging to use the new `com.restfb.HTTP` logger and now default to `FINE` instead of `INFO`
  for normal requests/responses.<br>
  This means you must explicitly enable logging to see what goes over the wire.<br>
  Thanks to Johno Crawford and Marcel Stoer.
* Added a new method to extend an existing Access Token:
  ```java
  FacebookClient.obtainExtendedAccessToken(String appId, String appSecret, String accessToken).
  ```
  See [extend Access Token @ Facebook](https://developers.facebook.com/roadmap/offline-access-removal/#extend_token) for details.<br>
  Thanks to Chris Petersen for the initial patch.  
* Added a new method to acquire an Application Access Token:
  ```java
  FacebookClient.obtainAppAccessToken(String appId, String appSecret).
  ```
  See [application accesstoken @ Facebook](https://developers.facebook.com/docs/authentication/applications) for details.<br>
  Thanks to Chris Petersen for the initial patch.
* Added Andrew Liles' `InsightUtils`-class, which provides a nice wrapper around querying for Insights data.<br>
  Thanks to Andrew Liles.
* Changed the signature of `FacebookClient.deleteObject(String object)` to `FacebookClient.deleteObject(String object, Parameter... parameters)`
  to support parameterized deletion. This is a breaking change if you have a custom implementation of FacebookClient (should not be many of you).<br>
  Thanks to Nick Fenwick for the patch.  
* `FacebookOAuthException` now includes an `errorCode` field since FB now returns one.<br>
  Thanks to connectvg for the bug report.
* `FacebookOAuthException` now includes an `httpStatusCode` field since it's helpful for client application error messaging.<br>
  Thanks to mtsahakis for the suggestion.  
* `DateUtils.toDateFromLongFormat(String date)` now handles the case where FB occasionally returns the number of
  seconds since the epoch instead of a `yyyy-MM-dd'T'HH:mm:ssZ` string.<br>
  Thanks to raphael.lisboa@gauge.com.br for the bug report.
* Added the static method `AccessToken.fromQueryString(String queryString)`, which encapsulates logic for parsing out
  Facebook's "standard" access token query strings, e.g. 'access_token=XXX' or 'access_token=XXX&expires=YYY'.<br>
  Thanks to egor@technoparkcorp.com for the suggestion.
* Can now map Java types to JSON if they have multiple @Facebook annotations for the same value (e.g. User's hometown fields).
  Previously the mapper would throw an exception, now it picks one of the fields - it's luck of the draw which one is used.
  This behavior may change later, this is a quick workaround to address the issue short-term.<br>
  Thanks to petermein for the bug report.
* Various changes to classes in `com.restfb.types` to reflect latest Facebook updates.<br>
  Thanks to Chris Petersen, Marcel Stoer, Mike Hazelwood, Marco Scavuzzo, Stefan Hauk, Mike Champion, nitinik, egor@technoparkcorp.com, and others.

1.6.9 (October 21, 2011)

* Bugfix for `Connection` page iteration.<br>
  Thanks to Takashi Kawachi for the patch.

1.6.8 (October 15, 2011)

* Now allow batch requests to specify `name` parameter and fixed a bug where request headers were not
  being included in requests.<br>
  Thanks to jyorke.
* Added new `place` field to `Post` type.<br>
  Thanks to Chris Schilling.
* Added new `location` field to `Page` type.<br>
  Thanks to Fernando Padilla.  
* Made several updates to the `education` field on the `User` type.<br>
  Thanks to Marcel Stoer and Silent Film.
* Fix for FB's inconsistent and undocumented behavior for returning `likes` and `comments` fields in `StatusMessage`.<br>
  Thanks to volkenborn.
* Moved to Github (https://github.com/revetkn/restfb) and back to an Ant-based build system for simplicity.
  RestFB releases will still be pushed to Maven central, but RestFB itself will no longer use Maven. 

1.6.7 (September 10, 2011)

* The `Connection<T>` constructor is now public and the class implements `Iterable<List<T>>`.<br>
  Thanks to kongo09, Andrew Liles, and everyone else who requested this.
* Fixed regression: re-added `comments` and `likes` fields to `StatusMessage` (FB docs are incorrect).<br>
  Thanks to nstratos and volkenborn for letting me know. 
* Added `cover_photo` and `privacy` fields to `Album`.<br>
  Thanks to serg472 for the heads-up.
* Added `checkins` and `phone` fields to `Page`.<br>
  Thanks to jaiswal.rahul for the heads-up about the `checkins` field.
* Added an example project which shows how to route `java.util.logging` calls to Log4j.
  It's available at http://restfb.com/examples/RestFB%20Log4j%20Example.zip<br>
  Thanks to Daniel Longosz for asking about this.

1.6.6 (July 12, 2011)

* Fixes a bug introduced in 1.6.5 where facebookClient.delete(...) would throw an NPE.<br>
  Thanks to Garret Collins.

1.6.5 (July 4, 2011)

* BREAKING CHANGE:

  We are no longer passing around InputStream objects to represent binary files when publishing -
  they have been replaced by instances of the new `com.restfb.BinaryAttachment` type.
  So this method:
  ```
  <T> T FacebookClient.publish(String connection, Class<T> objectType, InputStream inputStream, Parameter... parameters)
  ```
	is now:
  ```
  <T> T FacebookClient.publish(String connection, Class<T> objectType, BinaryAttachment binaryAttachment, Parameter... parameters)
   ```
  This change was necessary to support correct video uploads (FB now requires a filename with a video extension, e.g. .mov) and the new Batch API.
  Documentation and usage examples for `com.restfb.BinaryAttachment` are available on http://restfb.com.
* Added support for the new Batch API. The two new methods are:
  ```java
  List<BatchResponse> FacebookClient.executeBatch(BatchRequest... batchRequests)
  List<BatchResponse> FacebookClient.executeBatch(List<BatchRequest> batchRequests, List<BinaryAttachment> binaryAttachments)
  ```
  Thanks to everyone who asked for this!
* Added support for the new video upload API endpoint via https://graph-video.facebook.com and fixed a multipart
  filename bug that broke video uploads.<br>
  Thanks to yylaob@gmail.com for the filename bugfix and saylxxx@gmail.com and everyone else who asked for this!
* All types under com.restfb.types now implement Serializable.<br>
  Thanks to everyone who asked for this!  
* Fixed a numeric precision bug in `JsonObject.getLong()` which caused large numbers to be rounded incorrectly.<br>
  Thanks very much to ps@king.com for finding this and providing a patch.
* `DefaultJsonMapper`` can now instantiate types with no-argument constructors that are private, protected, or package-private -
  you no longer have to expose your constructors if you don't want to.
* `JsonMapper.toJson(Object object, boolean ignoreNullValuedProperties)` was added, which enables you to turn a Java object into
  JSON without including any properties with null values. 
* Added `comments`, `likes`, `position`, and `images` fields to `Photo` type.<br>
  Thanks to Andre Oosthuizen and Grace Win.
* Added `name`, `tags`, and `source` fields to `Video` and marked `message` as deprecated.<br>
  Thanks to Andre Oosthuizen and Marcel Stoer.
* Added `likes` and `access_token` fields to `Page` and deprecated `fan_count`.<br>
  Thanks to Shashank Agarwal and jspyeatt.
* Added `object_id` field to `Post`.<br>
  Thanks to Grace Win.
* Added `username` field to `User`.<br>
  Thanks to Iwan Gulenko.
* Added `city`, `state`, and `country` fields to `Checkin.Place.Location`.<br>
  Thanks to Thom Nichols.
* Removed `messages` and `likes` fields from `StatusMessage` because they're now connections.
  Added the `type` field to `StatusMessage`.<br>
  Thanks to volkenborn@yahoo.com.

1.6.4 (March 5, 2011)

* Added `application` and `type` fields to `Post`.<br>
  Thanks to nelrib.
* Removed `mobilePhone` and `address` fields from `User`.<br>
  Thanks to Marcel Stoer.
* Added undocumented `properties` field to `Post` type.
  
1.6.3 (February 19, 2011)

* RestFB now automatically uses the faster read-only endpoints for Graph and Old REST API calls when possible.<br>
  Thanks to Dmitry Naumenko for the suggestion.
* POTENTIALLY BREAKING CHANGE: Old REST API calls (including FQL with the Graph API) will now throw
  FacebookOAuthException instead of FacebookResponseStatusException when error code 190 is encountered.<br>
  Thanks to Ofer Fort for the suggestion.
* It is now simple to customize how RestFB maps Facebook error responses to FacebookException instances
  for both Graph and Old REST API errors by overriding `DefaultFacebookClient.createGraphFacebookExceptionMapper()` and
  `BaseFacebookClient.createLegacyFacebookExceptionMapper()`, respectively.  The default instances returned
  by both of these methods are now marked protected instead of anonymous classes so you can leverage them if you wish.<br>
  Thanks to Alexis Dufrenoy for the suggestion.
* Added lots of new fields to `User`, e.g `sports` and `languages`.<br>
  Thanks to Patrick Alberts for the patch.
* Added new PageConnection type, useful for mapping partial page types returned by some connections, e.g. "me/music".<br>
  Thanks to Patrick Alberts for the patch.
* Added undocumented `is_community_page` and `description` fields to the `Page` type.<br>
  Thanks to Ivan Viragine.
* Can now customize HttpURLConnection creation in DefaultWebRequestor by overriding openConnection(URL url)
  to support custom proxying etc.<br>
  Thanks to Matti Haro for the suggestion.
* Added new `Insight` type which can be used like this:
  ```java
  Connection<Insight> insights = facebookClient.fetchConnection("PAGE_ID/insights", Insight.class);
  ```

1.6.2 (January 23, 2011)

* You can now instruct `DefaultJsonMapper` how to handle mapping exceptions, e.g.
  ignoring them and mapping as null instead of throwing an exception. 

  ```java
  FacebookClient facebookClient = new DefaultFacebookClient("MY_ACCESS_TOKEN",
    new DefaultWebRequestor(), new DefaultJsonMapper(new JsonMappingErrorHandler() {
      public boolean handleMappingError(String unmappableJson, Class<?> targetType, Exception e) {
        // Here you might log the fact that JSON mapping failed.
        err.println(format("Uh oh, mapping %s to %s failed...", unmappableJson, targetType));

        // Returning true tells the mapper to map this JSON as null and keep going.
        // Returning false tells the mapper to throw an exception.
        return true;
      }
    }
  ));
  ```
  
  The default behavior is still to throw an exception immediately on mapping errors,
  this callback interface is your way of saying "I know what I'm doing and want to
  handle errors myself". Very useful for keeping your app running in production
  when Facebook changes the API unexpectedly.<br>
  Thanks to Igor Kabiljo for the suggestion.   
* Added new `quotes` and `address` and `mobile_phone` fields to the `User` type.<br>
  Thanks to ne52 for the heads-up about the addition of the quotes field.
* `DateUtils.toDateFromMonthYearFormat` now returns null for `0000-00` instead of `0 AD`.
  Not sure why FB gives us 0000-00 instead of null in some situations, e.g. work start date.

1.6.1 (unofficial release)

* We're now fully Mavenized - you can add RestFB to your Maven project like this:
  ```xml
  <dependency>
	  <groupId>com.restfb</groupId>
	  <artifactId>restfb</artifactId>
	  <version>1.6.2</version>
  </dependency>
  ```
  Many thanks to Kalle Korhonen, and congratulations to him for being the first additional RestFB committer!
* `JsonException` is now unchecked.
* Fix for start and end date handling on the `Event` type.<br>
  Thanks to Jason Peltzer.
* @Facebook annotation on accessToken on `Account` type is now correctly mapped to `access_token`.<br>
  Thanks to Scott Aaron.
* Added the `category` field to the `Checkin.Place` type.<br>
  Thanks to Mattia Tommasone.
* `Latitude` and `Longitude` fields on `Checkin.Place` are now of type `Double` instead of `Float`,
  thereby supporting the full precision of the data returned by FB instead of truncating to fit correctly in a Float.

1.6 (December 30, 2010)

* POTENTIALLY BREAKING CHANGE: `User.getPicture()` has been removed.
  To get the user's picture, simply construct a URL by hand, e.g. http://graph.facebook.com/USER_ID/picture
* Now use `java.util.logging` instead of Log4j - RestFB now has 0 dependencies.
* FacebookException now extends `RuntimeException` - you no longer have to catch it if you don't want to.<br>
  Thanks to Jeff Schnitzer.
* Removed `contains` attribute on `@Facebook` annotation since it is no longer necessary.<br>
  Thanks to Igor Kabiljo for the initial patch.
* Exposed and cleaned up previously-internal RestFB's repackaged JSON.org code in the new com.restfb.json package.
* You may now tell `JsonMapper` to map to `JsonObject`, which permits you to have total control over result mapping at run time
  (as opposed to the compile-time-only `@Facebook` annotation).
* `JsonMapper` now supports having a Facebook JSON field mapped to multiple `@Facebook`-annotated Java fields.
  This addresses the issue of Facebook returning the same field name but differently-structured data in certain situations.
  See issues 56 and 90 for details.<br>
  Thanks to Daniel Winterstein, Igor Kabiljo, Marcel Stoer, Pramod Biligiri, and Tom Schindl for their input.
* Exposed and cleaned up previously-internal RestFB utilities in the new `com.restfb.util` package.
* Added new exception package `com.restfb.exception` and exception types
  (`FacebookOAuthException`, `FacebookQueryParseException`) for simple handling of common Graph API exception types.<br>
  Thanks to Jeff Schnitzer.
* Added formal support for Connection paging by URL via `FacebookClient.fetchConnectionPage()` and the new `Connection.getPreviousPageUrl()/getNextPageUrl()`.
  No more manual offset calculation required.

  Example:

  ```java
   Connection<Post> myFeed = facebookClient.fetchConnection("me/feed", Post.class);

   if(myFeed.hasNext()) {
     Connection<Post> myFeedPage2 = facebookClient.fetchConnectionPage(myFeed.getNextPageUrl(), Post.class);
     // ...
   }
  ```
  
  Thanks to Jakub Danilewicz.
* Added `FacebookClient.convertSessionKeysToAccessTokens()` method.<br>
  Thanks to Scott Hernandez and Mattia Tommasone. 
* Fixed issue where `User.birthday` causes mapping to fail if "show only month & date" is set on FB.<br>
  Thanks to Kalle Korhonen.
* Old REST API now always uses the HTTPS endpoint.<br>
  Thanks to Christopher Oestlund.
* Added `embed_html`, `picture`, `icon`, `comments` to `Video` type.<br>
  Thanks to Fahd Akbar and Chris Pruett.
* Added `Account` type to support impersonation feature.<br>
  Thanks to jovanovich.
* Fixed error-handling issues: 1) where HTTP 400 error code would throw an incorrect exception and 2) where error response JSON would not get
  correctly read in this and other error situations.<br>
  Thanks to Igor Kabiljo, ovonick, and everyone else who reported this.
* Performance enhancement: `ReflectionUtils` now caches `@Facebook` field information so we never have to reflect more than once.<br>
  Thanks to Igor Kabiljo for the patch.
* Added Checkin type.<br>
  Thanks to everyone else who requested this and Colin Britton for the checkin code snippet.
* Added StatusMessage type.
* Added middle_name field to User type.<br>
  Thanks to Fehmican Saglam.

1.5.4 (September 16, 2010)

* Post comment type now handles case where FB returns an empty array instead of an object type.<br>
  Thanks to Pipo, CR, S13_Alan, Igor Kabiljo, and Jakub Danilewicz.

1.5.3 (July 21, 2010)

* `NamedFacebookType` now handles situation where FB returns, for example,
  "hometown":"Beograd", instead of the expected "hometown":{"id":123,"name":"Belgrade, Serbia"}<br>
  Thanks to Igor Kabiljo for the bug report and suggested fix.
* Mapper now gracefully handles situation where Facebook returns the not-JSON string "false" and instead of {}.<br>
  Thanks to Igor Kabiljo for the bug report and suggested fix.
* The Post type's comments field changed on the Facebook side to include a "count" field.<br>
  Thanks to Jakub Danilewicz for the bug report and suggested fix.<br>
  Thanks to Igor Kabiljo for the suggested fix.    
* Fixed infinite recursion bug that was triggerable in exceptional situations in ReflectionUtils.toString().<br>
  Thanks to Igor Kabiljo for the bug report and suggested fix.
* Mapper fix for situations where Facebook returns the string "null" instead of an empty object or nothing.<br>
  Thanks to Igor Kabiljo for the bug report.
* Timezone field in User is now a Double to account for timezones with fractional offsets.<br>
  Thanks to Sam for the bug report.
* Changed Parameter's date marshaling to produce Facebook Date strings in a zero-based hour format.<br>
  Thanks to Jakub Danilewicz for the bug report and suggested fix.
* `Connection<?>` has `getNext()` and `getPrevious()` methods that return the next/previous URLs returned by Facebook.
* All fields in `BaseFacebookClient`, `DefaultFacebookClient`, and `DefaultLegacyFacebookClient` are protected for easier subclassing.
  All Facebook API URLs are now overridable via protected accessor methods.<br>
  Thanks to Kalle Korhonen and bijuphilip0 for the enhancement requests.
* Added locale field to User.

1.5.2 (June 8, 2010)

* No longer require an access token for the Graph API (now have a default constructor for `DefaultFacebookClient`).<br>
  Thanks to Yoav Shapira for the enhancement request.
* Facebook changed the data in the `Photo Tag` object - updated to reflect that.<br>
  Thanks to Max for reporting. 
* Facebook added fields to the `User` object - updated to reflect that.<br>
  Thanks to fellahst and ndimiduk for reporting.
* HTTP GET operations executed by `DefaultWebRequestor` now use `httpUrlConnection.setUseCaches(false)`.<br>
  Thanks to Jumpa for reporting this.
* Special check to only URL-encode access token if it's not already URL-encoded.

1.5.1 (May 14, 2010)

* OAuth support broke in `DefaultLegacyFacebookClient` because Facebook changed the `token` param to `access_token`.<br>
  Thanks to Antonio Casula for the initial problem report.
  
1.5 (May 1, 2010)

* Added support for the new Facebook Graph API
* Old REST API now has support for the new OAuth authentication tokens
* Fix for multiqueries with empty resultsets throwing a JSON mapping exception
* `WebRequestor` now supports POSTs which perform file uploading and GETs
* The `com.restfb.json` undocumented package is now gone.  The `org.json` code is now package-private inside of `com.restfb`
* Many small bugfixes and enhancements
* Enhanced examples and documentation<br>
  Thanks to Ofer Fort, Ryan Wilson, and Alex Launi for their input.

1.4 (April 21, 2010)

* Added support for Java -> JSON mapping to support more complex API calls like stream.publish.<br>
  Thanks to Alex Launi and biz@layes.com for their input.

1.3 (March 19, 2010)

* In response to recent FB changes, JSON mapper now coerces empty JSON arrays to empty Java Strings if you're trying to map to a String field.<br>
  Thanks to Antonello Naccarato for reporting this!

1.2 (March 2, 2010)

* Fixed bug related to mapping JSON null to Java types
* Library source is now included in the JAR for easy browsing in your IDE

1.1 (February 23, 2010)

* Formal support for `fql.multiquery` API call
* Minor JSON mapping fixes and error message improvements
* Updated website with `fql.multiquery` documentation and explicit mention of non-support of session key acquisition

1.0 (February 2, 2010)

* Initial release
