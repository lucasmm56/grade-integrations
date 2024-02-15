package br.edu.imepac.professores.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorRequestDTO {
    @NotBlank(message = "Campo nome não pode ser em branco")
    private String nome;
    @NotBlank(message = "Campo email não pode ser em branco")
    private String email;
}
