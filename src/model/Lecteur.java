package model;

public class Lecteur {
    private String numeroCarte;
    private String nom;
    private String email;
    private int age;

    public Lecteur(String numeroCarte, String nom, String email, int age) {
        setNumeroCarte(numeroCarte);
        setNom(nom);
        setEmail(email);
        setAge(age);
    }

    public String getNumeroCarte() {
        return numeroCarte;
    }

    public void setNumeroCarte(String numeroCarte) {
        if (numeroCarte == null || numeroCarte.isBlank()) {
            throw new IllegalArgumentException("Le numéro de carte ne peut pas être vide.");
        }
        this.numeroCarte = numeroCarte.trim();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        if (nom == null || nom.trim().length() < 2) {
            throw new IllegalArgumentException("Le nom doit comporter au moins 2 caractères.");
        }
        this.nom = nom.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("L'email doit contenir un caractère '@'.");
        }
        this.email = email.trim();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 12) {
            throw new IllegalArgumentException("L'âge doit être au moins de 12 ans.");
        }
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s - %d ans", getNumeroCarte(), getNom(), getEmail(), getAge());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Lecteur)) {
            return false;
        }
        Lecteur other = (Lecteur) obj;
        return numeroCarte.equals(other.numeroCarte);
    }

    @Override
    public int hashCode() {
        return numeroCarte.hashCode();
    }
}
