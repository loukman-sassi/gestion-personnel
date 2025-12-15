package org.isep.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.xml.bind.annotation.XmlTransient;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Professeur extends Personnel {

    @Column(length = 100)
    private String diplome;

    @OneToMany(mappedBy = "responsable") 
    private List<Module> modules = new ArrayList<>();

    // Default Spring boot constructor
    public Professeur() {}

    public Professeur(String nom, String prenom, LocalDate dateNaissance, String diplome) {
        super(nom, prenom, dateNaissance);
        this.diplome = diplome;
    }

    public void ajouter_module(Module m) {
        if(m != null) {
            this.modules.add(m);
            m.setResponsable(this);
        }
    }

    public String getDiplome() {
        return diplome;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    @XmlTransient
    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
}
