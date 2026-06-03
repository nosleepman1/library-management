package model;

public class Magazine extends Ressource {
    private int numeroEdition;

    public Magazine(String reference, String titre, double prix, int numeroEdition) {
        super(reference, titre, prix, 7);
        setNumeroEdition(numeroEdition);
    }

    public int getNumeroEdition() {
        return numeroEdition;
    }

    public void setNumeroEdition(int numeroEdition) {
        if (numeroEdition <= 0) {
            throw new IllegalArgumentException("Le numéro d'édition doit être supérieur à 0.");
        }
        this.numeroEdition = numeroEdition;
    }

    @Override
    public String getCategorie() {
        return "Magazine";
    }

    @Override
    public String toString() {
        return String.format("%s - Édition: %d", super.toString(), getNumeroEdition());
    }
}
