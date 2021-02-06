# RestFB Changelog

## 2.21.0 (May 9, 2019)

* Issue #993: Missing fields added to `StoryAttachment`
* Issue #994: `VideoPoll` type added

## 2.20.0 (May 1, 2019)

* Graph API 3.3:
   * Issue #989: Version enum changed
   * Issue #990: deprecated fields in `Post` type
   * Issue #991: conversation webhook is deprecated now
* Issue #987: Graph API 2.8 marked as deprecated
* Issue #988: `priorMessage` field added to `MessagingItem`<br />
  Thanks to @chaas for the hint
* Issue #983: Facebook Marketing API rate limitation (Ad Account Level Limits)<br />
  Thanks to @mikron for the hint
* Issue #980 and #981: Video type checked and missing fields added
* Issue #979: `AdCreativeLinkDataChildAttachment.place_data` should be an Object<br />
  Thanks to @AdrianMF for the hint
* Issue #982: correct bid strategy enum names<br />
  Thanks to @Vanley for the PR
* Issue #986: introduce `issues_info` field on `ad-set` and `campaign`<br />
  Thanks to @Vanley for the PR

## 2.19.0 (April 5, 2019)

* Issue #972: code of conduct added
* Issue #975: waffle.io badge removed
* Issue #973: multiple JSON fields with same name fixed<br />
  Thanks to @thevietto for the hint

## 2.18.1 (March 9, 2019)

* Issue #970: `FacebookJsonMappingException` when fetching `Hours` object of a childpage<br />
  Thanks to @mknoebel for the hint and the example JSON

## 2.18.0 (February 19, 2019)

* Issue #953: underscore fields added to NLP entity types
* Issue #967: `overall_star_rating` and `rating_count` fields added to `Page` type<br />
  Thanks to @AvielNiego for the hint
* Issue #965: `FacebookJsonMappingException` when fetching page object hours<br />
  Thanks to @AvielNiego for the hint
* Issue #966: connection iterator fixed<br />
  Thanks to @amanduggal for the hint

## 2.17.0 (February 10, 2019)

* Issue #959: Missing AdSet Targeting field added<br />
  Thanks to @AndreaFalcon for the hint
* Issue #960: More Ad types added and some fixed
* Issue #961: API version annotation added
* Issue #962: undefined change values added<br />
  Thanks to @thevietto for the hint and the JSONs
* Issue #963: licence information changed to 2019

## 2.16.0 (January 25, 2019)

* Issue #956: Some missing fields added to ads classes<br />
  Thanks to @AndreaFalcon for the hint
* Issue #957: `UrlPayload` crash for building image attachment<br />
  Thanks to @nitrag for the hint and the tests

## 2.15.0 (January 11, 2019)

* Issue #950: osgi bundlor replaced
* Issue #952: missing NLP entities added
* Issue #951: error field added to `NlpResult`<br />
  Thanks to @RedEagle for the hint and the example

## 2.14.0 (December 24, 2018)

* Issue #944: minimal-json updated to latest code
* Issue #948: lead_retrieval permission added
* Issue #945: unit test coverage improved

## 2.13.0 (December 4, 2018)

* Documentation improvement
  * Issue #936 - Improve "Setting persistent menu" section<br />
  Thanks to @eximius313 for the hints
  * Issue #935 - Improve "Setting a Welcome Message" section<br />
  Thanks to @eximius313 for the hints
* `DefaulJsonMapper` improved
  * Issue #938 - extract short circuit methods
  * Issue #939 - remove the `JsonMappingErrorHandler`
* Issue #930 - Post `sponsor_tags` field missing
* Issue #928 - unversioned constructors removed from `DefaultFacebookClient`  

## 2.12.0 (October 25, 2018)

* Issue #922: Cleanup old Graph API versions
* Issue #924: Graph API 3.2: add version to enum<br />
  Thanks to @pcornelissen for the PR

## 2.11.0 (October 9, 2018)

* Issue #911: remove ant description from readme
* Issue #912: Webhook JSON for new recommendation
* Issue #915: Place.class improvement with new fields<br />
  Thanks to @egemenozkan for the hint
* Issue #916: Infinite loop while fetching next page<br />
  Thanks to @CrWEE for the hint the analysis
* Issue #920: Set Graph API version 2.7 to deprecated
* Issue #919: Added tasks field to Account<br />
  Thanks to @juvirez for the PR

## 2.10.0 (September 15, 2018)

* Issue #908: Deployment improved
* Issue #909: Connection should construct next & prev page URLs from cursors as a fallback<br />
  Thanks to @mrhota for the hint

## 2.9.0 (August 21, 2018)

* Issue #902: Report parameter values when throwing<br />
  Thanks to @pkoivisto for the PR 
* Issue #905: `publish_video` permission added<br />
  Thanks to @mekya for the hint 
* Issue #904: Changes for the new "recommendations" feature

## 2.8.0 (August 3, 2018)

* Graph API 3.1
  * Issue #899: basic support
  * Issue #900: `LiveVideo` replace `type` with `source` field 

## 2.7.0 (July 8, 2018)

* Issue #892 - `LIKES_ADD` missing for user object in webhook<br />
  Thanks to Mikhail (Facebook DM) for the hint
* Issue #893 - Add `user` webhook change value that contains a link to a page

## 2.6.0 (June 1, 2018)

* Issue #889 - switch to java 7
* Issue #890 - replace old custom base 64 implementation
* Issue #888 - improved exception logging

## 2.5.0 (May 4, 2018)

* Graph API 3.0
  * Issue #885: Basic support
  * Issue #886: Permissions added and cleanup
* Issue #882: new field to instagram comments/replies<br />
  Thanks to @amanduggal for the hint and PR
* Cleanup:
  * Issue #881: picture conversion helper added 
  * Issue #880: `Date` mapping added
  * Issue #877: old readonly API removed
* Issue #876: version 2.5 is deprecated now
* Issue #879: `getCaptions()` method throws NPE in `Video` object when `captions` is null<br />
  Thanks to @CrWEE for the hint
* Issue #878: Some `OpenGraphRating` fields mapped with extra quotation marks<br />
  Thanks to @CrWEE for the hint

## 2.4.0 (April 6, 2018)

* Issue #865: Add logging formatter for JUL
* Issue #868: Support for Instagram Content Publish Scope<br />
  Thanks to @AndreaFalcon for the hint
* Issue #870: Deserialisation issue with JsonNumber<br />
  Thanks to @ErfanMaybe for the hint
* Issue #872: Improve deserialisation according to #870
* Issue #873: Check April 2018 breaking changes

## 2.3.0 (March 4, 2018)

* Instagram support
  * Issue #854: add `instagram_business_account` to Page type
  * Issue #855: create `IgUser` type
  * Issue #856: `IgMedia` type
  * Issue #859: webhook objects
  * Issue #857: `IgComment` type
  * Issue #858: missing connections added
  * Issue #851: Request for support of Facebook Instagram API<br />
    Thanks to @martinmalek for the hint

* Messenger Platform 2.3 changes
  * Issue #861: Phone and email quick replies
  * Issue #862: Request thread control for the Handover Protocol

## 2.2.0 (February 20, 2018)

* Issue #845: Graph API 2.12 enum added
* Issue #846: Graph API 2.12 - marked field in `Event` type as deprecated
* Issue #847: `screennames` added to `Page` type<br />
  Thanks to @joelforessottile for the hint
* Issue #848: old eclipse stuff removed<br />
  Thanks to @ghost for the hint
* Issue #849: missing id lists added to `User` type<br />
  Thanks to @JB4GH for the hint
* Issue #701: Solution for integration testing without make the endpoint urls changeable
* Issue #850: Add invitable friends to `User` type
* Issue #852: Add instagram permissions  

## 2.1.1 (January 28, 2018)

* Issue #844: fix "bundlor" problem

## 2.1.0 (January 28, 2018)

* Issue #834: directories restructured
* Issue #836: Copyright year changed to 2018
* Issue #837: `canReplyPrivately` added to `Post` type<br />
  Thanks to @dmtar for the hint
* Issue #838: `DefaultFacebookClient.fetchObjects()` uses Json array<br />
  Thanks to @grumseren for the hint and analysis

## 2.0.0 (December 11, 2017)

* Issue #833: Facebook Messenger - builtin NLP entities added<br />
  Thanks to @RedEagle for the hint
* Issue #830: Git branches reorganized

## 2.0.0-rc.4 (December 2, 2017)

* Issue #791: README.md cleanup
* Issue #802: Old Version enum values removed

## 2.0.0-rc.3 (July 13, 2017)

* Issue #746: Make `FacebookPermission` enum public

## 2.0.0-rc.2 (March 28, 2017)

* Issue #577: Move permissions in one class
* Issue #642: Use AssertJ in core unit tests
* Issue #677: Remove obsolete FQL methods
* Issue #692: Remove Rest API access

## 2.0.0-rc.1 (December 24, 2016)

* new Json parser
  * Issue #321: Replace "Json.org" parser with "minimal-json" parser
  * Issue #519: unicode (emoji) fix added to minimal-json (port of #501)
  * Issue #585: improve the `JsonAssert` util
* Logging
  * Issue #31: support user-definable logging via slf4j<br>
    Great idea @revetkn
  * Issue #635: rename logger categories
* Code quality
  * Issue #339: Code quality fix - Collection.isEmpty should be used<br>
    Thanks to @faisal-hameed for this PR
  * Issue #345: Code quality fix - @Override annotation should be used<br>
    Thanks to @faisal-hameed for this PR
  * Issue #344: Code quality fix - Redundant field intializers<br>
    Thanks to @faisal-hameed for this PR
  * Issue #189 and #190: create collections with needed size<br>
    Thanks to @profes for the report and PR
* Cleanup
  * Issue #299: `StoryAttachment` changed
  * Issue #257: `Post` and `Comment` use same `Attachment` type
  * Issue #302: Graph API 1.0 support removed
  * Issue #314: `Event#getPicture` returns `Picture` instead of String
  * Issue #311: Remove `Attribution` field from `Post` type
  * Issue #310: `Event` venue field has wrong type
  * Issue #323: Remove deprecated classes from `Post` type
  * Issue #337: `Photo#getComments()` returns List of comments instead of Comments object<br>
    Thanks to @vido88 for this report
  * Issue #364: Remove inner class `MessageTag` from `Post`
  * Issue #80: Exception Generator added<br>
    Thanks to @slumx for the report
  * Issue #195: `Likes` type improved
  * Issue #113: `Event` can contain multiple `venues`<br>
    Thanks to @guychauliac for the PR
  * Issue #367: old inner class `comments` removed from `comment` type
  * Issue #402: `Page` field `location` should be of type `Location`
  * Issue #301: deprecated FQL methods removed
  * Issue #609: Remove experimental classes
  * Issue #467: Use `Comments` and `Likes` in `Photo` type
  * Issue #452: fix bug from #448

## 1.49.0 (December 1, 2017)

* Issue #824: Sticker field missing from messages<br />
  Thanks to @CrWEE for the hint
* Issue #825: Add `MessagingItem.isAppRoles()`<br />
  Thanks to @marceloverdijk for the hint
* Issue #826: Should the `OpenGraphTemplatePayload` have elements instead of buttons?<br />
  Thanks to @marceloverdijk for the hint

## 1.48.0 (November 12, 2017)

* Graph API 2.11
  * Issue #816: 2.11 Version added
  * Issue #817: Webhook sender_name and sender_id is from now
* Messenger Platform 2.2
  * Issue #819: messaging_type enum added
  * Issue #820: Broadcast API
  * Issue #818: Add media template

## 1.47.0 (October 29, 2017)

* Issue #793: Added convenience constants and methods
* Issue #794: Added pass/take thread control callbacks
* Issue #797: Convenience messaging item methods
* Issue #798: Add more is* methods in InnerMessagingItem
* Issue #796: Added convenient has* methods to MessageItem
* Issue #803: Set old version values to deprecated
* Issue #801: Add Open Graph Template to Send API types
* Issue #800: Support default_action in Generic Template element
* Issue #799: Add is_test_payment to PaymentSummary
* Issue #810: Add Stand By webhook callback
* Issue #809: Add App Roles webhook callback
* Issue #805: Add object for Messenger Code API
* Issue #811: Add UserProfile for fetching user information in messenger context
* Issue #804: Add object representing Messenger Profile API data 

## 1.46.0 (September 28, 2017)

* Issue #782: Support custom wit.ai NLP in Messenger
* Issue #781: Fix Java 7 built on Travis CI
* Issue #787: Let Travis CI built RestFB with Java 9
* Issue #784: Prevent nullpointer when null quickReplies in `Message`<br />
  Thanks to @SijmenHuizenga for the PR
* Issue #785: `ChangeValueFactory` tries to convert a string to `JsonObject` and throws exception<br />
  Thanks to @maurocanuto for the hint and the example JSON

## 1.45.0 (September 1, 2017)

* Issue #765: Messenger Platform 2.1: support NLP on messages
* Issue #779: Ads: Reach Frequency Prediction type is missing

## 1.44.0 (August 13, 2017)

* Graph API 2.10 support
  * Issue #760 and #773: basic support added<br>
  Thanks to @jcdavidconde for the hint
  * Issue #763: Webhook updates for `event` added to factory
* Messenger Platform:
  * Issue #757: shareable option is missing in Send API
  * Issue #766 : Policy Enforcement Callback
* Issue #753: Bugfix for uploading video captions<br>
  Thanks to @frank72833 for the hint  
* Issue #769: `VideoCaption` type added
* Issue #758: `RATINGS_RATING_EDIT` added to Webhook types
* Issue #759: `FEED_VIDEO_REMOVE` added to Webhook types
* Issue #752: Add fields missing from `AdCreativeLinkData` and `AdCreativeVideoData`<br>
  Thanks to @pkoivisto for the PR 
* Issue #756: Add missing fields for `AdCreativeLinkDataCallToActionValue`<br>
  Thanks to @pkoivisto for the PR

## 1.43.0 (July 2, 2017)

* Issue #741: `commentId` field added to `FeedReactionValue`<br>
  Thanks to @thienit5 for the hint
* Issue #742: `TargetingSentenceLine` improved<br>
  Thanks to @eipark for the hint
* Issue #744: add `open_graph_story_id` to ratings comment<br>
  Thanks to @abid76 for the hint
* Issue #745: `Post.getLikesCount` should return 0 if no likes field is found<br>
  Thanks to @bogdanlupashko for the hint
* Issue #748: Add missing `thread_key` to `PageConversation` webhook type
* Issue #749: Webhook from editing a visitors post maps to `FallBackChangeValue`<br>
  Thanks to @CrWEE for the hint

## 1.42.0 (June 9, 2017)

* Issue #730: Add `permalink_url` to `Comment`<br>
  Thanks to @ashishnargundkar for the hint
* Issue #729: Add `target_countries` to `LookalikeSpec<br>
  Thanks to @graysky for the hint 
* Issue #728: `TargetingSentenceLine` missing<br>
  Thanks to @eipark for the hint
* Issue #732: Getter for `DebugTokenError` added<br>
  Thanks to @ClaudioBull for the PR
* Issue #733: Spring mapping issue fixed<br>
  Thanks to @bogdanlupashko for the hint
* Issue #735: Webhook "mention" ChangeValue added<br> 
  Thanks to @torbenw for the hint
* Issue #736: `X-Page-Usage` and `X-App-Usage` return JSON instead of percentage `String`
* Issue #738: `PersistentMenu` Invalid keys `callToActions`<br>
  Thanks to @thienit5 for the hint

## 1.41.0 (May 17, 2017)

* Issue #723: `AdReportRunFields` type added<br>
  Thanks to @eipark for the hint
* Issue #725: Reactions support for `Comments`
* Issue #726: Fetching device access tokens fixed

## 1.40.0 (April 23, 2017)

* Graph API 2.9:
  * Issue #713: basic Graph API 2.9 support
  * Issue #714: `short_name` added to `User` type
  * Issue #716: `engagement` added to `URL` type
  * Issue #715: `fields` in `subscriptions` object changed<br>
  This is a breaking change! Check the javadoc
* Messenger Platform 2.0:
  * Issue #717: ID matching types added
* Issue #706: multiple photos support added to Webhook type<br>
  Thanks to @GithubMood for the hint
* Issue #709: Integration test for posting to a group added
* Issue #708: Fetching users from group integration test added<br>
  Thanks to @saveSession for the hint

## 1.39.0 (March 26, 2017)

* Issue #691: Rest API set to deprecated
* Issue #690: Contributing.md added
* Issue #696: Version 2.2 set to deprecated
* Issue #694: Webhook: missing ChangeValues RATINGS_REACTION_ADD/EDIT/REMOVE
* Issue #697: Missing fields in post.call_to_action added<br>
  Thanks to @nemzsom for the hint
* Issue #699: README.md updated<br>
  Thanks to @chena for the PR
* Issue #698 and #702: fetchObjects normelizes IDs<br>
  Thanks to @UbaldoF and @oferfort for the hints
* Issue #693: Adding setters to the facebook endpoint URL's<br>
  Thanks to @jaguth for the PR

## 1.38.0 (March 9, 2017)

* Messenger Platform 1.4 support
  * Issue #680: `PersistentMenu` added
  * Issue #683: quick replies limit increased to 11
  * Issue #682: `locale` added to `Greeting`
  * Issue #681: optional `name` field added to phone recipient
  * Issue #676: `image_aspect_ratio` added to `GenericTemplatePayload`  
* Issue #673: `AdStudy` type added<br>
  Thanks to @hkroger for the hint
* Issue #674: `is_canceled` added to `Event`type<br>
  Thanks to @DerKeil for the hint
* Issue #672: `snapshot()` method added to connection iterator<br>
  Thanks to @DerKeil for the information and the hints
* Issue #679: `call_to_action` added to `Post` type<br>
  Thanks to @CrWEE for the hint
* Issue #685: `child_attachments` added to `Post` type<br>
  Thanks to @nemzsom for the hint
* Issue #686: `comment_id` added to `FeedLikeValue` webhook type<br>
  Thanks to @Glenn-Bergmans for the hint

## 1.37.0 (February 21, 2017)

* Issue #660: Leadgen Webhook support added<br>
  Thanks to @pibernardi for the hint and the test data
* Issue #661: `business_management` permission added<br>
  Thanks to @eipark for the hint
* Issue #662: unit test improvements
* Issue #663: `addTo` and `removeTo` in `Message` type fixed
* Issue #664 and #665: `UserPermission` added<br>
  Thanks to @anenkov for the PR
* Issue #666: `isLike` method added to Messaging Webhook
* Issue #667: `photo` field added to `FeedCommentValue`<br>
  Thanks to @pcricardo for the hint
* Issue #669: `message` field added to `FeedPhotoAddValue`<br>
  Thanks to @incredibledevs for the hint
* Issue #671: `video` field added to `FeedCommentValue`<br>
  Thanks to @pcricardo for the hint
* Issue #670: Some code smells removed/fixed  

## 1.36.0 (January 29, 2017)

* Issue #657: Add `equals`, `hashcode` and `toString` to Send API types<br>
  Thanks to @marceloverdijk for the hint
* Issue #655: `WorkExperience` type added
* Issue #656: `SavedMessageResponse` type added
* Issue #654: OpenGraph types added
* Issue #652: `InstagramUse` type added<br>
  Thanks to @hkroger for the hint
* Issue #639: `LiveVideo` type added
* Issue #650: `is_transient` field added to error<br>
  Thanks to @hkroger for the hint
* Issue #651: License header changed to 2017

## 1.35.0 (December 16, 2016)

* Issue #634: `FEED_REACTION_EDIT` webhook type added
* Issue #636: `FEED_VIDEO_EDITED` webhook type added 
* Issue #637: `CallToAction` object<br>
  Thanks to @haresh208 for the hint

## 1.34.1 (November 24, 2016)

* Issue #632: fix error in javadoc<br>
  Thanks to @alexconlin for the PR
* Issue #631: performance issue with stacktrace during logging<br>
  Thanks to @hkroger for the hint

## 1.34.0 (November 17, 2016)

* Messenger Platform 1.3 support
  * Issue #619: Passing parameters via M.me links
  * Issue #620: List Template
  * Issue #621: seq id is deprecated
  * Issue #622: Checkbox plugin support<br>
    Thanks to @marceloverdijk for the hint
  * Issue #623: messaging_referral event<br>
    Thanks to @marceloverdijk for the hint
* Issue #625: Bugfix for logger name issue<br>
  Thanks to @torbenw for the bug report
* Issue #626: Add `toString` method to `MessageRecipient` Impls<br>
  Thanks to @marceloverdijk for the hint
* Issue #618: `deleteObject` support thread settings now<br>
  Thanks to @marceloverdijk for the hint
* Issue #574 and #617: Thread settings enums added<br>
  Thanks to @marceloverdijk for the hint and PR
* Issue #615 and #616: `NotificationTypeEnum` added<br>
  Thanks to @marceloverdijk for the hint and PR
* Issue #613: `fbtrace_id` added to `FacebookGraphException`<br>
  Thanks to @marceloverdijk for the hint
* Issue #612: `MessageRecipient` interface added<br>
  Thanks to @marceloverdijk for the hint

## 1.33.0 (October 26, 2016)

* Cleanup:
  * Issue #602: Readme changed to describe only basic usage
  * Issue #604: Experimental package marked as deprecated
  * Issue #608: unused imports removed
* Issue #605: more targeting improvements<br>
  Thanks to @matthewbogner for the hint and PR
* Issue #607: PaymentItem credentials changed to credential
  Thanks to @barhun for the hint
* Issue #603: Basic logging change to resolve #31
  
## 1.32.0 (October 13, 2016)

* Graph API 2.8:
  * Issue #589: Feed Targeting
  * Issue #590: User Bios
  * Issue #588: Basic support
* Graph API 2.7:
  * Issue #517: Add missing field to `VideoCopyright` 
* Messenger Platform:
  * Issue #594: add `sticker_id` to `MessageItem` class and `MessagingAttachment` class<br>
    Thanks to @Abdelrahman-Hasan for the hint
  * Issue #596: Improve the `SendResponse`<br>
    Thanks to @marceloverdijk for the hint
  * Issue #584: add `attachment_id` and `is_reusable` to `MediaAttachment` class<br>
    Thanks to @Abdelrahman-Hasan for the hint
  * Issue #581: Please add a `Message#addQuickReplies` (multiple)<br>
    Thanks to @marceloverdijk for the hint
  * Issue #582: Throw exception on adding too many `QuickReplies`
  * Issue #580: Wrong Date in `FlightSchedule` leads to error code when sending
  * Issue #592: Messenger documentation bug<br>
    Thanks to @vraffy for the hint
* Issue #587: Added `ChangeValue` type `FEED_REACTION_ADD`<br>
  Thanks to @misternerd for the hint
* Issue #586: Wrong `appsecret_proof` created by `EncodingUtils`<br>
  Thanks to @mchmielarz for the hint
* Issue #579: Move license files to root
* Issue #600 and #601: `NPE` on empty `message_tags`<br>
  Thanks to @anenkov for the PR
* Issue #599: add support for interests in `post.feed_targeting`<br>
  Thanks to @matthewbogner for the hint and PR
* Issue #597: `relevant_until_ts` field in `FeedTargeting` is missing
  
## 1.31.0 (September 22, 2016)

* Messenger Platform Update:
  * Issue #566: new QuickReply types added<br>
    Thanks to @marceloverdijk for the hint
  * Issue #567: `CallButton` added
  * Issue #568: `ShareButton` added
  * Issue #571: Field `webview_height_ratio` added to `WebButton`
  * Issue #569: `BuyButton` added, `Payment` webhook type added
  * Issue #570: Webview and Extension fields added
* General Cleanup:
  * Issue #556: log exceptions that can be ignored
  * Issue #559: log more exceptions and use `lastIndexOf` with Char
  * Issue #557: use `isEmpty()` instead of `size()` where possible
  * Issue #561: catch `Exception` instead of `Throwable`
  * Issue #564: types should extend `AbstractFacebookType`
* Issue #552: Added missing fields to comment attachments<br>
  Thanks to @mnshdw for the PR
* Issue #544: Add `toString()` method to webhook classes<br>
  Thanks to @marceloverdijk for the hint
* Issue #553: Add `SenderAction` enum<br>
  Thanks to @marceloverdijk for the hint
* Issue #576: Fixed issue `MessagingItem.getItem` not returning `ReadItem`<br>
  Thanks to @marceloverdijk for the PR
* Issue #555: `FacebookPermissions` scope changed to public<br>
  Thanks to @rhino88 for the PR

## 1.30.0 (August 15, 2016)

* Issue #548 and #549: Support for `x-page-usage` header<br>
  Thanks to @kukido for the PR
* Issue #543: `getCoverPhoto` returns `Photo`-type now<br>
  Thanks to @torbenw for the hint
* Issue #546: account linking support for webhook<br>
  Thanks to @marceloverdijk for the hint
* Issue #547: account linking support for send API
* Issue #542: greeting `thread_settings`<br>
  Thanks to @uwol for the PR
* Issue #541: quick replies not working correctly
* Issue #539: Additional change values for webhook<br>
  Thanks to @CrWEE for the PR
* Issue #540: fetch device access token with Graph API 2.7 fixed
* Issue #538: Ad types checked for usage of fields with type `object`
* Issue #537: Integration test for likes/fan_count
* Issue #536: Graph API 2.0 deprecated
* Issue #535: Targeting rule deserialisation fixed<br>
  Thanks to @hkroger for the hint

## 1.29.0 (July 28, 2016)

* Issue #391, #532 and #533: `LifeEvent` type added<br>
  Thanks to @quangpld for the PR
* Issue #522: TargetingProductAudienceSubSpec setRule not visible outside package<br>
  Thanks to @hkroger for the hint
* Issue #525: Add `Quick replies` support to `Send API`<br>
  Thanks to @alexdlaird for the PR
* Issue #524: Message object should contain a `metadata` field<br>
  Thanks to @alexdlaird for the PR
* Issue #523: `AdsImageCrops` deserialization doesn't work<br>
  Thanks to @hkroger for the hint
* Issue #528 and #527: Removed `product` from `com.restfb.types.Payment`<br>
  Thanks to @fmachado for the PR
* Issue #526: `jsonObject.keys()` as String-typed Iterator<br>
  Thanks to @leandroutn for the hint
* Issue #529: cleanup - Add unittests to utils package
* Issue #531: Missing field `publisher_platforms` in Targeting class<br>
  Thanks to @huymluu for the hint
* Issue #530: Webhooks: add unhide for all HIDE-able elements

## 1.28.0 (July 18, 2016)

* Issue #513: More Webhook ChangeValue types added<br>
  Thanks to @gnagy for the PR
* Issue #501: Error when sending smiley fixed<br>
  Thanks to @bqcuong2212 for the hint
* Issue #496: TargetingProductAudienceSubSpec serialization<br>
  Thanks to @hkroger for the hint
* Issue #518: Deserializing Lookalike Custom Audience fails<br>
  Thanks to @hkroger for the hint
* Issue #511: Adding read-receipt item to web hook.<br>
  Thanks to @alexdlaird for the PR
* Issue #514: Graph API 2.7 support
* Issue #516: Graph API 2.7 - Add missing fields to Video type
* Issue #516: Graph API 2.7 - Add missing is_webhooks_subscribed field to Page type
* Issue #512: Append classDefinition to ChangeValueFactory log warning message<br>
  Thanks to @gnagy for the PR

## 1.27.0 (July 12, 2016)

* Issue #509: Marketing API - form leads added<br>
* Issue #508: `creative_id` field removed from `AdCreative`<br>
  Thanks to @hkroger for the hint
* Issue #507: `AdCreativeLinkDataCallToAction` value cannot be deserialized<br>
  Thanks to @hkroger for the hint
* Issue #506: Add a Gitter chat badge to README.md
* Issue #505: MessagingItem"` timestamp field is always null<br>
  Thanks to @alexdlaird
* Issue #504: Messenger fallback attachments<br>
  Thanks to @alexdlaird for the PR
* Issue #503: Location Attachment and Receipt Template Improvements; Airline Templates<br>
  Thanks to @alexdlaird for the PR
* Issue #502: Fallback Attachment Fails Parsing (String, Not Object)<br>
  Thanks to @alexdlaird
* Issue #500: EVEN MORE Attachment Improvements (Plus Some Message Attributes)<br>
  Thanks to @alexdlaird
* Issue #498: Custom Audience missing lookalike specific fields<br>
  Thanks to @hkroger for the hint
* Issue #497: When creating catalog, you need undocumented password field<br>
  Thanks to @hkroger for the hint

## 1.26.0 (June 30, 2016)

* Issue #495 and #493: Messenger Attachment Improvements<br>
  Thanks to @alexdlaird for the PR
* Issue #494: Incorrect Javadoc throws declaration<br>
  Thanks to @eebbesen for the PR
* Issue #491: `is_echo` field added to `MessageItem`<br>
  Thanks to @alexdlaird for the PR
* Issue #490: `SendResponse` added as Send API Response<br>
  Thanks to @alexdlaird for the hint
* Issue #487: product catalog management API added<br>
  Thanks to @hkroger for the hint
* Issue #486: more comples structure for `rule` field<br>
  Thanks to @hkroger for the hint
* Issue #484: `TargetingProductAudienceSubSpec.retetionSeconds` changed<br>
  Thanks to @hkroger for the hint
* Issue #483: convenience constructor added to `IDName`<br>
  Thanks to @hkroger for the hint
* Issue #482: Missing Search API types added<br>
  Thanks to @hkroger for the hint
* Issue #481: Url class `OGObject` is missing `image`<br>
  Thanks to @Enalmada for the hint
* Issue #480: Sonar URL changed
* Issue #479: missing fields in `AdCreative` type<br>
  Thanks to @hkroger for the hint
* Issue #478: Error while reading `AdGroup`<br>
  Thanks to @hkroger for the hint
* Issue #476: Sonar - fix connection issues
* Issue #475: Sonar - xml indentation changed to 2 chars
* Issue #472: setting welcome message for messenger bot<br>
  Thanks to @bqcuong2212 for the hint

## 1.25.0 (June 6, 2016)

* Issue #460: Issue with TOS fields in `AdAccount`<br>
  Thanks to @hkroger
* Issue #461: Page is missing `owner_business`<br>
  Thanks to @hkroger
* Issue #462: Retrieve business userpermissions<br>
  Thanks to @hkroger
* Issue #463: Provide JsonObject to Map<String, T> conversion
* Issue #464: Custom/Lookalike Audience managment<br>
  Thanks to @hkroger
* Issue #468: getter/setter test base class improved
* Issue #469: sonar analysis added to travis.yml
* Issue #470: `pom.xml` improved - formatting and minor changes
* Issue #471: logging changed, `String.format is used`

## 1.24.0 (May 22, 2016)

* Issue #458: `FundingSourceDetails` causes an exception<br>
  Thanks to @hkroger
* Issue #457: `getLoginDialogUrl` does not support reauthentication<br>
  Thanks to @hkroger
* Issue #456: add "hide status" to webhook types
* Issue #455: Add rate limiting header
* Issue #454: Post: story tags are missing
* Issue #453: `likes` field in `Page` my be a list instead of long<br>
  Thanks to @vido88
* Issue #451: Successful login redirect contains "expires_in" instead of expires<br>
  Thanks to @Mobe91
* Issue #448: PageRating missing fields / failing parsing<br>
  Thanks to @ronaldheft for hint and PR

## 1.23.0 (May 1, 2016)

* Graph API 2.6 support
  * Issue #435: missing field `content_tags` in `Video` type
  * Issue #438: missing `PageLabel` type
  * Issue #441: `Messaging` webhook types (*Messenger Platform*)
  * Issue #443: Send Api types (*Messenger Platform*)
  * Issue #445: Device Login URL changed<br>
    Thanks to DigitalXXL
* Issue #444: bugfix - corrupted `Parameter` name<br>
  Thanks to @rheylen
* Issue #439: cleanup - change boolean to Boolean
* Issue #440: Adding `xmpp_login` extended page permission<br>
  Thanks to @alexdlaird for PR

## 1.22.0 (April 13, 2016)

* Graph API 2.6 support
  * Issue #433: `PageAdminNote` type is missing
  * Issue #431: `Pages` type `likes` renamed to `fan_count`
  * Issue #430: Post `reactions`
  * Issue #428: `Video` type has field `live_audience_count`
  * Issue #427: `Video` type new field `live_status type`
  * Issue #426: new permission `pages_messaging_phone_number`
  * Issue #425: new permission `pages_messaging`
  * Issue #437: reactions added to `Video` and `Photo` type
  * Issue #424: Version enum
* Issue #423: Missing `page post` values in webhook type<br>
  Thanks to @volkenborn for providing some JSON files
* Issue #419: add missing permission `pages_manage_instant_articles`
* Issue #418: Improve `GraphResponse` type
* Issue #415: Missing `getVideoData()` on `com.restfb.types.Message.Attachment<br>
  Thanks to @lalitg14 for the hint and the JSON

## 1.21.1 (April 2, 2016)

* Issue #409 and #410: Handled missing rating, is_draft, language fields in PageRating<br>
  Thanks to @venilnoronha for bug report and PR
* Issue #411: Custom RestFB tags added to javadoc

## 1.21.0 (March 28, 2016)

* Issue #390: `Notification` type added
* Issue #403: Missing fields in `Message Attachment`
* Issue #404: Integration test for posting a image to a group<br>
  Thanks to @kfredy for question
* Issue #405: Missing field `shares` added to `Message` type<br>
  Thanks to @ajeetsk for hint
* Issue #407: Build files updated

## 1.20.0 (March 4, 2016)

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

## 1.19.0 (February 5, 2016)

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

## 1.18.1 (January 4, 2016)

* Issue #355: copyright year changed to 2016
* Issue #354: changelog syntax changed to markdown

## 1.18.0 (December 20, 2015)

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

## 1.17.0 (November 26, 2015)

* Issue #328: Facebook Exception provides access to raw error json<br>
  Thanks to Fran Sanchez for input
* Issue #330: Bugfix: `place` field in `Event`-type changed to `Place`-type<br>
  Thanks to Matthew Gillingham for input
* Issue #331: `album` field and other fields added to `Photo`-type<br>
  Thanks to @tsurelad for input
* Issue #333: `comments` and more fields added to `Album`-type<br>
  Thanks to Paolo for input

## 1.16.0 (October 22, 2015)

* Issue #317: Marketing API improved
* Issue #324: `message_tags` field added to `Post`-type<br>
  Thanks to Miguel Sandim for input
* Issue #325: missing fields added to `Comment`-type<br>
  Thanks to Miguel Sandim for input
* Issue #322: missing fields added to `Video`-type<br>
  Thanks to @aonischenko for input

## 1.15.0 (October 8, 2015)

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

## 1.14.1 (September 1, 2015)

* Issue #252: strange events behaviour<br>
  Thanks to Paul Myburgh
* Issue #277: version enum changed - `LATEST added<br>
  Thanks to @skaluva
* Issue #286: `likes` field in `Comment` type changed<br>
  Thanks to Bogdan Neacsa

## 1.14.0 (July 18, 2015)

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

## 1.13.0 (July 8, 2015)

* Issue #261: `targeting` field missing in `Post`-type<br>
  Thanks to @Karnifexx
* Issue #262: mapping exception when using `Post` with `feed_targeting` and `target city`<br>
  Thanks to @Karnifexx
* Issue #263: `full_picture` field missing in `Post` type<br>
  Thanks to @CannyDuck

## 1.12.0 (July 5, 2015)

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

## 1.11.0 (May 28, 2015)

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

## 1.10.1 (April 24, 2015)

* Issue #223: access token parsing for Graph API 2.3 added<br>
  Thanks to @SmokyBot
* Issue #224: javadoc improvement regarding how some fields are filled<br>
  Thanks to @munwaikong

## 1.10.0 (April 22, 2015)

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

## 1.9.0 (March 25, 2015)

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

## 1.8.0 (February 13, 2015)

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

## 1.7.1 (January 16, 2015)

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

## 1.7.0 (November 7, 2014)

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

## 1.6.16 (September 22, 2014)

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

## 1.6.15 (August 29, 2014)

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

## 1.6.14 (January 27, 2014)

* Fix for regression in 1.6.13 where one DefaultFacebookClient constructor override would ignore the supplied
  WebRequestor and JsonMapper and instead use the default implementations.<br>
  Thanks to AlekseiS

## 1.6.13 (January 25, 2014)

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

## 1.6.12 (March 10, 2013)

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

## 1.6.11 (September 7, 2012)

* *New feature*: you may annotate a method in a class mapped by JsonMapper with `@JsonMappingCompleted`.
  Your method may be *private* and must take *0 parameters* or *1 JsonMapper parameter*.
  This is useful for performing one-time actions after the `JsonMapper` has completed its work, e.g. massaging
  JSON data into other formats.
* Bugfix: The `message_tags` field in `Post`-type could throw an exception when JsonMapper attempted to map it.<br>
  Thanks to Shaya Potter for reporting this.

## 1.6.10 (September 3, 2012)

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

## 1.6.9 (October 21, 2011)

* Bugfix for `Connection` page iteration.<br>
  Thanks to Takashi Kawachi for the patch.

## 1.6.8 (October 15, 2011)

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

## 1.6.7 (September 10, 2011)

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

## 1.6.6 (July 12, 2011)

* Fixes a bug introduced in 1.6.5 where facebookClient.delete(...) would throw an NPE.<br>
  Thanks to Garret Collins.

## 1.6.5 (July 4, 2011)

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

## 1.6.4 (March 5, 2011)

* Added `application` and `type` fields to `Post`.<br>
  Thanks to nelrib.
* Removed `mobilePhone` and `address` fields from `User`.<br>
  Thanks to Marcel Stoer.
* Added undocumented `properties` field to `Post` type.
  
## 1.6.3 (February 19, 2011)

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

## 1.6.2 (January 23, 2011)

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

## 1.6.1 (unofficial release)

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

## 1.6 (December 30, 2010)

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

## 1.5.4 (September 16, 2010)

* Post comment type now handles case where FB returns an empty array instead of an object type.<br>
  Thanks to Pipo, CR, S13_Alan, Igor Kabiljo, and Jakub Danilewicz.

## 1.5.3 (July 21, 2010)

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

## 1.5.2 (June 8, 2010)

* No longer require an access token for the Graph API (now have a default constructor for `DefaultFacebookClient`).<br>
  Thanks to Yoav Shapira for the enhancement request.
* Facebook changed the data in the `Photo Tag` object - updated to reflect that.<br>
  Thanks to Max for reporting. 
* Facebook added fields to the `User` object - updated to reflect that.<br>
  Thanks to fellahst and ndimiduk for reporting.
* HTTP GET operations executed by `DefaultWebRequestor` now use `httpUrlConnection.setUseCaches(false)`.<br>
  Thanks to Jumpa for reporting this.
* Special check to only URL-encode access token if it's not already URL-encoded.

## 1.5.1 (May 14, 2010)

* OAuth support broke in `DefaultLegacyFacebookClient` because Facebook changed the `token` param to `access_token`.<br>
  Thanks to Antonio Casula for the initial problem report.
  
## 1.5 (May 1, 2010)

* Added support for the new Facebook Graph API
* Old REST API now has support for the new OAuth authentication tokens
* Fix for multiqueries with empty resultsets throwing a JSON mapping exception
* `WebRequestor` now supports POSTs which perform file uploading and GETs
* The `com.restfb.json` undocumented package is now gone.  The `org.json` code is now package-private inside of `com.restfb`
* Many small bugfixes and enhancements
* Enhanced examples and documentation<br>
  Thanks to Ofer Fort, Ryan Wilson, and Alex Launi for their input.

## 1.4 (April 21, 2010)

* Added support for Java -> JSON mapping to support more complex API calls like stream.publish.<br>
  Thanks to Alex Launi and biz@layes.com for their input.

## 1.3 (March 19, 2010)

* In response to recent FB changes, JSON mapper now coerces empty JSON arrays to empty Java Strings if you're trying to map to a String field.<br>
  Thanks to Antonello Naccarato for reporting this!

## 1.2 (March 2, 2010)

* Fixed bug related to mapping JSON null to Java types
* Library source is now included in the JAR for easy browsing in your IDE

## 1.1 (February 23, 2010)

* Formal support for `fql.multiquery` API call
* Minor JSON mapping fixes and error message improvements
* Updated website with `fql.multiquery` documentation and explicit mention of non-support of session key acquisition

## 1.0 (February 2, 2010)

* Initial release
