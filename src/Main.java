import gestion.GestionEmprunts;
import gestion.GestionLecteurs;
import gestion.GestionRessources;
import model.Ebook;
import model.Lecteur;
import model.Livre;
import model.Magazine;
import model.Ressource;
import exceptions.LecteurInexistantException;
import exceptions.LimiteEmpruntException;
import exceptions.RessourceDejaEmprunteeException;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GestionRessources gestionRessources = new GestionRessources();
        GestionLecteurs gestionLecteurs = new GestionLecteurs();
        GestionEmprunts gestionEmprunts = new GestionEmprunts(gestionLecteurs);

        Scanner scanner = new Scanner(System.in);
        boolean quitter = false;

        while (!quitter) {
            afficherMenu();
            System.out.print("Choix : ");
            String choix = scanner.nextLine().trim();
            System.out.println();

            switch (choix) {
                case "1" -> ajouterRessource(scanner, gestionRessources);
                case "2" -> ajouterLecteur(scanner, gestionLecteurs);
                case "3" -> emprunterRessource(scanner, gestionRessources, gestionEmprunts);
                case "4" -> afficherRessourcesLecteur(scanner, gestionEmprunts);
                case "5" -> listerLecteurs(gestionLecteurs);
                case "6" -> listerRessources(gestionRessources);
                case "7" -> rechercherRessourceParCategorie(scanner, gestionRessources);
                case "8" -> calculerPrixMoyen(gestionRessources);
                case "9" -> afficherValeurTotaleEmpruntees(gestionEmprunts);
                case "10" -> quitter = true;
                default -> System.out.println("Option invalide. Merci de choisir entre 1 et 10.");
            }

            if (!quitter) {
                System.out.println();
                System.out.println("Appuyez sur Entrée pour revenir au menu...");
                scanner.nextLine();
            }
        }

        System.out.println("Au revoir !");
        scanner.close();
    }

    private static void afficherMenu() {
        System.out.println("=== Bibliothèque Numérique ===");
        System.out.println("1. Ajouter une ressource");
        System.out.println("2. Ajouter un lecteur");
        System.out.println("3. Emprunter une ressource");
        System.out.println("4. Afficher les ressources d'un lecteur");
        System.out.println("5. Lister tous les lecteurs");
        System.out.println("6. Lister toutes les ressources");
        System.out.println("7. Rechercher une ressource par catégorie");
        System.out.println("8. Calculer le prix moyen des ressources");
        System.out.println("9. Afficher la valeur totale des ressources empruntées");
        System.out.println("10. Quitter");
    }

    private static void ajouterRessource(Scanner scanner, GestionRessources gestionRessources) {
        System.out.println("Choisissez le type de ressource :");
        System.out.println("1. Livre");
        System.out.println("2. Magazine");
        System.out.println("3. Ebook");
        System.out.print("Type : ");
        String type = scanner.nextLine().trim();

        try {
            Ressource ressource;
            String reference = lireTexte(scanner, "Référence : ");
            String titre = lireTexte(scanner, "Titre : ");
            double prix = lireDouble(scanner, "Prix : ");

            switch (type) {
                case "1" -> {
                    String auteur = lireTexte(scanner, "Auteur : ");
                    ressource = new Livre(reference, titre, prix, auteur);
                }
                case "2" -> {
                    int numeroEdition = lireInt(scanner, "Numéro d'édition : ");
                    ressource = new Magazine(reference, titre, prix, numeroEdition);
                }
                case "3" -> {
                    double tailleMo = lireDouble(scanner, "Taille en Mo : ");
                    ressource = new Ebook(reference, titre, prix, tailleMo);
                }
                default -> {
                    System.out.println("Type invalide. Ressource non ajoutée.");
                    return;
                }
            }
            gestionRessources.ajouterRessource(ressource);
            System.out.println("Ressource ajoutée avec succès.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    private static void ajouterLecteur(Scanner scanner, GestionLecteurs gestionLecteurs) {
        try {
            String numeroCarte = lireTexte(scanner, "Numéro de carte : ");
            String nom = lireTexte(scanner, "Nom : ");
            String email = lireTexte(scanner, "Email : ");
            int age = lireInt(scanner, "Âge : ");

            Lecteur lecteur = new Lecteur(numeroCarte, nom, email, age);
            gestionLecteurs.ajouterLecteur(lecteur);
            System.out.println("Lecteur ajouté avec succès.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    private static void emprunterRessource(Scanner scanner, GestionRessources gestionRessources, GestionEmprunts gestionEmprunts) {
        String numeroCarte = lireTexte(scanner, "Numéro de carte du lecteur : ");
        String reference = lireTexte(scanner, "Référence de la ressource : ");
        Ressource ressource = gestionRessources.rechercherParReference(reference);

        if (ressource == null) {
            System.out.println("Ressource introuvable.");
            return;
        }

        try {
            gestionEmprunts.emprunter(numeroCarte, ressource);
            System.out.println("Emprunt réalisé avec succès.");
        } catch (LecteurInexistantException | RessourceDejaEmprunteeException | LimiteEmpruntException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    private static void afficherRessourcesLecteur(Scanner scanner, GestionEmprunts gestionEmprunts) {
        String numeroCarte = lireTexte(scanner, "Numéro de carte du lecteur : ");
        try {
            List<Ressource> ressources = gestionEmprunts.ressourcesLecteur(numeroCarte);
            if (ressources.isEmpty()) {
                System.out.println("Aucune ressource empruntée pour ce lecteur.");
            } else {
                System.out.println("Ressources empruntées :");
                ressources.forEach(r -> System.out.println("- " + r));
            }
        } catch (LecteurInexistantException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    private static void listerLecteurs(GestionLecteurs gestionLecteurs) {
        List<Lecteur> lecteurs = gestionLecteurs.listerTousLesLecteurs();
        if (lecteurs.isEmpty()) {
            System.out.println("Aucun lecteur enregistré.");
            return;
        }
        System.out.println("Liste des lecteurs :");
        lecteurs.forEach(l -> System.out.println("- " + l));
    }

    private static void listerRessources(GestionRessources gestionRessources) {
        List<Ressource> ressources = gestionRessources.trierParPrixCroissant();
        if (ressources.isEmpty()) {
            System.out.println("Aucune ressource disponible.");
            return;
        }
        System.out.println("Liste des ressources (prix croissant) :");
        ressources.forEach(r -> System.out.println("- " + r));
    }

    private static void rechercherRessourceParCategorie(Scanner scanner, GestionRessources gestionRessources) {
        String categorie = lireTexte(scanner, "Catégorie (Livre, Magazine, Ebook) : ");
        List<Ressource> ressources = gestionRessources.rechercherParCategorie(categorie);
        if (ressources.isEmpty()) {
            System.out.println("Aucune ressource trouvée pour cette catégorie.");
            return;
        }
        System.out.println("Ressources trouvées :");
        ressources.forEach(r -> System.out.println("- " + r));
    }

    private static void calculerPrixMoyen(GestionRessources gestionRessources) {
        double moyenne = gestionRessources.calculerPrixMoyen();
        if (moyenne <= 0) {
            System.out.println("Aucune ressource pour calculer le prix moyen.");
        } else {
            System.out.printf("Prix moyen des ressources : %.2f€%n", moyenne);
        }
    }

    private static void afficherValeurTotaleEmpruntees(GestionEmprunts gestionEmprunts) {
        double valeur = gestionEmprunts.valeurTotaleRessourcesEmpruntees();
        System.out.printf("Valeur totale des ressources empruntées : %.2f€%n", valeur);
    }

    private static String lireTexte(Scanner scanner, String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    private static int lireInt(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            try {
                String ligne = scanner.nextLine().trim();
                return Integer.parseInt(ligne);
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide. Veuillez saisir un nombre entier.");
            }
        }
    }

    private static double lireDouble(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            try {
                String ligne = scanner.nextLine().trim();
                return Double.parseDouble(ligne);
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide. Veuillez saisir un nombre.");
            }
        }
    }
}
