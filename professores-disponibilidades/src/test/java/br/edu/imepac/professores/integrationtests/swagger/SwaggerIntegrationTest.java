package br.edu.imepac.professores.integrationtests.swagger;

import br.edu.imepac.professores.config.TestConfigs;
import br.edu.imepac.professores.integrationtests.testescontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest  extends AbstractIntegrationTest {

    @Test
    @DisplayName("JUnit test for Should Display Swagger UI Page")
    void testShouldDisplaySwaggerUiPage(){
       var content =  given()
                .basePath("swagger-ui/index.html")
                .port(TestConfigs.SERVER_PORT)
                .when()
                    .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
       assertTrue(content.contains("Swagger UI"));
    }
}
