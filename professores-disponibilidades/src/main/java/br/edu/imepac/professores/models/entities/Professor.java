package br.edu.imepac.professores.models.entities;

import br.edu.imepac.professores.dto.response.DisponibilidadeResponseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "professores")
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String nome;
    private String email;
    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private List<Disponibilidade> disponibilidades;
}
