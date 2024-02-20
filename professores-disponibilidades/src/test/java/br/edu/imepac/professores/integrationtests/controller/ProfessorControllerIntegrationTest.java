package br.edu.imepac.professores.integrationtests.controller;


import br.edu.imepac.professores.config.TestConfigs;
import br.edu.imepac.professores.dto.request.ProfessorRequestDTO;
import br.edu.imepac.professores.dto.response.ProfessorResponseDTO;
import br.edu.imepac.professores.integrationtests.testescontainers.AbstractIntegrationTest;
import br.edu.imepac.professores.models.entities.Disponibilidade;
import br.edu.imepac.professores.models.entities.Professor;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProfessorControllerIntegrationTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;

    private static ObjectMapper objectMapper;

    private static ProfessorRequestDTO professorRequest;

    private static Professor professor;
    private static Professor professorUpdated;
    private static List<Disponibilidade> disponibilidades;

    private Long professorId;



    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();

        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        specification = new RequestSpecBuilder()
                .setBasePath("professores")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

         disponibilidades = new ArrayList<>();
        professor = new Professor(1L, "Lucas", "lucas@gmail.com", disponibilidades );
        professorUpdated = new Professor(1L, "Lucas Updated", "lucas_updated@gmail.com", disponibilidades );
        professorRequest = new ProfessorRequestDTO("Lucas", "lucas@gmail.com");
    }

    @Test
    @Order(1)
    void integrationTestGivenProfessorObject_when_CreateOneTeacher_ShouldReturnAProfessorObject(){
        var response = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(professorRequest)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .as(ProfessorResponseDTO.class);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(professorRequest.getNome(), response.getNome());
        assertEquals(professorRequest.getEmail(), response.getEmail());
        assertNull(response.getDisponibilidades());

        professorId = response.getId();
    }

    @Test
    @Order(2)
    void integrationTestGivenProfessorObject_when_Update_ShouldReturnUpdatedProfessor() {
        ProfessorRequestDTO updatedProfessorRequest = new ProfessorRequestDTO("Lucas Updated", "lucas_updated@gmail.com");

        var response = given().spec(specification)
                .pathParam("id", professor.getId())
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(updatedProfessorRequest)
                .when()
                .put("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(ProfessorResponseDTO.class);

        assertNotNull(response);
        assertEquals(professor.getId(), response.getId());
        assertEquals(updatedProfessorRequest.getNome(), response.getNome());
        assertEquals(updatedProfessorRequest.getEmail(), response.getEmail());
    }

    @Test
    @Order(3)
    void integrationTestGivenProfessorObject_when_findById_thenReturnAProfessorObject(){
        var response = given().spec(specification)
                .pathParam("id", professor.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(ProfessorResponseDTO.class);

        assertNotNull(response);
        assertEquals(professorUpdated.getId(), response.getId());
        assertEquals(professorUpdated.getNome(), response.getNome());
        assertEquals(professorUpdated.getEmail(), response.getEmail());
        assertEquals(professorUpdated.getDisponibilidades(), response.getDisponibilidades());
    }

    @Test
    @Order(4)
    void itegrationTestGivenListAllProfessores_when_findAll_thenReturnAllList(){
        ProfessorRequestDTO anotherProfessor = new ProfessorRequestDTO("Ana", "ana@gmail.com");

        given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(anotherProfessor)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .as(ProfessorResponseDTO.class);

        var response = given().spec(specification)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(List.class);

        assertNotNull(response);
        assertEquals("Lucas", professor.getNome());
        assertEquals("Ana", anotherProfessor.getNome());
        assertEquals(2, response.size());
    }


    @Test
    @Order(5)
    void integrationTestGivenProfessorObject_when_delete_ShouldReturnNoContent(){
        given().spec(specification)
                .pathParam("id", professor.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);
    }


}
