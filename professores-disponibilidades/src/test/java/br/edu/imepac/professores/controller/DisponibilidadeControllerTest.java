package br.edu.imepac.professores.controller;

import br.edu.imepac.professores.dto.request.DisponibilidadeRequestDTO;
import br.edu.imepac.professores.dto.request.ProfessorRequestDTO;
import br.edu.imepac.professores.dto.response.DisponibilidadeCreateResponseDTO;
import br.edu.imepac.professores.dto.response.ProfessorCreateDisponibilidadeResponseDTO;
import br.edu.imepac.professores.dto.response.ProfessorResponseDTO;
import br.edu.imepac.professores.models.services.DisponibilidadeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(DisponibilidadeController.class)
class DisponibilidadeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DisponibilidadeService disponibilidadeService;

    @Test
    void testListDisponibilidades() throws Exception {
        // Criação de uma lista de DisponibilidadeCreateResponseDTO com dados esperados
        List<DisponibilidadeCreateResponseDTO> expectedResponse = new ArrayList<>();
        expectedResponse.add(new DisponibilidadeCreateResponseDTO(1L, "Segunda-feira", "08:00", new ProfessorCreateDisponibilidadeResponseDTO(1L, "Jhon")));
        expectedResponse.add(new DisponibilidadeCreateResponseDTO(2L, "Terça-feira", "14:00", new ProfessorCreateDisponibilidadeResponseDTO(2L, "Doe")));

        // Configuração do comportamento do mock do serviço para retornar a lista de DisponibilidadeCreateResponseDTO esperada
        when(disponibilidadeService.listDisponibilidades()).thenReturn(expectedResponse);

        // Execução da requisição GET para listar as disponibilidades
        mockMvc.perform(MockMvcRequestBuilders.get("/disponibilidades").with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].diaSemana").value("Segunda-feira"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].horario").value("08:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].professor.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].professor.name").value("Jhon"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].diaSemana").value("Terça-feira"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].horario").value("14:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].professor.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].professor.name").value("Doe"));
    }

    @Test
    void testSaveDisponibilidade() throws Exception {
        // Criação de um objeto DisponibilidadeRequestDTO com dados válidos
        DisponibilidadeRequestDTO requestDTO = new DisponibilidadeRequestDTO();
        requestDTO.setProfessorId(1L);
        requestDTO.setDiaSemana("Quarta-feira");
        requestDTO.setHorario("10:00");

        // Criação de um objeto DisponibilidadeCreateResponseDTO com dados esperados
        DisponibilidadeCreateResponseDTO expectedResponseDTO = new DisponibilidadeCreateResponseDTO();
        expectedResponseDTO.setId(1L);
        expectedResponseDTO.setDiaSemana("Quarta-feira");
        expectedResponseDTO.setHorario("10:00");
        ProfessorCreateDisponibilidadeResponseDTO expectedProfessor = new ProfessorCreateDisponibilidadeResponseDTO(1L, "Jhon");
        expectedResponseDTO.setProfessor(expectedProfessor);


        // Configuração do comportamento do mock do serviço para retornar o objeto DisponibilidadeCreateResponseDTO esperado
        when(disponibilidadeService.cadastrarDisponibilidade(requestDTO)).thenReturn(expectedResponseDTO);

        // Execução da requisição POST para salvar a disponibilidade
        mockMvc.perform(MockMvcRequestBuilders.post("/disponibilidades")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.diaSemana").value("Quarta-feira"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.horario").value("10:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.professor.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.professor.name").value("Jhon"));
    }

    @Test
    void testListDisponibilidadesEmpty() throws Exception {
        // Configuração do comportamento do mock do serviço para retornar uma lista vazia de DisponibilidadeCreateResponseDTO
        when(disponibilidadeService.listDisponibilidades()).thenReturn(new ArrayList<>());

        // Execução da requisição GET para listar as disponibilidades
        mockMvc.perform(MockMvcRequestBuilders.get("/disponibilidades").with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }


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
