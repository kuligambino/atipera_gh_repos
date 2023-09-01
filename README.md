# **List repositories for a user**

Lists all repositories  with branches except forks for the specified user.

`GET /v1/repos`

**Parameters for "List repositories for a user"**

### Headers

`Accept string`

Setting to application/json is recommended.

### Path parameters

`username string Required`

The handle for the GitHub user account.

Example Response:
```json
[
  {
    "name": "repo1",
    "ownerLogin": "owner",
    "branches": [
      {
        "name": "example2",
        "lastCommitSha": "543e1267d68cfe13a4d27ecafc045f3466859002"
      },
      {
        "name": "example",
        "lastCommitSha": "543e1267d68cfe13a4d27ecafc045f3466859002"
      },
      {
        "name": "master",
        "lastCommitSha": "57c747aebfd58ba153531148a9d7e3dea6d74dd0"
      },
      {
        "name": "pozdro",
        "lastCommitSha": "e4cd91df21fd3f0b247d0e6d9877f5019e863baf"
      }
    ]
  },
  {
    "name": "repo2",
    "ownerLogin": "owner",
    "branches": []
  },
  {
    "name": "repo3",
    "ownerLogin": "owner",
    "branches": [
      {
        "name": "branch1",
        "lastCommitSha": "2fa69fa7be0d5082b5a28d5a853249597c630cbd"
      },
      {
        "name": "main",
        "lastCommitSha": "1658d192f3d50ab05e9d881b3693e150714d8e45"
      }
    ]
  },
  {
    "name": "repo4",
    "ownerLogin": "owner",
    "branches": [
      {
        "name": "main",
        "lastCommitSha": "6a092659b8ee5972ce1718d6eac0fb2d281a385d"
      }
    ]
  }
]
```

### HTTP response status codes for "List repositories for a user"


Status code	Description

200 OK

404 NOT FOUND -> username doesn't exist in  gh

406 NOT_ACCEPTABLE -> acceptHeader is not allowed i.e. application/xml

**Acceptance criteria:**

As an api consumer, given username and header “Accept: application/json”, I would like to list all his github repositories, which are not forks. Information, which I require in the response, is:

1. Repository Name

2. Owner Login

3. For each branch it’s name and last commit sha

As an api consumer, given not existing github user, I would like to receive 404 response in such a format:

{
“status”: ${responseCode}
“Message”: ${whyHasItHappened}
}

As an api consumer, given header “Accept: application/xml”, I would like to receive 406 response in such a format:

{
“status”: ${responseCode}
“Message”: ${whyHasItHappened}
}

**Notes:**

Please full-fill the given acceptance criteria, delivering us your best code compliant with industry standards.

Please use https://developer.github.com/v3 as a backing API

Application should have a proper README.md file