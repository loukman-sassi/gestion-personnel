package org.isep.web;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.isep.dao.ModuleRepository;
import org.isep.dao.ProfesseurRepository;
import org.isep.entities.Module;
import org.isep.entities.Professeur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/* Toutes les methodes d'une classe qui a une annotation @Controlleur ont comme parametre l'interface Model
 * qui va jouer le role d'une camion de livraison qui transporte les donner du controlleur vers la vue
 */


@Controller // Annotation pour un controlleur qui va communiquer avec les fichiers JSP
@RequestMapping("/professeurs") 
public class ProfesseurController {

	// @Autowired permet de charger l'instance de la repository cree par Spring Boot (le BEAN) lors du demarrage de l'application 
	@Autowired private ProfesseurRepository profDao;
	@Autowired private ModuleRepository moduleDao;

	@GetMapping()
	public String displayForm(Model model) {
		List <Module> modules = moduleDao.findAll();
		model.addAttribute("modules", modules);
		return "addProfesseur";

	}

	@PostMapping("/add")
	public String add(Model model,
			@RequestParam(name = "nom") String nom,
			@RequestParam(name = "prenom") String prenom,
			@RequestParam(name = "diplome") String diplome,
			@RequestParam(name = "date") String date,
			@RequestParam(name = "idsModules") List<Long> idsModules) {

		Professeur p = new Professeur();
		p.setNom(nom);
		p.setPrenom(prenom);
		p.setDiplome(diplome);
		p.setDateNaissance(LocalDate.parse(date));

		profDao.save(p);

		for (Long idModule : idsModules) {
			Optional<Module> module = moduleDao.findById(idModule);
			if (module.isPresent()) {
				Module m = module.get();
				m.setResponsable(p);
				moduleDao.save(m);
			}
		}
		return "redirect:/professeurs";
	}
	

	/* 3. Suppression
	@RequestMapping(value = "/professeurs/delete")
	public String delete(Model model, 
			@RequestParam(name = "ref", defaultValue = "") Long idProf,
			@RequestParam(name = "mc", defaultValue = "") String mc) {

		// Attention: Avant de supprimer un prof, il faut "libérer" ses modules 
		// sinon erreur de clé étrangère ou suppression en cascade selon config
		Optional<Professeur> p = profDao.findById(idProf);
		if(p.isPresent()) {
			for(Module m : p.get().getModules()) {
				m.setResponsable(null); // On détache le module
				moduleDao.save(m);
			}
			profDao.deleteById(idProf);
		}

		return "redirect:/professeurs/search?motCle=" + mc;
	}

	// 1. Recherche et Affichage de la liste
		@RequestMapping(value = "/professeurs/search")
		public String search(Model model, 
				@RequestParam(name = "motCle", defaultValue = "") String mc) {
			List<Professeur> profs = profDao.findByNom("%" + mc + "%");
			model.addAttribute("profs", profs);
			model.addAttribute("motC", mc);
			return "addProfesseur"; // Correspond à addProfesseur.jsp
		} */
}