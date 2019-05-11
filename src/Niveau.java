import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Niveau {
    private Joueur zoe;
    private ArrayList<Monstre> monstres;
    private ArrayList<Coffre> coffres;
    private Bloc sortie;
    private int largeur, hauteur;
    private LinkedList<Affichable>[][] grille;

    /**
     * Constructeur de Niveau
     *
     * @param zoe       reference vers zoe
     * @param numNiveau numero du niveau qu'on veut generer
     */

    public Niveau(Joueur zoe, int numNiveau) {
        this.zoe = zoe;
        this.largeur = LevelGenerator.LARGEUR;
        this.hauteur = LevelGenerator.HAUTEUR;
        this.grille = new LinkedList[hauteur][largeur];
        this.monstres = new ArrayList<Monstre>();
        this.coffres = new ArrayList<Coffre>();


        Paire niveau = LevelGenerator.generateLevel(numNiveau);
        ArrayList<String[]> description = diviserString((String[]) niveau.getValue());
        ajouterMurs((boolean[][]) niveau.getKey());
        ajouterMonstres(description, numNiveau);
        ajouterCoffre(description);
        ajouterSortie(description);
        ajouterZoe(description, zoe);

    }

    /**
     * Ajoute les murs a la grille
     * @param murs indique ou sont les murs
     */
    private void ajouterMurs(boolean[][] murs) {
        for (int i = 0; i < this.hauteur; i++) {
            for (int j = 0; j < this.largeur; j++) {
                if (murs[i][j]) {
                    this.grille[i][j] = new LinkedList<Affichable>();
                    this.grille[i][j].addFirst(new Bloc(Bloc.Type.MUR));
                }
            }
        }
    }

    /**
     * prend le tableau de String generer par LevelGenerator et le transforme en tableau utilisable
     * @param niveauValue la description des objets du niveau en String
     * @return retourne la description du niveau sous forme de tableau de string
     */
    private ArrayList<String[]> diviserString(String[] niveauValue) {
        ArrayList<String[]> tableauNiveauSplit = new ArrayList<String[]>();
        for (int i = 0; i < niveauValue.length; i++) {
            tableauNiveauSplit.add(niveauValue[i].split(":"));
        }
        return tableauNiveauSplit;

    }

    /**
     * Ajoute les monstres a la grille
     * @param description description des objets a placer sur le niveau
     * @param numNiveau numero du niveau
     */
    private void ajouterMonstres(ArrayList<String[]> description, int numNiveau) {
        //instancie le nombre de monstres specifie

        String[] nomsMonstres = {"Armos", "Gel", "Zol", "Peahat", "Chasupa", "Cucco", "Goriya", "Kodondo",
                "Leever", "Octorok"};
        Random noms = new Random();

        for (int i = 0; i < description.size(); i++) {

            if (description.get(i)[0].equals("monstre")) {
                int maxVies = (int) Math.max(0.6 * numNiveau, 1);
                int force = (int) Math.max(0.4 * numNiveau, 1);
                this.monstres.add(new Monstre(nomsMonstres[noms.nextInt(10)], maxVies, force,
                        Integer.parseInt(description.get(i)[2]),
                        Integer.parseInt(description.get(i)[3]),
                        new Item(description.get(i)[1])));

            }
        } //place le monstre sur la grille
        for (int i = 0; i < monstres.size(); i++) {
            this.grille[monstres.get(i).getPosY()][monstres.get(i).getPosX()] = new LinkedList<Affichable>();
            this.grille[monstres.get(i).getPosY()][monstres.get(i).getPosX()].addFirst(monstres.get(i));
            monstres.get(i).setNiveau(this); // Référence vers niveau
        }
    }

    /**
     *  Ajoute les coffre sur la grille
     * @param description description des objets a placer sur le niveau
     */
    private void ajouterCoffre(ArrayList<String[]> description) {

        for (int i = 0; i < description.size(); i++) {
            if (description.get(i)[0].equals("tresor")) {
                this.coffres.add(new Coffre(new Item(description.get(i)[1])));
                int posX = Integer.parseInt(description.get(i)[2]);
                int posY = Integer.parseInt(description.get(i)[3]);
                grille[posY][posX] = new LinkedList<Affichable>();
                grille[posY][posX].addFirst(coffres.get(coffres.size() - 1));//va chercher le dernier coffre instancie
                // et le
                // place sur la grille;
            }

        }

    }

    /**
     * Place la sortie sur la grille
     * @param description description des objets a placer sur le niveau
     */
    private void ajouterSortie(ArrayList<String[]> description) {
        for (int i = 0; i < description.size(); i++) {
            if (description.get(i)[0].equals("sortie")) {
                this.sortie = new Bloc(Bloc.Type.SORTIE);
                int posX = Integer.parseInt(description.get(i)[1]);
                int posY = Integer.parseInt(description.get(i)[2]);
                grille[posY][posX] = new LinkedList<>();
                grille[posY][posX].addFirst(sortie);

            }
        }
    }

    /**
     * place zoe sur la grille
     * @param description description des objets a placer sur le niveau
     * @param zoe reference vers zoe
     */
    private void ajouterZoe(ArrayList<String[]> description, Joueur zoe) {
        for (int i = 0; i < description.size(); i++) {
            if (description.get(i)[0].equals("zoe")) {
                int posX = Integer.parseInt(description.get(i)[1]);
                int posY = Integer.parseInt(description.get(i)[2]);
                grille[posY][posX] = new LinkedList<>();
                grille[posY][posX].addFirst(zoe);
                zoe.setPosX(posX);
                zoe.setPosY(posY);
                zoe.setNiveau(this);

            }
        }
    }

    /**
     * Getter de la Largeur de la grille
     * @return retourne la largeur
     */
    public int getLargeur() {
        return largeur;
    }


    /**
     * getter de la hauteur de la grille
     * @return retourne la hauteur
     */
    public int getHauteur() {
        return hauteur;
    }


    /**
     * Getter de la grille
     * @return retourne la grille
     */
    public LinkedList<Affichable>[][] getGrille() {
        return grille;
    }

    /**
     * Getter de Zoe
     * @return retourne zoe
     */
    public Joueur getZoe() {
        return zoe;
    }

    /**
     * Getter de l'array de monstres
     * @return retourne l'array de Monstres
     */
    public ArrayList<Monstre> getMonstres() {
        return monstres;
    }
}


