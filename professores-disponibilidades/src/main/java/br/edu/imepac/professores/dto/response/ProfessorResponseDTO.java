package br.edu.imepac.professores.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private List<DisponibilidadeResponseDTO> disponibilidades;
}
