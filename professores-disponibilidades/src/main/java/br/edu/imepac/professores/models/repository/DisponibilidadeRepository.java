package br.edu.imepac.professores.models.repository;

import br.edu.imepac.professores.models.entities.Disponibilidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisponibilidadeRepository extends JpaRepository<Disponibilidade, Long> {
}
