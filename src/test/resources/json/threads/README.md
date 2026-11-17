# Threads mapping fixtures

The following fixtures are synthetic field-coverage data, not captured API responses:

* `media-with-additional-fields.json`
* `reply-with-additional-fields.json`
* `additional-fields-null.json`
* `ghost-posts.json` (including an invented future status to test forward compatibility)

They exercise individual mappings and optional values; their field combinations do not imply that every endpoint returns all fields together.

Field coverage is based on Meta's documentation:

* [Retrieve user posts](https://developers.facebook.com/documentation/threads/retrieve-and-discover-posts/retrieve-posts)
* [Media retrieval reference](https://developers.facebook.com/documentation/threads/reference/media-retrieval)
* [Retrieve user replies](https://developers.facebook.com/documentation/threads/retrieve-and-manage-replies/retrieve-replies)
* [Replies and conversations](https://developers.facebook.com/documentation/threads/retrieve-and-manage-replies/replies-and-conversations)
