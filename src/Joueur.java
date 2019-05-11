import java.util.LinkedList;

public final class Joueur extends Personnage implements Affichable {


    private int nbrHexa;
    private static boolean partieGagne = false;

    // MÉTHODES :

    /**
     * Constructeur de Joueur {@link Personnage#Personnage(String, int, int, int, int)}
     *
     * @param nom nom du joueur pour afficher
     * @param symbole caractère qui représente le personnage
     * @param maxVies nombre de points de vie maximal
     * @param force force d'attaque
     * @param posX coordonnée x du personnage
     * @param posY coordonnée y du personnage
     */
    public Joueur(String nom, char symbole, int maxVies, int force, int posX, int posY) {
        super(nom, maxVies, force, posX, posY);
        this.nbrHexa = 0;
        this.symbole = symbole;
    }

    /**
     * Accède au nombre d'hexaforces du joueur
     *
     * @return nbrHexa nombre d'hexaforces
     */
    public int getNbrHexa() {
        return nbrHexa;
    }

    /**
     * Ajoute un hexaforce au joueur
     * @see #getNbrHexa()
     */
    public void ajouterHexa() {
        this.nbrHexa = nbrHexa + 1;

        if (nbrHexa == 6) {
            Joueur.partieGagne = true;
        }
    }

    /**
     * Indique si la partie est gagnée
     *
     * @return true si la partie est gagnée, false sinon
     */
    public static boolean isPartieGagne() {
        return partieGagne;
    }

    /**
     * Creuse dans les murs autour du joueir
     */
    public void creuser() {

        // Vérifie les 8 cases autour du joueur
        for (int i = this.getPosY() - 1; i <= this.getPosY() + 1; i++) {
            for (int j = this.getPosX() - 1; j <= this.getPosX() + 1; j++) {

                if (i >= 0 && i < niveau.getHauteur() && j >= 0 && j < niveau.getLargeur()) {
                    if (niveau.getGrille()[i][j] != null && niveau.getGrille()[i][j].getFirst().getSymbole() == '#') {
                        niveau.getGrille()[i][j] = null; // Efface le symbole # du mur
                    }
                }
            }
        }
    }

    /**
     * Ouvre un coffre et prend l'item à l'intérieur
     */
    public void ouvrirCoffre() {

        // Vérifie les 8 cases autour du joueur
        for (int i = this.getPosY() - 1; i <= this.getPosY() + 1; i++) {
            for (int j = this.getPosX() - 1; j <= this.getPosX() + 1; j++) {

                if (i >= 0 && i < niveau.getHauteur() && j >= 0 && j < niveau.getLargeur()) {
                    if (niveau.getGrille()[i][j] != null && niveau.getGrille()[i][j].getFirst() instanceof Coffre) {

                        Coffre coffre = (Coffre) niveau.getGrille()[i][j].getFirst();
                        this.prendre(coffre.getItem()); // Prend l'item
                        coffre.estPris();
                    }
                }
            }
        }
    }

    /**
     * Prend un item, affiche un message dans la console et applique les effets
     *
     * @param item objet porté par un monstre ou dans un coffre
     * @see Item
     */
    public void prendre(Item item) {

        switch (item.contenu) {
            case HEXAFORCE:
                LegendOfZoe.ajouterMessage("Vous avez trouvé un morceau d'hexaforce!");
                this.ajouterHexa();
                break;


            case POTION:
                LegendOfZoe.ajouterMessage("Vous avez trouvé une potion!");
                this.gagnerVie(item);
                break;

            case COEUR:
                LegendOfZoe.ajouterMessage("Vous avez trouvé un coeur!");
                this.gagnerVie(item);
                break;
        }

    }

    /**
     * Gère les déplacements du joueur. Peut se trouver sur la même case qu'un monstre.
     *
     * @param posX coordonnée en x du déplacement {@link #setPosX(int)} {@link #getPosX()}
     * @param posY coordonnée en y du déplacement {@link #setPosY(int)} {@link #getPosY()}
     */
    @Override
    public void deplacer(int posX, int posY) {
        if (deplacerValide(posX, posY)) {
           if(niveau.getGrille()[this.getPosY()][this.getPosX()]!=null&&
                   niveau.getGrille()[this.getPosY()][this.getPosX()].size()>1){
            niveau.getGrille()[this.getPosY()][this.getPosX()].remove(this);
           } else{
               niveau.getGrille()[this.getPosY()][this.getPosX()]=null;
           }

            // Change les coordonnées du joueur
            this.setPosX(posX);
            this.setPosY(posY);

            if (niveau.getGrille()[this.getPosY()][this.getPosX()] == null) {
                niveau.getGrille()[this.getPosY()][this.getPosX()] = new LinkedList<Affichable>();
                niveau.getGrille()[this.getPosY()][this.getPosX()].addFirst(this);

            } else if (niveau.getGrille()[this.getPosY()][this.getPosX()].getFirst() instanceof Monstre) {
                // Place le joueur sur la même case qu'un monstre
                niveau.getGrille()[this.getPosY()][this.getPosX()].addFirst(this);
            }
        }
    }



    /**
     * Attaque tous les monstres dans les cases adjacentes, dont la case où se trouve le joueur
     */
    public void attaquer() {

        // Vérifie les 9 cases
        for (int i = this.getPosY() - 1; i <= this.getPosY() + 1; i++) {
            for (int j = this.getPosX() - 1; j <= this.getPosX() + 1; j++) {

                if (i >= 0 && i < niveau.getHauteur() && j >= 0 && j < niveau.getLargeur()) {
                    if (niveau.getGrille()[i][j] != null && niveau.getGrille()[i][j].getFirst() instanceof Monstre) {

                        Monstre monstre = (Monstre) niveau.getGrille()[i][j].getFirst();
                        if (monstre.enVie(monstre.getNbrVies())) {
                            monstre.perdreVie(monstre.getNbrVies(), this.getForce()); // Attaquer le monstre
                            LegendOfZoe.ajouterMessage(this.getNom() + " attaque " + monstre.getNom() + "!");

                            // Si le monstre meurt
                            if (!monstre.enVie(monstre.getNbrVies())) {
                                monstre.mourir();
                                this.prendre(monstre.getItem());// Prendre item du monstre
                                monstre.estPris();
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param nbrVies nombre de points de vie {@link #getNbrVies()} {@link #setNbrVies(int)}
     * @param force   force d'attaque {@link #getForce()}
     */
    @Override
    public void perdreVie(int nbrVies, int force) {

        if ((this.getNbrVies() - force) > 0) {
            this.setNbrVies(this.getNbrVies() - force);
        } else {
            this.setNbrVies(0); // Le joueur ne peut pas avoir un nombre de points de vie négatif
        }
    }

    /**
     * Ajoute des points de vie au joueur selon l'effet de l'item
     *
     * @param item {@link #prendre(Item)}
     * @see Item
     */
    public void gagnerVie(Item item) {

        switch (item.contenu) {
            case COEUR:
                if (this.getNbrVies() < this.getMaxVies()) {
                    this.setNbrVies(this.getNbrVies() + 1); // Ajoute un point de vie
                }
                break;

            case POTION:
                this.setNbrVies(this.getMaxVies()); // Rétablit les points de vie
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mourir() {

        Messages.afficherDefaite();
    }
}
