package br.edu.imepac.professores.integrationtests.swagger;

import br.edu.imepac.professores.config.TestConfigs;
import br.edu.imepac.professores.integrationtests.testescontainers.AbstractIntegrationTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Slf4j
@Disabled("Desabilitado paleativamente")
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
       log.info("The content is {}", content);
       assertTrue(content.contains("Swagger UI"));
    }
}
