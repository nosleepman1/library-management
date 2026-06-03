package model;

public abstract class Ressource {
    private String reference;
    private String titre;
    private double prix;
    private int dureeEmprunt;

    public Ressource(String reference, String titre, double prix, int dureeEmprunt) {
        setReference(reference);
        setTitre(titre);
        setPrix(prix);
        setDureeEmprunt(dureeEmprunt);
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        if (reference == null || reference.isBlank()) {
            throw new IllegalArgumentException("La référence ne peut pas être vide.");
        }
        this.reference = reference.trim();
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        if (titre == null || titre.trim().length() < 3) {
            throw new IllegalArgumentException("Le titre doit comporter au moins 3 caractères.");
        }
        this.titre = titre.trim();
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        if (prix <= 0) {
            throw new IllegalArgumentException("Le prix doit être strictement supérieur à 0.");
        }
        this.prix = prix;
    }

    public int getDureeEmprunt() {
        return dureeEmprunt;
    }

    public void setDureeEmprunt(int dureeEmprunt) {
        if (dureeEmprunt < 1) {
            throw new IllegalArgumentException("La durée d'emprunt doit être au moins de 1 jour.");
        }
        this.dureeEmprunt = dureeEmprunt;
    }

    public abstract String getCategorie();

    @Override
    public String toString() {
        return String.format("[%s] %s - %s - %.2f€ - %d jour(s)", getReference(), getCategorie(), getTitre(), getPrix(), getDureeEmprunt());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Ressource)) {
            return false;
        }
        Ressource other = (Ressource) obj;
        return reference.equals(other.reference);
    }

    @Override
    public int hashCode() {
        return reference.hashCode();
    }
}
