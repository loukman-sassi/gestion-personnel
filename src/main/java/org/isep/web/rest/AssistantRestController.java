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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assistants")
@CrossOrigin(origins = "*")
public class AssistantRestController {
	@Autowired
    private AssistantRepository assistantDao;

    @GetMapping
    public List<Assistant> getAll() {
        return assistantDao.findAll();
    }

    @PostMapping
    public Assistant create(@RequestBody Assistant assistant) {
        return assistantDao.save(assistant);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        assistantDao.deleteById(id);
    }
}
