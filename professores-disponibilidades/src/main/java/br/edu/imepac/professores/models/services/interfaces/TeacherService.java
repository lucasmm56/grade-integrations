package br.edu.imepac.professores.models.services.interfaces;

import br.edu.imepac.professores.dto.request.ProfessorRequestDTO;
import br.edu.imepac.professores.dto.response.ProfessorResponseDTO;

import java.util.List;

public interface TeacherService {

    ProfessorResponseDTO createTeacher(ProfessorRequestDTO professorRequest);
    ProfessorResponseDTO updateTeacher(Long id, ProfessorRequestDTO professorRequest);
    List<ProfessorResponseDTO> listTeachers();
    ProfessorResponseDTO findTeacherById(Long id);
    void deleteTeacher(Long id);
}
