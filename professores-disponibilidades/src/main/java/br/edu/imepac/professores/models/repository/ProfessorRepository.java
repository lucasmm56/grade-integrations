package br.edu.imepac.professores.models.repository;

import br.edu.imepac.professores.models.entities.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository  extends JpaRepository<Professor, Long > {
}
