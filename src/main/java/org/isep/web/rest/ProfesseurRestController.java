package org.isep.web.rest;

import java.util.List;
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

	@GetMapping("/index")
	public List<Professeur> getAll() {
		return profDao.findAll();
	}

	@PostMapping("/create")
	public Professeur create(@RequestBody Professeur p) {
		return profDao.save(p);
	}

	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable Long id) {
		profDao.deleteById(id);
	}

	@GetMapping("/show/{id}")
	public Professeur getProfesseur(@PathVariable Long id) {
		return profDao.findById(id).orElse(null);
	}

	@PutMapping("/update/{id}")
	public Professeur update(@PathVariable Long id, @RequestBody Professeur professeur) {
		return profDao.findById(id).map( professeurExistant -> {
			professeurExistant.setNom(professeur.getNom());
			professeurExistant.setPrenom(professeur.getPrenom());
			professeurExistant.setDateNaissance(professeur.getDateNaissance());
			professeurExistant.setDiplome(professeur.getDiplome());
			professeurExistant.setModules(professeur.getModules());
			return profDao.save(professeurExistant);
		}).orElseGet( () -> {
			return profDao.save(professeur);
		});
	}
}
