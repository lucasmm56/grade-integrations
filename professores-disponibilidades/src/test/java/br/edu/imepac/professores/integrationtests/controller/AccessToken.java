package br.edu.imepac.professores.integrationtests.controller;

import br.edu.imepac.professores.controller.TokenController;
import br.edu.imepac.professores.integrationtests.testescontainers.AbstractIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AccessToken extends AbstractIntegrationTest {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void testTokenEndpoint() {
        TokenController.User user = new TokenController.User(
                "admin",
                "app_professores",
                "password",
                "admin_professor"
        );

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/token")
                .then()
                .statusCode(200)
                .body("access_token", notNullValue());
    }
}
