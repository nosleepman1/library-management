package gestion;

import exceptions.LecteurInexistantException;
import exceptions.LimiteEmpruntException;
import exceptions.RessourceDejaEmprunteeException;
import model.Lecteur;
import model.Ressource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GestionEmprunts {

    private final GestionLecteurs gestionLecteurs;

    private final Map<String, Set<Ressource>> empruntsParLecteur = new HashMap<>();

    public GestionEmprunts(GestionLecteurs gestionLecteurs) {
        this.gestionLecteurs = gestionLecteurs;
    }

    public void emprunter(String numeroCarte, Ressource ressource)
            throws LecteurInexistantException, 
                    RessourceDejaEmprunteeException, 
                    LimiteEmpruntException
        {

        Lecteur lecteur = gestionLecteurs.rechercher(numeroCarte);

        if (lecteur == null) {
            throw new LecteurInexistantException("Le lecteur n'existe pas pour le numéro de carte : " + numeroCarte);
        }

        Set<Ressource> ressources = empruntsParLecteur.computeIfAbsent(numeroCarte, k -> new HashSet<>());

        if (ressources.contains(ressource)) {
            throw new RessourceDejaEmprunteeException("Le lecteur a déjà emprunté cette ressource.");
        }

        if (ressources.size() >= 4) {
            throw new LimiteEmpruntException("Le lecteur ne peut pas emprunter plus de 4 ressources.");
        }
        ressources.add(ressource);
    }



    public List<Ressource> ressourcesLecteur(String numeroCarte) throws LecteurInexistantException {
        Lecteur lecteur = gestionLecteurs.rechercher(numeroCarte);
        
        if (lecteur == null) {
            throw new LecteurInexistantException("Le lecteur n'existe pas pour le numéro de carte : " + numeroCarte);
        }
        
        Set<Ressource> ressources = empruntsParLecteur.getOrDefault(numeroCarte, new HashSet<>());
        return new ArrayList<>(ressources);
    }

    public int nombreTotalEmprunts() {
        return empruntsParLecteur.values().stream()
                .mapToInt(Set::size)
                .sum();
    }

    public double valeurTotaleRessourcesEmpruntees() {
        return empruntsParLecteur.values().stream()
                .flatMap(Set::stream)
                .mapToDouble(Ressource::getPrix)
                .sum();
    }
}
