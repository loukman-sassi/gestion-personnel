package org.isep;

import java.time.LocalDate;

import org.isep.dao.AssistantRepository;
import org.isep.dao.ModuleRepository;
import org.isep.dao.ProfesseurRepository;
import org.isep.entities.Professeur;
import org.isep.entities.Assistant;
import org.isep.entities.Module;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GestionPersonnelApplication {

	public static void main(String[] args) {
		// 1. Récupération du contexte de l'application
				ApplicationContext ctx = SpringApplication.run(GestionPersonnelApplication.class, args);
				
				// 2. Récupération des DAOs (Beans) via le contexte
				ProfesseurRepository profDao = ctx.getBean(ProfesseurRepository.class);
				ModuleRepository moduleDao = ctx.getBean(ModuleRepository.class);
				AssistantRepository assistantDao = ctx.getBean(AssistantRepository.class);
				
				// 3. Création et sauvegarde des Professeurs
				Professeur p1 = new Professeur("Gharzouli", "Mohamed", LocalDate.of(1989, 3, 14), "Systemes Autonomes et Informatique");
				Professeur p2 = new Professeur("Mennour", "Rostom", LocalDate.of(1970, 6, 23), "Reseaux et Systemes Distribues");
				
				profDao.save(p1);
				profDao.save(p2);
				
				// 4. Création et sauvegarde des Modules (avec association)
				Module m1 = new Module("Calculs Orientes Services", 64, 32, 32);
				m1.setResponsable(p1); // Gharzouli est responsable
				moduleDao.save(m1);
				
				Module m2 = new Module("Cloud Computing", 64, 32, 32);
				m2.setResponsable(p2); // Mennour est responsable
				moduleDao.save(m2);
				
				// 5. Création et sauvegarde d'un Assistant
				Assistant a1 = new Assistant("Halladj", "Hamza", LocalDate.of(2000, 11, 7), "Reseaux et Systemes Distribues");
				a1.modifierResponsable(p1); // L'assistant est sous la responsabilité de Gharzouli
				assistantDao.save(a1);
				
				Assistant a2 = new Assistant("Yaou", "Mey", LocalDate.of(2003, 07, 7), "Systemes Intelligents Distribues");
				a2.modifierResponsable(p2); // L'assistant est sous la responsabilité de Mennour
				assistantDao.save(a2);
				
				// 6. Affichage pour vérifier (style fonctionnel comme demandé)
				System.out.println("---------- LISTE DES PROFESSEURS ----------");
				profDao.findAll().forEach(p -> System.out.println(p.getNom() + " " + p.getPrenom()));
				
				System.out.println("---------- LISTE DES MODULES ----------");
				moduleDao.findAll().forEach(m -> System.out.println(m.getTitre() + " (Resp: " + m.getResponsable().getNom() + ")"));
	}

}
