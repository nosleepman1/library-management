package model;

public class Livre extends Ressource {
    private String auteur;

    public Livre(String reference, String titre, double prix, String auteur) {
        super(reference, titre, prix, 14);
        setAuteur(auteur);
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        if (auteur == null || auteur.trim().isEmpty()) {
            throw new IllegalArgumentException("L'auteur est obligatoire pour un livre.");
        }
        this.auteur = auteur.trim();
    }

    @Override
    public String getCategorie() {
        return "Livre";
    }

    @Override
    public String toString() {
        return String.format("%s - Auteur: %s", super.toString(), getAuteur());
    }
}
