package br.edu.imepac.professores.controller;

import br.edu.imepac.professores.dto.request.ProfessorRequestDTO;
import br.edu.imepac.professores.dto.response.ProfessorResponseDTO;
import br.edu.imepac.professores.models.services.ProfessorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professores")
public class ProfessorController {
    private ProfessorService professorService;

    public ProfessorController(ProfessorService professorService){
        this.professorService = professorService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProfessorResponseDTO> saveTeacher(@Valid @RequestBody ProfessorRequestDTO professorRequestDTO){
        ProfessorResponseDTO professorResponseDTO = professorService.cadastrarProfessor(professorRequestDTO);
        return new ResponseEntity<>(professorResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ProfessorResponseDTO>> listProfessores(){
        List<ProfessorResponseDTO> professores = professorService.listarProfessores();
        return ResponseEntity.ok(professores);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> updateTeacher(@PathVariable Long id, @Valid @RequestBody ProfessorRequestDTO professorRequestDTO) {
            ProfessorResponseDTO professorResponseDTO = professorService.editarProfessor(id, professorRequestDTO);
            return ResponseEntity.ok(professorResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> findTeacher(@PathVariable Long id){
        ProfessorResponseDTO professorResponseDTO = professorService.findTeacherById(id);
        return ResponseEntity.ok(professorResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id){
        professorService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }
}
