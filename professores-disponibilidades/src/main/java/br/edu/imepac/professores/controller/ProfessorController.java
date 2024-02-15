package br.edu.imepac.professores.controller;

import br.edu.imepac.professores.dto.request.ProfessorRequestDTO;
import br.edu.imepac.professores.dto.response.ProfessorResponseDTO;
import br.edu.imepac.professores.models.services.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @PostMapping
    public ResponseEntity<ProfessorResponseDTO> saveTeacher(@Valid @RequestBody ProfessorRequestDTO professorRequestDTO){
        ProfessorResponseDTO professorResponseDTO = professorService.cadastrarProfessor(professorRequestDTO);
        return new ResponseEntity<>(professorResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProfessorResponseDTO>> listProfessores(){
        List<ProfessorResponseDTO> professores = professorService.listarProfessores();
        return ResponseEntity.ok(professores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> findTeacher(@PathVariable Long id){
        ProfessorResponseDTO professorResponseDTO = professorService.findTeacherById(id);
        return ResponseEntity.ok(professorResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTeacher(@PathVariable Long id){
        professorService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }
}
