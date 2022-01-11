# Contributing to RestFB

Contributing code is an essential part of open source and we try to make this as easy as possible. There are several ways you can contribute to RestFB.  Here are some guidelines for creating new issues and sending us pull requests. Please read them carefully before contributing.

## How to create a new issue

We like new issues and are very keen to hear your problems, ideas and proposals. After analyzing them, they are categorized and assigned to a milestone. To make this part easier you should give us some information. Please read the checklist and try to mark as many entries as possible.

**Issue checklist:**
* Check the closed issues and don't open duplicates
* Explain your idea or problem in plain English
* Provide a JSON snippet if possible (values may be obfuscated but similar to the original)
* Tell us why this is beneficial and what the advantage is for the RestFB users
* Explain your use case, it's easier to understand a proposal if we have some background knowledge
* If your request is not working in RestFB, check it in the Facebook Graph API explorer first

## How to create a pull request

Pull requests (in short: PR) are a very important way to contribute code to the RestFB library. We have guidelines for sending us a PR, because changes to the source code are even more fundamental than sending a new issue. This is an overview of our prerequisites. Your PR should fulfill our mandatory prerequisites, but the optionals are big plus and make it much easier to merge a PR in the RestFB code. The optional items are written in italic.

**PR checklist:**
* The code must be formatted with our code formatter (have a look at the [misc folder](https://github.com/restfb/restfb/tree/master/misc/eclipse))
* The code should conform to our general design standards (if you feel it's necessary to go against the grain, please ask us first!)
* Existing unit tests have to run without error (we use [Travis CI](https://travis-ci.org/restfb/restfb))
* The pull request should be mergeable
* Explain why you made the change and why it's beneficial
* *jUnit tests are a big plus*
* *The pull request should be based on the `dev` branch*
* *Group and squash your commits, fewer are better*

If your issue or your PR doesn't meet a condition, don't be afraid. We are not dogmatic with these rules and you can and should send us your contribution anyway. Be prepared to answer upcoming questions :smirk: