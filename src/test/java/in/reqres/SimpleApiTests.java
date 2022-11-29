package in.reqres;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class SimpleApiTests {

    String baseUrl = "https://reqres.in";

    @Test
    void totalUsersAtPageTest() {
        given()
                .log().uri()
                .when().get(baseUrl + "/api/users?page=1")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("total", is(12));
    }

    @Test
    void userGeorgeBluthIsExist() {
        given()
                .log().uri()
                .when().get(baseUrl + "/api/users?page=1")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.first_name", hasItems("George"))
                .body("data.last_name", hasItems("Bluth"));
    }

    @Test
    void userNotFoundResponseTest() {
        given()
                .log().uri()
                .when().get(baseUrl + "/api/users/qwerty")
                .then()
                .log().body()
                .statusCode(404);
    }

    @Test
    void firstPageOfResourcesTest() {
        given()
                .log().uri()
                .when().get(baseUrl + "/api/unknown")
                .then()
                .log().body()
                .statusCode(200)
                .body("data.name", hasItems("cerulean", "fuchsia rose"));
    }

    @Test
    void resourceNotFoundResponseTest() {
        given()
                .log().uri()
                .when().get(baseUrl + "/api/unknown/qwerty")
                .then()
                .log().body()
                .statusCode(404);
    }

    @Test
    void createUsersTest() {
        String userData = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";
        given()
                .log().uri()
                .contentType(JSON)
                .body(userData)
                .when().post(baseUrl + "/api/users")
                .then()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }
}
