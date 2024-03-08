package br.edu.imepac.professores.controller;

import br.edu.imepac.professores.dto.request.ProfessorRequestDTO;
import br.edu.imepac.professores.dto.response.ProfessorResponseDTO;
import br.edu.imepac.professores.models.services.interfaces.TeacherService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professores")
public class ProfessorController {
    private final TeacherService teacherService;

    public ProfessorController(TeacherService teacherService){
        this.teacherService = teacherService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProfessorResponseDTO> saveTeacher(@Valid @RequestBody ProfessorRequestDTO professorRequestDTO){
        ProfessorResponseDTO professorResponseDTO = teacherService.createTeacher(professorRequestDTO);
        return new ResponseEntity<>(professorResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProfessorResponseDTO>> listProfessores(){
        List<ProfessorResponseDTO> professores = teacherService.listTeachers();
        return ResponseEntity.ok(professores);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProfessorResponseDTO> updateTeacher(@PathVariable Long id, @Valid @RequestBody ProfessorRequestDTO professorRequestDTO) {
            ProfessorResponseDTO professorResponseDTO = teacherService.updateTeacher(id, professorRequestDTO);
            return ResponseEntity.ok(professorResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> findTeacher(@PathVariable Long id){
        ProfessorResponseDTO professorResponseDTO = teacherService.findTeacherById(id);
        return ResponseEntity.ok(professorResponseDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id){
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }
}
