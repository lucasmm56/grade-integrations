package br.edu.imepac.professores.models.services;

import br.edu.imepac.professores.dto.request.ProfessorRequestDTO;
import br.edu.imepac.professores.dto.response.DisponibilidadeResponseDTO;
import br.edu.imepac.professores.dto.response.ProfessorResponseDTO;
import br.edu.imepac.professores.models.entities.Disponibilidade;
import br.edu.imepac.professores.models.entities.Professor;
import br.edu.imepac.professores.models.repository.ProfessorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {
    private ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository){
        this.professorRepository = professorRepository;
    }

    public ProfessorResponseDTO cadastrarProfessor(ProfessorRequestDTO requestDTO) {
        Professor professor = new Professor();
        professor.setNome(requestDTO.getNome());
        professor.setEmail(requestDTO.getEmail());

        Professor savedProfessor = professorRepository.save(professor);

        ProfessorResponseDTO responseDTO = new ProfessorResponseDTO();
        responseDTO.setId(savedProfessor.getId());
        responseDTO.setNome(savedProfessor.getNome());
        responseDTO.setEmail(savedProfessor.getEmail());

        return responseDTO;
    }

    public ProfessorResponseDTO editarProfessor(Long id, ProfessorRequestDTO professorRequestDTO){
        Optional<Professor> optionalProfessor = Optional.ofNullable(this.findById(id));
        if(optionalProfessor.isPresent()){
            Professor professor = optionalProfessor.get();
            professor.setNome(professorRequestDTO.getNome());
            professor.setEmail(professorRequestDTO.getEmail());

            Professor savedProfessor = professorRepository.save(professor);
            ProfessorResponseDTO responseDTO = new ProfessorResponseDTO();
            responseDTO.setId(savedProfessor.getId());
            responseDTO.setNome(savedProfessor.getNome());
            responseDTO.setEmail(savedProfessor.getEmail());
            return responseDTO;
        }else{
            throw new EntityNotFoundException("Professor não encontrado com id" + id +" !");
        }
    }

    public List<ProfessorResponseDTO> listarProfessores() {
        List<Professor> professores = professorRepository.findAll();
        return professores.stream()
                .map(this::converterProfessorParaDTO)
                .toList();
    }

    private ProfessorResponseDTO converterProfessorParaDTO(Professor professor) {
        ProfessorResponseDTO responseDTO = new ProfessorResponseDTO();
        responseDTO.setId(professor.getId());
        responseDTO.setNome(professor.getNome());
        responseDTO.setEmail(professor.getEmail());
        responseDTO.setDisponibilidades(this.getDisponibilidadesDTO(professor));

        return responseDTO;
    }

    private DisponibilidadeResponseDTO converterDisponibilidadeParaDTO(Disponibilidade disponibilidade) {
        DisponibilidadeResponseDTO disponibilidadeDTO = new DisponibilidadeResponseDTO();
        disponibilidadeDTO.setId(disponibilidade.getId());
        disponibilidadeDTO.setDiaSemana(disponibilidade.getDiaSemana());
        disponibilidadeDTO.setHorario(disponibilidade.getHorario());
        return disponibilidadeDTO;
    }

    public Professor findById(Long id){
        return professorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Professor não encontrado com id" + id +" !"));
    }

    private List<DisponibilidadeResponseDTO> getDisponibilidadesDTO(Professor professor){
        return professor.getDisponibilidades()
                .stream()
                .map(this::converterDisponibilidadeParaDTO)
                .toList();
    }

    public ProfessorResponseDTO findTeacherById(Long id){
        Professor professor = this.findById(id);
        ProfessorResponseDTO professorResponse = new ProfessorResponseDTO();
        professorResponse.setId(professor.getId());
        professorResponse.setNome(professor.getNome());
        professorResponse.setEmail(professor.getEmail());
        professorResponse.setDisponibilidades(this.getDisponibilidadesDTO(professor));
        return professorResponse;
    }

    public void deleteTeacher(Long id){
        Professor professor = this.findById(id);
        professorRepository.delete(professor);
    }

}
