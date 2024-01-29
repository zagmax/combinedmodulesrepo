package Tasks;

import jdk.jfr.Name;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class TaskThreeTests {
    private final String endp = "https://petstore.swagger.io/v2/";

    @Test
    @Name("1. Verify that allows creating a User")
    public void checkUserCreate() {
        String body = """
                {
                  "id": 0,
                  "username": "UNAME",
                  "firstName": "FNAME",
                  "lastName": "LNAME",
                  "email": "mail@mail.ma",
                  "password": "passwordsss",
                  "phone": "+410101010",
                  "userStatus": 0
                }
                """;
        var response = given().header("Content-type", "application/json").and().body(body).when().post(endp + "user").then().statusCode(200);
        response.log().body();
    }

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
                "username": "string0",
                "firstName": "string0",
                "lastName": "string0",
                "email": "string0",
                "password": "string0",
                "phone": "string0",
                "userStatus": 0
                }
                ,
                {
                "id": 1,
                "username": "string1",
                "firstName": "string1",
                "lastName": "string1",
                "email": "string1",
                "password": "string1",
                "phone": "string1",
                "userStatus": 1
                }
                ]
                """;
        var response = given().header("Content-type", "application/json").and().body(body).when().post(endp + "user/createWithList").then().statusCode(200);
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
        String body = """
                {
                  "id": 10,
                  "category": {
                    "id": 1,
                    "name": "string"
                  },
                  "name": "NEW DOG",
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
        var response = given().header("Content-type", "application/json").and().body(body).when().post(endp + "pet").then().statusCode(200);
        response.log().body();
    }

    @Test
    @Name("6. Verify that allows updating Pet’s image")
    public void checkAddPetImage() {
        String body = """
                {
                "id": 5,
                "category": {
                  "id": 0,
                  "name": "dog"
                },
                "name": "Doggo the test",
                "photoUrls": [
                  "https://hips.hearstapps.com/hmg-prod/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg"
                ],
                "tags": [
                  {
                    "id": 0,
                    "name": "string"
                  }
                ],
                "status": "OUT OF STOCK"
                }
                """;
        var response = given().header("Content-type", "application/json").and().body(body).when().put(endp + "pet").then().statusCode(200);
        response.log().body();
    }

    @Test
    @Name("7. Verify that allows updating Pet’s name and status")
    public void checkUpdatePetNameStatus() {
        String body = """
                {
                  "id": 0,
                  "category": {
                    "id": 0,
                    "name": "string"
                  },
                  "name": "doggieNOT",
                  "photoUrls": [
                    "string"
                  ],
                  "tags": [
                    {
                      "id": 0,
                      "name": "string"
                    }
                  ],
                  "status": "OUT OF STOCK"
                }
                """;
        var response = given().header("Content-type", "application/json").and().body(body).when().put(endp + "pet").then().statusCode(200);
        response.log().body();
    }

    @Test
    @Name("8. Verify that allows deleting Pet ")
    public void checkDeletePet() {
        checkAddingPet();
        var response = given().when().delete(endp + "pet/10").then().statusCode(200);
        response.log().body();
    }

}
