@User_Blog_comments

  Feature: Validate the comments for the post made by User

    @Retrieve_User
    Scenario Outline: Validate the email address from the comments made by the user on each post
      Given The base API request<URL>
      When I search the user with<username>
      And The details of the username should be fetched
      And I search the posts written by the User
      And For each post I fetch the comments
      Then Validate if the email in the comment section are in the proper format

      Examples:
      |URL                                  | username |
      | https://jsonplaceholder.typicode.com| Delphine |