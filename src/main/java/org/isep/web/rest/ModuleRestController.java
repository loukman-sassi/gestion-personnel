package org.isep.web.rest;

import java.util.List;
import org.isep.dao.ModuleRepository;
import org.isep.entities.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/modules")
@CrossOrigin(origins = "*")
public class ModuleRestController {

    @Autowired
    private ModuleRepository moduleDao;

    @GetMapping("/index")
    public List<Module> getAll() {
        return moduleDao.findAll();
    }

    @PostMapping("/create")
    public Module create(@RequestBody Module module) {
        return moduleDao.save(module);
    }
    
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        moduleDao.deleteById(id);
    }
    
    @GetMapping("/show/{id}")
    public Module getOne(@PathVariable Long id) {
        return moduleDao.findById(id).orElse(null);
    }

    @PutMapping("/update/{id}")
    public Module update(@PathVariable Long id, @RequestBody Module module) {
        return moduleDao.findById(id).map(moduleExistant -> {
            moduleExistant.setTitre(module.getTitre());
            moduleExistant.setNombreHeuresCours(module.getNombreHeuresCours());
            moduleExistant.setNombresHeuresTd(module.getNombreHeuresTd()); 
            moduleExistant.setNombreHeuresTp(module.getNombreHeuresTp());
            return moduleDao.save(moduleExistant);
        }).orElseGet(() -> {
            return moduleDao.save(module);
        });
    }
}