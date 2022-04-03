@User_Blog_comments

  Feature:Validate the comments for the post made by User

    @Retrieve_User
    Scenario Outline: Validate the email address from the comments made by the user on each post
      Given The base API request URL
      When I search the user with<username>
      And The details of the username should be fetched
      And I search the posts written by the User
      Then For each post I fetch the comments and validate if the email is in proper format

      Examples:
        | username |
        | Delphine |

    @VerifyUserExist
    Scenario Outline: Verify if the user exist
      Given The base API request URL
      When I search the user with<username>
      Then I verify if <username> exist

      Examples:
        | username  |
        | Mmakgwale |

