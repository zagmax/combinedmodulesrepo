package Tasks;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.log4j.Log4j2;
import manager.PageFactoryManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@Log4j2
public class TaskThreeTests {
    private final Properties PROPERTIES = PageFactoryManager.getPROPERTIES();

    @BeforeAll
    public static void profileSetUp() {
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
    }

    @Test
    @DisplayName("1. Verify that allows creating a User")
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
        var response = given().header("Content-type", "application/json").and().body(body).when().post(PROPERTIES.getProperty("swaggerPage") + "user").then().statusCode(200);
        log.info("user creation test executed \n" + "Response: " + response.extract().asString());
        checkLoginUser("UNAME", "passwordsss");
    }

    @Test
    @DisplayName("2. Verify that allows login as a User")
    public void testLoginUser() {
        checkLoginUser("", "");
    }

    public void checkLoginUser(String user, String pass) {
        if (user.isEmpty() && pass.isEmpty()) {
            user = "test";
            pass = "test";
        }
        var response = given().
                when().
                get(PROPERTIES.getProperty("swaggerPage") + "user/login?username=" + user + "&password=" + pass).
                then().
                assertThat().
                statusCode(200).
                body("message", containsString("logged in user session"));
        log.info("logging in user check executed \n" + "Response:" + response.extract().asString());
    }

    @Test
    @DisplayName("3. Verify that allows creating the list of Users")
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
        var response = given().header("Content-type", "application/json").and().body(body).when().post(PROPERTIES.getProperty("swaggerPage") + "user/createWithList").then().statusCode(200);
        log.info("list of users creation test executed \n" + "Response:" + response.extract().asString());
        checkLoginUser("string1", "string1");
        checkLoginUser("string2", "string2");
    }

    @Test
    @DisplayName("4. Verify that allows Log out User")
    public void checkUserLogout() {
        var response = given().when()
                .get(PROPERTIES.getProperty("swaggerPage") + "user/logout")
                .then()
                .statusCode(200)
                .assertThat()
                .body("message", containsString("ok"));
        log.info("logging out test executed \n" + "Response:" + response.extract().asString());
    }

    @Test
    @DisplayName("5. Verify that allows adding a new Pet")
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
        var response = given().header("Content-type", "application/json").and().body(body)
                .when()
                .post(PROPERTIES.getProperty("swaggerPage") + "pet")
                .then().statusCode(200);
        given().when().get(PROPERTIES.getProperty("swaggerPage") + "pet/10").then().assertThat().body("name", equalTo("NEW DOG")).and().body("id", equalTo(10));
        log.info("adding pet test executed \n" + "Response:" + response.extract().asString());

    }

    @Test
    @DisplayName("6. Verify that allows updating Pet’s image")
    public void checkAddPetImage() {
        String body = """
                {
                "id": 5,
                "category": {
                  "id": 0,
                  "name": "dog"
                },
                "name": "Doggo the test",
                "photoUrls":["https://hips.hearstapps.com/hmg-prod/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg"]
                ,
                "tags": [
                  {
                    "id": 0,
                    "name": "string"
                  }
                ],
                "status": "OUT OF STOCK"
                }
                """;
        var response = given().header("Content-type", "application/json").and().body(body)
                .when()
                .put(PROPERTIES.getProperty("swaggerPage") + "pet")
                .then().statusCode(200);

        given().when().get(PROPERTIES.getProperty("swaggerPage") + "pet/5")
                .then().log().body()
                .assertThat()
                .body("id", equalTo(5)).and()
                .body("photoUrls.get(0)", containsString(PROPERTIES.getProperty("imageLink")));


        log.info("adding link to pet imagine test executed \n" + "Response:" + response.extract().asString());
    }

    @Test
    @DisplayName("7. Verify that allows updating Pet’s name and status")
    public void checkUpdatePetNameStatus() {
        String body = """
                {
                  "id": 1,
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
        var response = given().header("Content-type", "application/json").and().body(body)
                .when()
                .put(PROPERTIES.getProperty("swaggerPage") + "pet")
                .then().statusCode(200);

        given().when().get(PROPERTIES.getProperty("swaggerPage") + "pet/1")
                .then()
                .assertThat().body("name", equalTo("doggieNOT"))
                .and().body("status", equalTo("OUT OF STOCK"));

        log.info("update pet status test executed \n" + "Response: " + response.extract().asString());
    }

    @Test
    @DisplayName("8. Verify that allows deleting Pet ")
    public void checkDeletePet() {
        checkAddingPet();
        var response = given().when()
                .delete(PROPERTIES.getProperty("swaggerPage") + "pet/10")
                .then().statusCode(200)
                .assertThat().body("message", equalTo("10"));
        log.info("deleting a pet test executed \n" + "Response: " + response.extract().asString());
    }

}
