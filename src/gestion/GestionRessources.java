package gestion;

import model.Ressource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GestionRessources {
    
    private final Map<String, Ressource> ressources = new HashMap<>();

    public void ajouterRessource(Ressource r) {
        
        if (r == null) {
            throw new IllegalArgumentException("La ressource ne peut pas être nulle.");
        }

        if (ressources.containsKey(r.getReference())) {
            throw new IllegalArgumentException("Une ressource avec cette référence existe déjà.");
        }
        ressources.put(r.getReference(), r);
    }

    public Ressource rechercherParReference(String reference) {
        if (reference == null) {
            return null;
        }
        return ressources.get(reference.trim());
    }

    public List<Ressource> rechercherParCategorie(String categorie) {
        if (categorie == null || categorie.isBlank()) {
            return new ArrayList<>();
        }

        String recherche = categorie.trim();
    
        return ressources.values().stream()
                .filter(r -> r.getCategorie().equalsIgnoreCase(recherche))
                .collect(Collectors.toList());
    }

    public double calculerPrixMoyen() {
        if (ressources.isEmpty()) {
            return 0.0;
        }
        return ressources.values().stream()
                .mapToDouble(Ressource::getPrix)
                .average()
                .orElse(0.0);
    }

    public List<Ressource> trierParPrixCroissant() {
        return ressources.values().stream()
                .sorted(Comparator.comparingDouble(Ressource::getPrix))
                .collect(Collectors.toList());
    }

    public List<Ressource> listerToutesLesRessources() {
        return new ArrayList<>(ressources.values());
    }
}
