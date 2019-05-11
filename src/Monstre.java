import java.util.LinkedList;

public class Monstre extends Personnage implements Prenable {

    private Item item;

    /**
     * Constructeur de Monstre
     *
     * @param nom     nom du personnage pour afficher
     * @param maxVies nombre de points de vie maximal
     * @param force   force d'attaque
     * @param posX    coordonnée x du personnage
     * @param posY    coordonnée y du personnage
     * @see Personnage#Personnage(String, int, int, int, int)
     */
    public Monstre(String nom, int maxVies, int force, int posX, int posY, Item item) {
        super(nom, maxVies, force, posX, posY);
        super.symbole = '@';
        this.item = item;
    }


    /**
     * Gère les déplacements du personnage
     *
     * @param posX coordonnée en x du déplacement
     * @param posY coordonnée en x du déplacement
     * @see #deplacerValide(int, int)
     */
    @Override
    public void deplacer(int posX, int posY) {
        if (deplacerValide(posX, posY)) {
            niveau.getGrille()[this.getPosY()][this.getPosX()] = null;

            // Change les coordonnées du monstre
            this.setPosX(posX);
            this.setPosY(posY);

            if (niveau.getGrille()[this.getPosY()][this.getPosX()] == null) {
                niveau.getGrille()[this.getPosY()][this.getPosX()] = new LinkedList<Affichable>();
                niveau.getGrille()[this.getPosY()][this.getPosX()].addFirst(this);

            } else if (niveau.getGrille()[this.getPosY()][this.getPosX()].getFirst() instanceof Joueur) {
                niveau.getGrille()[this.getPosY()][this.getPosX()].add(1, this); // Ajoute le monstre en 2e derriere Zoe

            }
        }

    }


    /**
     * Calcule le déplacement horizontal que doit faire le monstre
     * @param zoe instance du Joueur que doit suivre que monstre
     * @return int déplacement en x
     */
    public int trouverZoeHor(Joueur zoe) {
        if (this.getPosX() < zoe.getPosX()) {
            // Zoe à droite
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * Calcule le déplacement vertical que doit faire le monstre
     *
     * @param zoe nstance du Joueur que doit suivre que monstre
     * @return int déplacement en y
     */
    public int trouverZoeVer(Joueur zoe) {
        if (this.getPosY() > zoe.getPosY()) {
            // Zoe plus haut
            return -1;
        } else {
            return 1;
        }
    }

    /**
     * Attaquer un personnage et afficher le message
     *
     * @param zoe personnage à attaquer
     */
    public void attaquer(Personnage zoe) {
        zoe.perdreVie(zoe.getNbrVies(), this.getForce());
        LegendOfZoe.ajouterMessage(this.getNom() + " attaque " + zoe.getNom() + "!");
    }


    /**
     * Perdre des points de vie
     *
     * @param nbrVies nombre de points de vie {@link Personnage(String, char, int, int, int, int, int)}
     *                {@link #getNbrVies()} {@link #setNbrVies(int)}
     * @param force   force d'attaque {@link Personnage(String, char, int, int, int, int, int)}
     *                {@link #getForce()}
     */
    @Override
    public void perdreVie(int nbrVies, int force) {
        this.setNbrVies(getNbrVies() - force);
    }

    /**
     * Déclenche les événements de la mort du personnage
     */
    @Override
    public void mourir() {
        this.setSymbole('x');
        LegendOfZoe.ajouterMessage(this.getNom() + " meurt!");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void estPris() {
        this.getItem().contenu = Item.Contenu.VIDE;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public Item getItem() {
        return this.item;
    }
}
