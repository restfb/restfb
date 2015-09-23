# Migration Guide
Because we have a lot of API changes from version 1.x to 2.0 RestFB provides a migration guide, where the breaking changes can be found

## tl;dr
`Comment.Attachment` -> `StoryAttachment`

## Comment.Attachment removed
We have removed the `Comment.Attachment` type because it is replaced by 
a newer type called `StoryAttachment`. `StoryAttachment` has the same 
fields as `Comment.Attachment` but contains the target field, too. 
Therefore it is better suitable to the original Facebook JSON. 