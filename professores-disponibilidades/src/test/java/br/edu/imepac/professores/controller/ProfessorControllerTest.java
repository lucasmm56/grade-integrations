package br.edu.imepac.professores.controller;

import br.edu.imepac.professores.dto.request.ProfessorRequestDTO;
import br.edu.imepac.professores.dto.response.ProfessorResponseDTO;
import br.edu.imepac.professores.models.services.interfaces.TeacherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.mockito.Mockito.when;

@WebMvcTest(ProfessorController.class)
@AutoConfigureMockMvc
class ProfessorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeacherService teacherService;

    @Test
     void testSaveTeacher() throws Exception {
        // Criação de um objeto ProfessorRequestDTO com dados válidos
        ProfessorRequestDTO requestDTO = new ProfessorRequestDTO();
        requestDTO.setNome("John Doe");
        requestDTO.setEmail("johndoe@example.com");

        // Criação de um objeto ProfessorResponseDTO com dados esperados
        ProfessorResponseDTO expectedResponseDTO = new ProfessorResponseDTO();
        expectedResponseDTO.setId(1L);
        expectedResponseDTO.setNome("John Doe");
        expectedResponseDTO.setEmail("johndoe@example.com");

        // Configuração do comportamento do mock do serviço para retornar o objeto ProfessorResponseDTO esperado
        when(teacherService.createTeacher(requestDTO)).thenReturn(expectedResponseDTO);

        // Execução da requisição POST para salvar o professor
        mockMvc.perform(MockMvcRequestBuilders.post("/professores")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("johndoe@example.com"));
    }

    @Test
    void testUpdateTeacher() throws Exception {
        // Criação de um objeto ProfessorRequestDTO com dados válidos para atualização
        ProfessorRequestDTO requestDTO = new ProfessorRequestDTO();
        requestDTO.setNome("John Doe");
        requestDTO.setEmail("johndoe@example.com");

        // Criação de um objeto ProfessorResponseDTO com dados esperados após a atualização
        ProfessorResponseDTO expectedResponseDTO = new ProfessorResponseDTO();
        expectedResponseDTO.setId(1L);
        expectedResponseDTO.setNome("John Doe");
        expectedResponseDTO.setEmail("johndoe@example.com");

        // Configuração do comportamento do mock do serviço para retornar o objeto ProfessorResponseDTO esperado
        when(teacherService.updateTeacher(1L, requestDTO)).thenReturn(expectedResponseDTO);

        // Execução da requisição PUT para atualizar o professor
        mockMvc.perform(MockMvcRequestBuilders.put("/professores/1")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("johndoe@example.com"));
    }

    @Test
     void testListProfessores() throws Exception {
        // Criação de uma lista de ProfessorResponseDTO com dados esperados
        List<ProfessorResponseDTO> expectedProfessores = new ArrayList<>();
        ProfessorResponseDTO professor1 = new ProfessorResponseDTO();
        professor1.setId(1L);
        professor1.setNome("John Doe");
        professor1.setEmail("johndoe@example.com");
        expectedProfessores.add(professor1);
        ProfessorResponseDTO professor2 = new ProfessorResponseDTO();
        professor2.setId(2L);
        professor2.setNome("Jane Smith");
        professor2.setEmail("janesmith@example.com");
        expectedProfessores.add(professor2);

        // Configuração do comportamento do mock do serviço para retornar a lista de ProfessorResponseDTO esperada
        when(teacherService.listTeachers()).thenReturn(expectedProfessores);

        // Execução da requisição GET para listar os professores
        mockMvc.perform(MockMvcRequestBuilders.get("/professores")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("johndoe@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nome").value("Jane Smith"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email").value("janesmith@example.com"));
    }

    @Test
    void testListProfessoresListaVazia() throws Exception {
        // Criação de uma lista vazia de ProfessorResponseDTO
        List<ProfessorResponseDTO> expectedProfessores = new ArrayList<>();

        // Configuração do comportamento do mock do serviço para retornar a lista vazia
        when(teacherService.listTeachers()).thenReturn(expectedProfessores);

        // Execução da requisição GET para listar os professores
        mockMvc.perform(MockMvcRequestBuilders.get("/professores")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }

    @Test
     void testSaveTeacherDadosInvalidos() throws Exception {
        // Criação de um objeto ProfessorRequestDTO com dados inválidos
        ProfessorRequestDTO requestDTO = new ProfessorRequestDTO();
        requestDTO.setNome(""); // Nome vazio
        requestDTO.setEmail("johndoe@example.com");

        // Execução da requisição POST para salvar o professor
        mockMvc.perform(MockMvcRequestBuilders.post("/professores")
                       .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    // Método auxiliar para converter um objeto para JSON
    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
