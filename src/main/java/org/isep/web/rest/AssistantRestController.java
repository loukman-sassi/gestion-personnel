package org.isep.web.rest;

import java.util.List;

import org.isep.dao.AssistantRepository;
import org.isep.entities.Assistant;
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
@RequestMapping("/api/assistants")
@CrossOrigin(origins = "*")
public class AssistantRestController {
	
	@Autowired private AssistantRepository assistantDao;

    @GetMapping("/index")
    public List<Assistant> getAll() {
        return assistantDao.findAll();
    }
    
    @GetMapping("/show/{id}")
    public Assistant show(@PathVariable Long id) {
    	return assistantDao.findById(id).orElse(null);
    }

    @PostMapping("/create")
    public Assistant create(@RequestBody Assistant assistant) {
        return assistantDao.save(assistant);
    }
    
    @PutMapping("/update/{id}")
    public Assistant update(@PathVariable Long id, @RequestBody Assistant assistant) {
        return assistantDao.findById(id).map(assistantExistant -> {
        	assistantExistant.setNom(assistant.getNom());
        	assistantExistant.setPrenom(assistant.getPrenom());
        	assistantExistant.setDiplome(assistant.getDiplome());
        	assistantExistant.setDateNaissance(assistant.getDateNaissance());
        	assistantExistant.setResponsable(assistant.getResponsable());
        	return assistantDao.save(assistantExistant);
        }).orElseGet(() -> {
        	return assistantDao.save(assistant);
        	});
    }
    
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        assistantDao.deleteById(id);
    }
}
