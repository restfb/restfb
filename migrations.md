# Migration Guide
Because we have a lot of API changes from version 1.x to 2.0 RestFB provides a migration guide, where the breaking changes can be found

## tl;dr
`Comment.Attachment` -> `StoryAttachment`
`FacebookClient.executeQuery()` -> `FacebookClient.executeFqlQuery()`
`FacebookClient.executeMultiquery()` -> `FacebookClient.executeFqlMultiquery()`

## Comment.Attachment removed
We have removed the `Comment.Attachment` type because it is replaced by 
a newer type called `StoryAttachment`. `StoryAttachment` has the same 
fields as `Comment.Attachment` but contains the target field, too. 
Therefore it is better suitable to the original Facebook JSON.

## FacebookClient#executeQuery and FacebookClient#executeMultiquery removed
The `FacebookClient` interface does not longer provide the `executeQuery` and 
`executeMultiquery` access to FQL. These methods are not longer working on the
Facebook side, so we don't need to support them further.
The `DefaultFacebookClient` does not longer contain these methods. Because some
developer still use the FQL interface (Graph API 2.0) they have to switch to the
newer (and working) `executeFqlQuery` and `executeFqlMultiquery`.
