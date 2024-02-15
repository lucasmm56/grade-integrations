package br.edu.imepac.professores.models.services;

import br.edu.imepac.professores.dto.request.DisponibilidadeRequestDTO;
import br.edu.imepac.professores.dto.response.DisponibilidadeCreateResponseDTO;
import br.edu.imepac.professores.dto.response.ProfessorCreateDisponibilidadeResponseDTO;
import br.edu.imepac.professores.models.entities.Disponibilidade;
import br.edu.imepac.professores.models.entities.Professor;
import br.edu.imepac.professores.models.repository.DisponibilidadeRepository;
import br.edu.imepac.professores.models.repository.ProfessorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class DisponibilidadeService {

    @Autowired
    private DisponibilidadeRepository disponibilidadeRepository;

    @Autowired
    private ProfessorRepository professorRepository;



    public DisponibilidadeCreateResponseDTO cadastrarDisponibilidade(DisponibilidadeRequestDTO requestDTO) {
        Professor professor = this.findById(requestDTO.getProfessorId());

        Disponibilidade disponibilidade = new Disponibilidade();
        disponibilidade.setDiaSemana(requestDTO.getDiaSemana());
        disponibilidade.setHorario(requestDTO.getHorario());
        disponibilidade.setProfessor(professor);

        Disponibilidade savedDisponibilidade = disponibilidadeRepository.save(disponibilidade);

        DisponibilidadeCreateResponseDTO responseDTO = new DisponibilidadeCreateResponseDTO();
        responseDTO.setId(savedDisponibilidade.getId());
        responseDTO.setHorario(savedDisponibilidade.getHorario());
        responseDTO.setDiaSemana(savedDisponibilidade.getDiaSemana());

        ProfessorCreateDisponibilidadeResponseDTO professorDto = new ProfessorCreateDisponibilidadeResponseDTO();
        professorDto.setId(professor.getId());
        professorDto.setName(professor.getNome());

        responseDTO.setProfessor(professorDto);

        return responseDTO;
    }

    public List<DisponibilidadeCreateResponseDTO> listDisponibilidades(){
        List<Disponibilidade> disponibilidades = disponibilidadeRepository.findAll();

        return disponibilidades.stream()
                .map(this::converterDisponibilidadeParaDTO).toList();
    }

    private DisponibilidadeCreateResponseDTO converterDisponibilidadeParaDTO(Disponibilidade disponibilidade){
        DisponibilidadeCreateResponseDTO responseDTO = new DisponibilidadeCreateResponseDTO();
        responseDTO.setId(disponibilidade.getId());
        responseDTO.setHorario(disponibilidade.getHorario());
        responseDTO.setDiaSemana(disponibilidade.getDiaSemana());

        ProfessorCreateDisponibilidadeResponseDTO professorResponse = new ProfessorCreateDisponibilidadeResponseDTO();
        professorResponse.setId(disponibilidade.getProfessor().getId());
        professorResponse.setName(disponibilidade.getProfessor().getNome());
        responseDTO.setProfessor(professorResponse);

        return responseDTO;
    }

    private Professor findById(Long id){
        return professorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Professor não encontrado com id " + id +" !"));
    }

}
