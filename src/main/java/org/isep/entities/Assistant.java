package org.isep.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Assistant extends Personnel {

    @Column(length = 100)
    private String diplome;

    @ManyToOne
    @JoinColumn(name = "responsable_id")
    @JsonIgnoreProperties({"modules", "assistants"})
    private Personnel responsable;

    // Default Spring boot constructor
    public Assistant() {}

    public Assistant(String nom, String prenom, LocalDate dateNaissance, String diplome) {
        super(nom, prenom, dateNaissance);
        this.diplome = diplome;
    }

    public String getDiplome() {
        return diplome;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    public Personnel getResponsable() {
        return responsable;
    }
    
    public void setResponsable(Personnel personnel) {
        this.responsable = personnel;
    }

    public void modifierResponsable(Personnel responsable) {
        this.responsable = responsable;
    }
}