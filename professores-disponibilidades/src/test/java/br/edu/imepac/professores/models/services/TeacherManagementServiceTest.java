package br.edu.imepac.professores.models.services;

import br.edu.imepac.professores.dto.request.ProfessorRequestDTO;
import br.edu.imepac.professores.dto.response.DisponibilidadeResponseDTO;
import br.edu.imepac.professores.dto.response.ProfessorResponseDTO;
import br.edu.imepac.professores.models.entities.Disponibilidade;
import br.edu.imepac.professores.models.entities.Professor;
import br.edu.imepac.professores.models.repository.ProfessorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherManagementServiceTest {

    @Mock
    private ProfessorRepository professorRepository;

    @InjectMocks
    private TeacherManagementService teacherService;


    @Test
    void testCadastrarProfessor() {
        // Criação do objeto de requisição
        ProfessorRequestDTO requestDTO = new ProfessorRequestDTO();
        requestDTO.setNome("John Doe");
        requestDTO.setEmail("johndoe@example.com");

        // Criação do objeto de professor salvo
        Professor savedProfessor = new Professor();
        savedProfessor.setId(1L);
        savedProfessor.setNome("John Doe");
        savedProfessor.setEmail("johndoe@example.com");

        // Configuração do comportamento do mock do repositório
        when(professorRepository.save(Mockito.any(Professor.class))).thenReturn(savedProfessor);

        // Execução do método cadastrarProfessor
        ProfessorResponseDTO responseDTO = teacherService.createTeacher(requestDTO);

        // Verificação dos resultados
        assertEquals(savedProfessor.getId(), responseDTO.getId());
        assertEquals(savedProfessor.getNome(), responseDTO.getNome());
        assertEquals(savedProfessor.getEmail(), responseDTO.getEmail());
    }

    @Test
    void testEditarProfessor() {
        // Criação do objeto de requisição
        ProfessorRequestDTO requestDTO = new ProfessorRequestDTO();
        requestDTO.setNome("John Doe");
        requestDTO.setEmail("johndoe@example.com");

        // Criação do objeto de professor existente
        Professor existingProfessor = new Professor();
        existingProfessor.setId(1L);
        existingProfessor.setNome("Existing Professor");
        existingProfessor.setEmail("existingprof@example.com");

        // Simulação do comportamento do repositório ao encontrar o professor existente
        when(professorRepository.findById(1L)).thenReturn(Optional.of(existingProfessor));

        // Simulação do comportamento do repositório ao salvar o professor atualizado
        when(professorRepository.save(Mockito.any(Professor.class))).thenAnswer(invocation -> {
            Professor updatedProfessor = invocation.getArgument(0);
            updatedProfessor.setId(existingProfessor.getId()); // Mantém o mesmo ID
            return updatedProfessor;
        });

        // Execução do método editarProfessor
        ProfessorResponseDTO responseDTO = teacherService.updateTeacher(1L, requestDTO);

        // Verificação dos resultados
        assertEquals(existingProfessor.getId(), responseDTO.getId());
        assertEquals(requestDTO.getNome(), responseDTO.getNome());
        assertEquals(requestDTO.getEmail(), responseDTO.getEmail());
    }

    @Test
    void testListarProfessores() {
        // Criação de uma lista de professores fictícia
        List<Professor> professores = new ArrayList<>();
        Professor professor1 = new Professor();
        professor1.setId(1L);
        professor1.setNome("John Doe");
        professor1.setEmail("johndoe@example.com");
        List<Disponibilidade> disponibilidades1 = new ArrayList<>();
        Disponibilidade disponibilidade1 = new Disponibilidade();
        disponibilidade1.setId(1L);
        disponibilidade1.setDiaSemana("Segunda-feira");
        disponibilidade1.setHorario("08:00 - 10:00");
        disponibilidades1.add(disponibilidade1);
        professor1.setDisponibilidades(disponibilidades1);
        professores.add(professor1);

        // Configuração do comportamento do mock do repositório
        when(professorRepository.findAll()).thenReturn(professores);

        // Execução do método listarProfessores
        List<ProfessorResponseDTO> responseDTOs = teacherService.listTeachers();

        // Verificação dos resultados
        assertEquals(1, responseDTOs.size());

        ProfessorResponseDTO responseDTO = responseDTOs.get(0);
        assertEquals(professor1.getId(), responseDTO.getId());
        assertEquals(professor1.getNome(), responseDTO.getNome());
        assertEquals(professor1.getEmail(), responseDTO.getEmail());

        List<DisponibilidadeResponseDTO> disponibilidadeDTOs = responseDTO.getDisponibilidades();
        assertEquals(1, disponibilidadeDTOs.size());

        DisponibilidadeResponseDTO disponibilidadeDTO = disponibilidadeDTOs.get(0);
        assertEquals(disponibilidade1.getId(), disponibilidadeDTO.getId());
        assertEquals(disponibilidade1.getDiaSemana(), disponibilidadeDTO.getDiaSemana());
        assertEquals(disponibilidade1.getHorario(), disponibilidadeDTO.getHorario());
    }

    @Test
    void testListarProfessoresListaVazia() {
        // Configuração do comportamento do mock do repositório para retornar uma lista vazia
        when(professorRepository.findAll()).thenReturn(new ArrayList<>());

        // Execução do método listarProfessores
        List<ProfessorResponseDTO> responseDTOs = teacherService.listTeachers();

        // Verificação dos resultados
        assertEquals(0, responseDTOs.size());
    }

    @Test
     void testCadastrarProfessorErro() {
        // Criação de um objeto ProfessorRequestDTO com dados inválidos
        ProfessorRequestDTO requestDTO = new ProfessorRequestDTO();
        requestDTO.setNome(null);
        requestDTO.setEmail("johndoe@example.com");

        // Configuração do comportamento do mock do repositório para lançar uma exceção ao salvar o professor
        when(professorRepository.save(Mockito.any(Professor.class))).thenThrow(new RuntimeException("Erro ao salvar professor"));

        // Execução do método cadastrarProfessor e verificação se uma exceção é lançada
        assertThrows(RuntimeException.class, () -> teacherService.createTeacher(requestDTO));
    }

    @Test
    void testFindTeacher(){
        Long id = 1L;
        Professor professor = new Professor();
        professor.setId(id);

        when(professorRepository.findById(id)).thenReturn(java.util.Optional.of(professor));
        teacherService.findById(id);
        verify(professorRepository, times(1)).findById(id);
    }

    @Test
    void testFindTeacher_ShouldReturnEntityNotFound(){
        //Given
        Long id = 1L;
        String expectedMessage = "Professor não encontrado com id " + id +" !";
        //When
        when(professorRepository.findById(id)).thenThrow(new EntityNotFoundException(expectedMessage));
        EntityNotFoundException actual = assertThrows(EntityNotFoundException.class, () -> teacherService.findTeacherById(id));
        //Then
        assertEquals(expectedMessage, actual.getMessage());
    }

    @Test
    void testDeleteProfessor(){
        //Given
        Long id = 1L;
        Professor professor = new Professor();
        professor.setId(id);
        //When & Then
        when(professorRepository.findById(id)).thenReturn(java.util.Optional.of(professor));
        teacherService.deleteTeacher(id);
        verify(professorRepository, times(1)).delete(professor);
    }
}
