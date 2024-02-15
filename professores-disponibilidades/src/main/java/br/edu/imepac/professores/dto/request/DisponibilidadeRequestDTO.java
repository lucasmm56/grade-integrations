package br.edu.imepac.professores.dto.request;

import lombok.Data;

@Data
public class DisponibilidadeRequestDTO {

    private Long professorId;
    private String diaSemana;
    private String horario;
}