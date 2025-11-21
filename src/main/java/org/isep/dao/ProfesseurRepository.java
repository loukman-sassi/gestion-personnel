package org.isep.dao;

import java.util.List;

import org.isep.entities.Professeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfesseurRepository extends JpaRepository<Professeur, Long>{
	@Query("select p from Professeur p where p.nom like :x")
    List<Professeur> findByNom(@Param("x") String mc);

}
