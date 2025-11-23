package org.isep.web.rest;

import java.time.LocalDate;
import java.util.List;

import org.isep.dao.ModuleRepository;
import org.isep.dao.ProfesseurRepository;
import org.isep.entities.Professeur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/professeurs")
@CrossOrigin(origins = "*") 
public class ProfesseurRestController {
	
	@Autowired private ProfesseurRepository profDao;
	@Autowired private ModuleRepository moduleDao;
	
	static class ProfesseurDTO {
        public Long id;
        public String nom;
        public String prenom;
        public String diplome;
        public LocalDate dateNaissance;
        public List<Long> idsModules; 
    }

	@GetMapping("/index")
	public List<Professeur> getAll() {
		return profDao.findAll();
	}

	@PostMapping("/create")
	public Professeur create(@RequestBody ProfesseurDTO dto) {
		Professeur p = new Professeur();
        p.setNom(dto.nom);
        p.setPrenom(dto.prenom);
        p.setDiplome(dto.diplome);
        p.setDateNaissance(dto.dateNaissance);
        
        Professeur savedProf = profDao.save(p);

        associerModules(savedProf, dto.idsModules);
        
        return savedProf;
	}

	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable Long id) {
		Professeur p = profDao.findById(id).orElse(null);
        if(p != null) {
            for(org.isep.entities.Module m : p.getModules()) {
                m.setResponsable(null);
                moduleDao.save(m);
            }
            profDao.deleteById(id);
        }
	}

	@GetMapping("/show/{id}")
	public Professeur getProfesseur(@PathVariable Long id) {
		return profDao.findById(id).orElse(null);
	}

	@PutMapping("/update/{id}")
	public Professeur update(@PathVariable Long id, @RequestBody ProfesseurDTO dto) {
		return profDao.findById(id).map(p -> {
            p.setNom(dto.nom);
            p.setPrenom(dto.prenom);
            p.setDiplome(dto.diplome);
            p.setDateNaissance(dto.dateNaissance);
            
            Professeur updatedProf = profDao.save(p);
            
            associerModules(updatedProf, dto.idsModules);
            
            return updatedProf;
        }).orElse(null);
	}
	
	private void associerModules(Professeur prof, List<Long> idsModules) {
        if (idsModules != null && !idsModules.isEmpty()) {
            for (Long idMod : idsModules) {
                moduleDao.findById(idMod).ifPresent(module -> {
                    module.setResponsable(prof);
                    moduleDao.save(module);
                });
            }
        }
    }
}
