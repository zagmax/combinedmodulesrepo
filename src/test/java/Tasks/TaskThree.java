package Tasks;

import jdk.jfr.Name;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class TaskThree {
    private final String endp = "https://petstore.swagger.io/v2/";

    @Test
    @Name("1. Verify that allows creating a User")
    public void checkUserCreate() {
        String body = "{\"id\": 0,\"username\": \"string\",\"firstName\": \"string\",\"lastName\": \"string\",\"email\": \"string\",\"password\": \"string\",\"phone\": \"string\",\"userStatus\": 0}";
       // var respo = given().body(body).when().auth().basic("user","pass").post(endp).then();//get(endp + "/login?username=test&password=test").then().statusCode(200);
        //respo.log().body();
        var response = given().body(body).when().post(endp+"user").then();//.statusCode(200);
        response.log().body();
    }
/*
                  "id": 0,
                  "username": "TheUserName",
                  "firstName": "FiName",
                  "lastName": "LaName",
                  "email": "mail@mail.ma",
                  "password": "passwordsss",
                  "phone": "+380111111111",
                  "userStatus": 0
*/

    @Test
    @Name("2. Verify that allows login as a User")
    public void checkLoginUser() {
        var response = given().when().get(endp + "user/login?username=test&password=test").then().statusCode(200);
        response.log().body();
    }

    @Test
    @Name("3. Verify that allows creating the list of Users")
    public void checkUserListCreate() {
        String body = """
[
{
"id": 0,
"username": "string",
"firstName": "string",
"lastName": "string",
"email": "string",
"password": "string",
"phone": "string",
"userStatus": 0
}
]
""";
        // var respo = given().body(body).when().auth().basic("user","pass").post(endp).then();//get(endp + "/login?username=test&password=test").then().statusCode(200);
        //respo.log().body();
        var response = given().body(body).when().post(endp+"user/createWithArray").then();//.statusCode(200);
        response.log().body();
    }

    @Test
    @Name("4. Verify that allows Log out User")
    public void checkUserLogout() {
        var response = given().when().get(endp + "user/logout").then().statusCode(200);
        response.log().body();
    }

    @Test
    @Name("5. Verify that allows adding a new Pet")
    public void checkAddingPet() {
        String head="accept: application/json";
        String head2="Content-Type: application/json";
        String body = """
{
  "id": 0,
  "category": {
    "id": 0,
    "name": "string"
  },
  "name": "doggie",
  "photoUrls": [
    "string"
  ],
  "tags": [
    {
      "id": 0,
      "name": "string"
    }
  ],
  "status": "available"
}
""";
        var response = given().body(body).when().post("https://petstore.swagger.io/v2/pet").then().log().body();//.statusCode(200);
        //response.log().body();
    }

    @Test
    @Name("6. Verify that allows updating Pet’s image")
    public void checkAddPetImage() {
    }

    @Test
    @Name("7. Verify that allows updating Pet’s name and status")
    public void checkUpdatePetNameStatus() {
    }

    @Test
    @Name("8. Verify that allows deleting Pet ")
    public void checkDeletePet() {
        var response = given().when().delete(endp + "pet/8").then().statusCode(200);
        response.log().body();
    }

}
