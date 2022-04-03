package commonClass;

import io.restassured.response.Response;

import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;


public class Helper {

    Response response;

    public boolean validateEmailAddress(String email){
        if (email ==null || email.isEmpty()){
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);
        if (pattern.matcher(email).matches()){
            return true;
        }else {
            return false;
        }
    }

    public Response responseInfo(String userNam){

        String user ="/users?username="+userNam ;
        response = given().get(user).then().extract().response();

        return response;
    }
}
