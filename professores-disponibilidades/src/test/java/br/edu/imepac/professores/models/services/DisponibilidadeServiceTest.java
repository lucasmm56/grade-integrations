package br.edu.imepac.professores.models.services;

import br.edu.imepac.professores.dto.request.DisponibilidadeRequestDTO;
import br.edu.imepac.professores.dto.response.DisponibilidadeCreateResponseDTO;
import br.edu.imepac.professores.dto.response.ProfessorCreateDisponibilidadeResponseDTO;
import br.edu.imepac.professores.models.entities.Disponibilidade;
import br.edu.imepac.professores.models.entities.Professor;
import br.edu.imepac.professores.models.repository.DisponibilidadeRepository;
import br.edu.imepac.professores.models.repository.ProfessorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DisponibilidadeServiceTest {

    @Mock
    private DisponibilidadeRepository disponibilidadeRepository;

    @Mock
    private ProfessorRepository professorRepository;

    @InjectMocks
    private DisponibilidadeService disponibilidadeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCadastrarDisponibilidade() {
        // Dados de entrada para o teste
        DisponibilidadeRequestDTO requestDTO = new DisponibilidadeRequestDTO();
        requestDTO.setProfessorId(1L);
        requestDTO.setDiaSemana("Segunda-feira");
        requestDTO.setHorario("10:00");

        // Mock do repositório do professor
        Professor professor = new Professor();
        professor.setId(1L);
        professor.setNome("John Doe");
        when(professorRepository.findById(1L)).thenReturn(java.util.Optional.of(professor));

        // Mock do repositório de disponibilidade
        Disponibilidade savedDisponibilidade = new Disponibilidade();
        savedDisponibilidade.setId(1L);
        savedDisponibilidade.setDiaSemana("Segunda-feira");
        savedDisponibilidade.setHorario("10:00");
        savedDisponibilidade.setProfessor(professor);
        when(disponibilidadeRepository.save(any(Disponibilidade.class))).thenReturn(savedDisponibilidade);

        // Execução da função a ser testada
        DisponibilidadeCreateResponseDTO responseDTO = disponibilidadeService.cadastrarDisponibilidade(requestDTO);

        // Verificações
        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("Segunda-feira", responseDTO.getDiaSemana());
        assertEquals("10:00", responseDTO.getHorario());

        ProfessorCreateDisponibilidadeResponseDTO professorDto = responseDTO.getProfessor();
        assertNotNull(professorDto);
        assertEquals(1L, professorDto.getId());
        assertEquals("John Doe", professorDto.getName());

        verify(professorRepository, times(1)).findById(1L);
        verify(disponibilidadeRepository, times(1)).save(any(Disponibilidade.class));
    }

    @Test
    void testCadastrarDisponibilidadeProfessorNaoEncontrado() {
        // Dados de entrada para o teste
        DisponibilidadeRequestDTO requestDTO = new DisponibilidadeRequestDTO();
        requestDTO.setProfessorId(1L);
        requestDTO.setDiaSemana("Segunda-feira");
        requestDTO.setHorario("10:00");

        // Mock do repositório do professor
        when(professorRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Execução da função a ser testada e verificação da exceção
        assertThrows(EntityNotFoundException.class, () -> {
            disponibilidadeService.cadastrarDisponibilidade(requestDTO);
        });

        verify(professorRepository, times(1)).findById(1L);
        verify(disponibilidadeRepository, never()).save(any(Disponibilidade.class));
    }

    @Test
     void testListDisponibilidades() {
        // Cria uma lista de disponibilidades simulada
        List<Disponibilidade> disponibilidades = new ArrayList<>();
        disponibilidades.add(new Disponibilidade(1L, new Professor(), "Segunda-feira", "08:00"));
        disponibilidades.add(new Disponibilidade(2L, new Professor(), "Terça-feira", "10:00"));

        // Configura o comportamento do mock do repositório
        when(disponibilidadeRepository.findAll()).thenReturn(disponibilidades);

        // Chama o método a ser testado
        List<DisponibilidadeCreateResponseDTO> responseDTOs = disponibilidadeService.listDisponibilidades();

        // Verifica se a lista de DTOs de resposta foi retornada corretamente
        Assertions.assertEquals(disponibilidades.size(), responseDTOs.size());
        for (int i = 0; i < disponibilidades.size(); i++) {
            Disponibilidade disponibilidade = disponibilidades.get(i);
            DisponibilidadeCreateResponseDTO responseDTO = responseDTOs.get(i);
            Assertions.assertEquals(disponibilidade.getId(), responseDTO.getId());
            Assertions.assertEquals(disponibilidade.getDiaSemana(), responseDTO.getDiaSemana());
            Assertions.assertEquals(disponibilidade.getHorario(), responseDTO.getHorario());
        }
    }

    @Test
     void testListDisponibilidadesEmpty() {
        // Configura o comportamento do mock do repositório para retornar uma lista vazia
        when(disponibilidadeRepository.findAll()).thenReturn(new ArrayList<>());

        // Chama o método a ser testado
        List<DisponibilidadeCreateResponseDTO> responseDTOs = disponibilidadeService.listDisponibilidades();

        // Verifica se a lista de DTOs de resposta está vazia
        Assertions.assertTrue(responseDTOs.isEmpty());
    }
}
