package org.isep.web.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import org.isep.entities.Professeur;
import java.util.List;

@WebService(name = "ProfesseurService", targetNamespace = "http://soap.web.isep.org/")
public interface ProfesseurServiceWs {

    @WebMethod
    List<Professeur> getTousLesProfesseurs();

    @WebMethod
    Professeur getProfesseurById(@WebParam(name = "id") Long id);

    @WebMethod
    Professeur creerProfesseur(
            @WebParam(name = "nom") String nom,
            @WebParam(name = "prenom") String prenom,
            @WebParam(name = "diplome") String diplome
    );

    @WebMethod
    boolean supprimerProfesseur(@WebParam(name = "id") Long id);
}