# Migration Guide
Because we have a lot of API changes from version 1.x to 2.0 RestFB provides a migration guide, where the breaking changes can be found

## tl;dr
* `Comment.Attachment` -> `StoryAttachment`
* `FacebookClient.executeQuery()` -> `FacebookClient.executeFqlQuery()`
* `FacebookClient.executeMultiquery()` -> `FacebookClient.executeFqlMultiquery()`
* `User.Picture` -> `ProfilePictureSource`
* `Event.getPicture()` return type `String` -> `Event.getPicture()` return type `ProfilePictureSource`
* `Event.getVenue()` return type `Venue` -> returns `Location` now
* `Group.getVenue()` return type `Venue` -> returns `Location` now

## Comment.Attachment removed
We have removed the `Comment.Attachment` type because it is replaced by 
a newer type called `StoryAttachment`. `StoryAttachment` has the same 
fields as `Comment.Attachment` but contains the target field, too. 
Therefore it is better suitable to the original Facebook JSON.

## `FacebookClient#executeQuery` and `FacebookClient#executeMultiquery` removed
The `FacebookClient` interface does not longer provide the `executeQuery` and 
`executeMultiquery` access to FQL. These methods are not longer working on the
Facebook side, so we don't need to support them further.
The `DefaultFacebookClient` does not longer contain these methods. Because some
developer still use the FQL interface (Graph API 2.0) they have to switch to the
newer (and working) `executeFqlQuery` and `executeFqlMultiquery`.

## User.Picture removed
We had to remove the `User.Picture` type because we drop the
compatibility. The picture field is now the `ProfilePictureSource`
type.

## Event.getPicture() returns `ProfilePictureSource` instead of `String`
The `Event.getPicture()` returned the image url as `String`. We changed this because
Facebook provides more information than only a `String`. The `ProfilePictureSource` is
a better choice.

## `Venue` replaced with `Location` type
The `Group` and the `Event` type contain a venue but this is the same type as `Location`.
So we replaced it and don't need to take care of two different java entities.
