package org.isep.web.soap;

import jakarta.jws.WebService;
import org.isep.dao.ProfesseurRepository;
import org.isep.entities.Professeur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@WebService(endpointInterface = "org.isep.web.soap.ProfesseurServiceWs")
public class ProfesseurServiceWsImpl implements ProfesseurServiceWs {

    @Autowired
    private ProfesseurRepository profDao;

    @Override
    public List<Professeur> getTousLesProfesseurs() {
        return profDao.findAllWithModules();
    }

    @Override
    public Professeur getProfesseurById(Long id) {
        return profDao.findById(id).orElse(null);
    }

    @Override
    public Professeur creerProfesseur(String nom, String prenom, String diplome) {
        Professeur p = new Professeur();
        p.setNom(nom);
        p.setPrenom(prenom);
        p.setDiplome(diplome);
        // On pourrait ajouter la date ici si nécessaire
        return profDao.save(p);
    }

    @Override
    public boolean supprimerProfesseur(Long id) {
        if (profDao.existsById(id)) {
            profDao.deleteById(id);
            return true;
        }
        return false;
    }
}