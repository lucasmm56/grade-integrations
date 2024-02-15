package br.edu.imepac.professores.controller;


import br.edu.imepac.professores.dto.request.DisponibilidadeRequestDTO;
import br.edu.imepac.professores.dto.response.DisponibilidadeCreateResponseDTO;
import br.edu.imepac.professores.models.services.DisponibilidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disponibilidades")
public class DisponibilidadeController {

    @Autowired
    private DisponibilidadeService disponibilidadeService;


    @GetMapping
    public ResponseEntity<List<DisponibilidadeCreateResponseDTO>> listDisponibilidades(){
        List<DisponibilidadeCreateResponseDTO> disponibilidadesResponse = disponibilidadeService.listDisponibilidades();
        return ResponseEntity.ok(disponibilidadesResponse);
    }


    @PostMapping
    public ResponseEntity<DisponibilidadeCreateResponseDTO> saveDisponibilidade(@RequestBody DisponibilidadeRequestDTO disponibilidadeRequestDTO){
        DisponibilidadeCreateResponseDTO savedDisponibilidade  = disponibilidadeService.cadastrarDisponibilidade(disponibilidadeRequestDTO);
        return ResponseEntity.ok(savedDisponibilidade);
    }
}
