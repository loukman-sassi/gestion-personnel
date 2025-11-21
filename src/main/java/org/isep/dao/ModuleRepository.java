package org.isep.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.isep.entities.Module;

public interface ModuleRepository extends JpaRepository<Module, Long>{

}
