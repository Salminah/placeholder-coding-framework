package stepDefinition;

import dataHandler.DataHandler;
import commonClass.Helper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.io.*;
import java.util.ArrayList;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

public class blogStepDef {

    DataHandler dataHandler = new DataHandler();
    Helper helper = new Helper();
    RequestSpecification request;
    Response response;

    private ArrayList<Integer> getUserId;
    private JsonPath js;
    private ArrayList<Integer> postsIds;
    private boolean emailValidationResults;
    private String body;
    private String username;


    @Given("^The base API request(.*)$")
    public void thebaseAPIrequest(String url){
        RestAssured.baseURI= "https://jsonplaceholder.typicode.com";
    }

    @When("^I search the user with(.*)$")
    public void iSearchTheUserWithUsername(String username) {
        this.username = username;
        String user ="/users?username="+username;

        response = given().get(user).then().extract().response();
        body= response.getBody().asString();
        System.out.println(body);

        assertTrue(body.contains(username), "Username does not exist");
    }

    @And("The details of the username should be fetched$")
    public void theDetailsOfUsernameShouldBeFetched() {

        body= response.getBody().asString();

        assertTrue(body.contains("email"), "Response does not contain email");

        js = response.jsonPath();
        getUserId = js.get("id");

        assertTrue(body.contains(username), "Username is not the same");

    }
    @And("I search the posts written by the User$")
    public void iSearchThePostsWrittenByTheUser() {
        int userIdIndex = getUserId.get(0);

        String postUserPath = "/users/"+userIdIndex+"/posts";
        response = given().get(postUserPath).then().extract().response();

        js = response.jsonPath();
        postsIds =js.get("id");

        assertTrue(response.getBody().asString().contains("tempora rem veritatis voluptas quo dolores vero"), "Post IDs is not the same");

    }

    @Then("For each post I fetch the comments and validate if the email is in proper format$")
    public void forEachPostIFetchTheCommentsAndValidateIfTheEmailIsInProperFormat() {

        assertTrue(isEmailValid(),"The Email format is not correct");

    }

    public boolean isEmailValid () {

        for (Integer valueOfPostId : postsIds) {

            String commentsPath = "/posts/" + valueOfPostId + "/comments";
            response = given().get(commentsPath).then().extract().response();
            //String commentsBody = response.getBody().asString();

            js = response.jsonPath();
            ArrayList<String> emailFromComments = js.get("email");

            Assert.assertTrue(body.contains("email"), "The email does not exist");

            for (String validateEmail : emailFromComments) {

                emailValidationResults = helper.validateEmailAddress(validateEmail);
                String mapEmailWithResults = validateEmail + " is " + emailValidationResults;

                System.out.println(mapEmailWithResults);

//                    String dataPath = "C:\\Users\\tumil\\IdeaProjects\\placeholder-coding-framework\\results\\EmailValidation.xls";
//                    int rowNum = 0;
//                    LinkedHashSet<String> set = new LinkedHashSet<>();
//                    set.add(mapEmailWithResults);
//
//                    Iterator<String> iterator = set.iterator();
//                    while (iterator.hasNext()) {
//                        dataHandler.writeDataToExcel(dataPath, iterator.next(), rowNum++, 0);
//                    }
            }
        }
        return emailValidationResults;
    }

    @Then("I verify if (.*) exist$")
    public void iVerifyIfTheUsernameExist(String username) {

        body = response.getBody().asString();
        assertTrue(body.contains(username), "User does not exist");
    }
}


