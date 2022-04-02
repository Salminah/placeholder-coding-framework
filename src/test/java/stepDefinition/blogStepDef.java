package stepDefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;


public class blogStepDef {

    RequestSpecification request;
    Response response;
    private ArrayList<Integer> getUserId;
    private JsonPath js;
    private ArrayList<Integer> postsIds;


    @Given("^The base API request(.*)$")
    public void thebaseAPIrequest(String url){
        RestAssured.baseURI= url;
    }

    @When("^I search the user with(.*)$")
    public void iSearchTheUserWithUsername(String username) {
        String user ="/users?username="+username;

        response = given().get(user).then().extract().response();
        System.out.println(response.getBody().asString());

    }

    @And("The details of the username should be fetched$")
    public void theDetailsOfUsernameShouldBeFetched() {

        String body= response.getBody().asString();
        assertEquals(body.contains("email"),true,"Response does not contain email");

        JsonPath js = response.jsonPath();
        this.js = js;
        ArrayList<Integer> getUserId = js.get("id");
         this.getUserId = getUserId;

        System.out.println("The UserId is: " + getUserId);

    }
    @And("I search the posts written by the User$")
    public void iSearchThePostsWrittenByTheUser() {
        int userIdIndex = getUserId.get(0);

        String postUserPath = "/users/"+userIdIndex+"/posts";

        response = given().get(postUserPath).then().extract().response();

        JsonPath js = response.jsonPath();
        ArrayList<Integer> postsIds =js.get("id");

        this.postsIds = postsIds;

    }

    @And("For each post I fetch the comments$")
    public void forEachPostIFetchTheComments() throws IOException {

        for(Integer valueOfPostId : postsIds){

            String commentsPath = "/posts/" +valueOfPostId+ "/comments";
            response = given().get(commentsPath).then().extract().response();
            String commentsBody = response.getBody().asString();
            JsonPath js = response.jsonPath();
            ArrayList<String> emailFromComments =  js.get("email");

            int rowNum = 0;
            for(String validateEmail : emailFromComments){

                String emailValidationResults = validateEmailAddress(validateEmail);
                System.out.println(validateEmail +" is " + emailValidationResults);


                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet1 = workbook.createSheet("Sheet1");
                CreationHelper createHelper = workbook.getCreationHelper();

                Row row = sheet1.createRow(rowNum);

                        row.createCell(0).setCellValue(validateEmail);
                        row.createCell(1).setCellValue(emailValidationResults);

                File file = new File("C:\\Users\\tumil\\IdeaProjects\\placeholder-coding-framework\\results\\EmailValidation.xls");
                FileOutputStream fileOutput = new FileOutputStream(file);
                workbook.write(fileOutput);
                fileOutput.close();
                workbook.close();


            }

        }

    }

    @Then("Validate if the email in the comment section are in the proper format$")
    public void validateIfTheEmailInTheCommentSectionAreInTheProperFormat()throws IOException {

    }
     public static String validateEmailAddress(String email){
        if (email ==null || email.isEmpty()){
            return "Invalid";
        }
         String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                                         "[a-zA-Z0-9_+&*-]+)*@" +
                                            "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

         Pattern pattern = Pattern.compile(emailRegex);
         if (pattern.matcher(email).matches()){
             return "Valid";
         }else {
             return "Invalid";
         }
     }
}


