package org.isep.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Module implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
    
    @Column(length = 100)
    private String titre;

    private int nombreHeuresCours;
    private int nombreHeuresTd;
    private int nombreHeuresTp;

    @ManyToOne
    @JoinColumn(name = "responsable_id")
    @JsonIgnoreProperties("modules")
    private Professeur responsable;

    // Default Spring boot constructor
    public Module() {}

    public Module(String titre, int nombreHeuresCours, int nombreHeuresTd, int nombreHeuresTp) {
        this.titre = titre;
        this.nombreHeuresCours = nombreHeuresCours;
        this.nombreHeuresTd = nombreHeuresTd;
        this.nombreHeuresTp = nombreHeuresTp;
    }

    // Getters and Setters
    public Long getIdModule() {
        return id;
    }

    public void setIdModule(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Professeur getResponsable() {
        return responsable;
    }

    public void setResponsable(Professeur responsable) {
        this.responsable = responsable;
    }
    
    public int getNombreHeuresCours() {
		return nombreHeuresCours;
	}
	
	public int getNombreHeuresTd() {
		return nombreHeuresTd;
	}
	
	public int getNombreHeuresTp() {
		return nombreHeuresTp;
	}
	
	public void setNombreHeuresCours(int nombreHeuresCours) {
		this.nombreHeuresCours = nombreHeuresCours;
	}
	
	public void setNombresHeuresTd(int nombreHeuresTd) {
		this.nombreHeuresTd = nombreHeuresTd;
	}
	
	public void setNombreHeuresTp(int nombreHeuresTp) {
		this.nombreHeuresTp = nombreHeuresTp;
	}
	
	public void ajouter_h_cc(int nb_h) {
		nombreHeuresCours = nombreHeuresCours + nb_h;
	}
	
	public void ajouter_h_td(int nb_h) {
		nombreHeuresTd = nombreHeuresTd + nb_h;
	}
	
	public void ajouter_h_tp(int nb_h) {
		nombreHeuresTp = nombreHeuresTp + nb_h;
	}
}
