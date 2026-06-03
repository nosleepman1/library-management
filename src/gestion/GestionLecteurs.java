package gestion;

import model.Lecteur;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GestionLecteurs {
    private final Map<String, Lecteur> lecteurs = new HashMap<>();

    public void ajouterLecteur(Lecteur l) {
        if (l == null) {
            throw new IllegalArgumentException("Le lecteur ne peut pas être nul.");
        }
        if (lecteurs.containsKey(l.getNumeroCarte())) {
            throw new IllegalArgumentException("Un lecteur avec ce numéro de carte existe déjà.");
        }
        lecteurs.put(l.getNumeroCarte(), l);
    }

    public Lecteur rechercher(String numeroCarte) {
        if (numeroCarte == null) {
            return null;
        }
        return lecteurs.get(numeroCarte.trim());
    }

    public List<Lecteur> rechercherParNom(String mot) {
        if (mot == null || mot.isBlank()) {
            return new ArrayList<>();
        }
        String recherche = mot.trim().toLowerCase();
        return lecteurs.values().stream()
                .filter(l -> l.getNom().toLowerCase().contains(recherche))
                .collect(Collectors.toList());
    }

    public List<String> listerEmails() {
        return lecteurs.values().stream()
                .map(Lecteur::getEmail)
                .collect(Collectors.toList());
    }

    public List<Lecteur> listerTousLesLecteurs() {
        return new ArrayList<>(lecteurs.values());
    }
}
