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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfesseurController {

    @Autowired
    private ProfesseurRepository profDao;

    @Autowired
    private ModuleRepository moduleDao;

    // 1. Recherche et Affichage de la liste
    @RequestMapping(value = "/professeurs/search")
    public String search(Model model, 
                         @RequestParam(name = "motCle", defaultValue = "") String mc) {
        List<Professeur> profs = profDao.findByNom("%" + mc + "%");
        model.addAttribute("profs", profs);
        model.addAttribute("motC", mc);
        return "addProfesseur"; // Correspond à addProfesseur.jsp
    }

    // 2. Ajout d'un professeur ET association des modules (Exercice 4)
    @RequestMapping(value = "/professeurs/add")
    public String add(Model model,
            @RequestParam(name = "nom", defaultValue = "") String nom,
            @RequestParam(name = "prenom", defaultValue = "") String prenom,
            @RequestParam(name = "diplome", defaultValue = "") String diplome,
            @RequestParam(name = "date", defaultValue = "") String date, // Reçu en String
            @RequestParam(name = "idsModules", required = false) List<Long> idsModules) { // Liste des modules cochés

        // Si le nom n'est pas vide, on tente de sauvegarder (Logique POST)
        if (!nom.equals("")) {
            // A. Créer le professeur
            Professeur p = new Professeur();
            p.setNom(nom);
            p.setPrenom(prenom);
            p.setDiplome(diplome);
            if(!date.isEmpty()) p.setDateNaissance(LocalDate.parse(date)); // Conversion String -> LocalDate
            
            // Sauvegarde du prof pour avoir un ID généré
            Professeur savedProf = profDao.save(p);

            // B. Associer les modules sélectionnés (Exercice 4)
            if (idsModules != null) {
                for (Long idMod : idsModules) {
                    Optional<Module> mOpt = moduleDao.findById(idMod);
                    if (mOpt.isPresent()) {
                        Module m = mOpt.get();
                        m.setResponsable(savedProf); // On associe le module au nouveau prof
                        moduleDao.save(m); // On met à jour le module en base
                    }
                }
            }
            
            // Redirection vers la liste pour voir le résultat
            return "redirect:/professeurs/search";
        }

        // Sinon, on affiche le formulaire vide (Logique GET)
        // IMPORTANT : On doit envoyer la liste des modules existants pour pouvoir les cocher !
        List<Module> modulesDispos = moduleDao.findAll();
        model.addAttribute("modules", modulesDispos);
        
        return "addProfesseur"; // Correspond à addProfesseur.jsp
    }

    // 3. Suppression
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
}