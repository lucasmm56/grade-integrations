package br.edu.imepac.professores.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisponibilidadeCreateResponseDTO {
        private Long id;
        private String diaSemana;
        private String horario;
        private ProfessorCreateDisponibilidadeResponseDTO professor;
}

