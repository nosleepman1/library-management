package model;

public class Ebook extends Ressource {
    private double tailleMo;

    public Ebook(String reference, String titre, double prix, double tailleMo) {
        super(reference, titre, prix, 30);
        setTailleMo(tailleMo);
    }

    public double getTailleMo() {
        return tailleMo;
    }

    public void setTailleMo(double tailleMo) {
        if (tailleMo <= 0) {
            throw new IllegalArgumentException("La taille en Mo doit être supérieure à 0.");
        }
        this.tailleMo = tailleMo;
    }

    @Override
    public String getCategorie() {
        return "Ebook";
    }

    @Override
    public String toString() {
        return String.format("%s - Taille: %.2f Mo", super.toString(), getTailleMo());
    }
}
