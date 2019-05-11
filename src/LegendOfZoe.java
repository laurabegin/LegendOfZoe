/**
 * Classe principale du programme.
 * <p>
 * NOTEZ : VOUS NE DEVEZ PAS RENOMMER CETTE CLASSE
 */

import java.lang.StringBuilder;
import java.util.LinkedList;
import java.util.Scanner;

public class LegendOfZoe {

    private static Joueur zoe = new Joueur("Zoe", '&', 5, 1, 0, 0);
    private static Niveau niveau;
    private static LinkedList<String> file;
    private static int numeroNiveau;
    public static void main(String[] args) {
        numeroNiveau = 1;
        file = new LinkedList<>();
        niveau = new Niveau(zoe, numeroNiveau);

        Messages.afficherIntro();
        afficherEtatZoe(zoe);
        afficherNiveau(niveau);

        while (zoe.enVie(zoe.getNbrVies()) && !Joueur.isPartieGagne()) {
            if (zoe.getNbrHexa() == numeroNiveau && procheSortie()) {
                chargerProchainNiveau();
            }

            lireEtExecuterCommandes(niveau);
            if (Joueur.isPartieGagne()) {
                Messages.afficherVictoire();
                System.exit(0);
            }
        }
        Messages.afficherDefaite();


    }

    /**
     * Affiche une ligne qui indique combien de vie et combien dhexaforce porte Zoe
     *
     * @param zoe reference vers zoe
     */
    private static void afficherEtatZoe(Joueur zoe) {
        int vies = zoe.getNbrVies();
        int hexa = zoe.getNbrHexa();
        StringBuilder ligne = new StringBuilder("Zoe: ");

        for (int i = 0; i < vies; i++) {
            ligne.append("♥ ");
        }
        for (int i = 0; i < 5 - vies; i++) {
            ligne.append("_ ");
        }
        ligne.append("| ");
        for (int i = 0; i < hexa; i++) {
            ligne.append("Δ ");
        }
        for (int i = 0; i < 6 - hexa; i++) {
            ligne.append("_ ");
        }
        System.out.println(ligne.toString());
    }

    /**
     * Affiche le niveau
     * @param niveau le niveau a afficher
     */
    private static void afficherNiveau(Niveau niveau) {

        StringBuilder ligne = new StringBuilder(niveau.getLargeur() + 1);
        for (int i = 0; i < niveau.getHauteur(); i++) {
            for (int j = 0; j < niveau.getLargeur(); j++) {
                if (niveau.getGrille()[i][j] == null) {
                    ligne.append(" ");
                } else {

                    ligne.append(niveau.getGrille()[i][j].getFirst().getSymbole());
                }
            }
            ligne.trimToSize();
            System.out.println(ligne.toString());

            ligne = new StringBuilder("");
        }
    }

    /**
     * Lit et execute les commande ecrit a la console. Mets a jour le niveau apres chaque commande
     *
     * @param niveau reference vers le niveau
     */
    private static void lireEtExecuterCommandes(Niveau niveau) {
        Scanner commandes = new Scanner(System.in);
        String operations = commandes.nextLine();
        for (int i = 0; i < operations.length(); i++) {
            char action = operations.charAt(i);

            switch (action) {
                case 'w':
                    // deplace haut
                    zoe.deplacer(zoe.getPosX(), zoe.getPosY() - 1);
                    break;

                case 's':
                    // deplace bas
                    zoe.deplacer(zoe.getPosX(), zoe.getPosY() + 1);
                    break;

                case 'a':
                    // deplace gauche
                    zoe.deplacer(zoe.getPosX() - 1, zoe.getPosY());
                    break;

                case 'd':
                    //deplacer droite
                    zoe.deplacer(zoe.getPosX() + 1, zoe.getPosY());
                    break;

                case 'x':
                    //attakk
                    zoe.attaquer();
                    break;

                case 'c':
                    //digg;
                    zoe.creuser();
                    break;

                case 'o':
                    //get rich $$$
                    zoe.ouvrirCoffre();
                    break;

                case 'q':
                    System.exit(0);


            }
            if (i == operations.length() - 1) {
                afficherMessages();
            }
            mettreAJour(niveau);
        }


    }

    /**   mets a jour la grille et execute un tour des monstres
     *
     * @param niveau reference vers le niveau
     */
    private static void mettreAJour(Niveau niveau) {

        tourMonstres();
        afficherEtatZoe(niveau.getZoe());
        afficherNiveau(niveau);
    }

    /**
     * Gere le tour des monstres, leur deplacements, sil attaquent Zoe
     */
    private static void tourMonstres() {
        for (int k = 0; k < niveau.getMonstres().size(); k++) {
            Monstre monstre = niveau.getMonstres().get(k);
            if (monstre.enVie(monstre.getNbrVies())) {

                for (int i = monstre.getPosY() - 1; i <= monstre.getPosY() + 1; i++) {
                    for (int j = monstre.getPosX() - 1; j <= monstre.getPosX() + 1; j++) {
                        if (i >= 0 && i < niveau.getHauteur() && j >= 0 && j < niveau.getLargeur()) {
                            if (niveau.getGrille()[i][j] != null && niveau.getGrille()[i][j].getFirst() instanceof Joueur) {
                                monstre.attaquer(zoe);
                            }

                        }
                    }

                }


                int deplacementX = monstre.getPosX() + monstre.trouverZoeHor(zoe);
                int deplacementY = monstre.getPosY() + monstre.trouverZoeVer(zoe);
                monstre.deplacer(deplacementX, deplacementY);
            }

        }
    }

    /**
     * Determine si Zoe est dans la portée de la sortie
     * @return true si elle est proche, false sinon
     */
    private static boolean procheSortie() {
        for (int i = zoe.posY - 1; i <= zoe.posY + 1; i++) {
            for (int j = zoe.posX - 1; j <= zoe.posX + 1; j++) {
                if (i > 0 && i < 14 && j > 0 && j < 40 && niveau.getGrille()[i][j] != null && niveau.getGrille()[i][j].getFirst().getSymbole() == 'E') {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     ajoute des messages a la file de message
     */
    public static void ajouterMessage(String message) {
        file.add(message);
    }

    /**
     * affiche la file de messages
     */
    private static void afficherMessages() {
        while (file.size() > 0) {
            System.out.println(file.getFirst());
            file.removeFirst();
        }
    }

    /**
     * Charge le prochain niveau
     */
    private static void chargerProchainNiveau() {
        numeroNiveau++;
        niveau = new Niveau(zoe, numeroNiveau);
        System.out.println("Vous entrez dans le niveau " + numeroNiveau);
        afficherEtatZoe(zoe);
        afficherNiveau(niveau);

    }
}

