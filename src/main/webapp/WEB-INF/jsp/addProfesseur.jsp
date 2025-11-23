<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ajouter un Professeur</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <div class="card">
        <div class="card-header">Ajouter un nouveau Professeur</div>
        <div class="card-body">
            <form action="add" method="post">
            
                <div class="form-group">
                    <label>Nom :</label>
                    <input type="text" name="nom" class="form-control" required/>
                </div>
                
                <div class="form-group">
                    <label>Prénom :</label>
                    <input type="text" name="prenom" class="form-control" required/>
                </div>
                
                <div class="form-group">
                    <label>Diplôme :</label>
                    <input type="text" name="diplome" class="form-control" required/>
                </div>
                
                <div class="form-group">
                    <label>Date de Naissance :</label>
                    <input type="date" name="date" class="form-control" required/>
                </div>
                
                <div class="form-group">
                    <label>Associer des Modules existants :</label>
                    <select name="idsModules" multiple class="form-control" style="height: 150px; required">
                        <c:forEach items="${modules}" var="m">
                            <option value="${m.idModule}">
                                ${m.titre} (Resp: ${m.responsable != null ? m.responsable.nom : 'Aucun responsable pour ce module'})
                            </option>
                        </c:forEach>
                    </select>
                    <small class="form-text text-muted">Maintenez Ctrl pour sélectionner plusieurs modules.</small>
                </div>

                <button type="submit" class="btn btn-primary">Enregistrer</button>
                <a href="search" class="btn btn-secondary">Retour</a>
            </form>
            
        </div>
    </div>
</div>
</body>
</html>