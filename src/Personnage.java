abstract class Personnage implements Affichable {

    // ATTRIBUTS :

    protected String nom;
    protected char symbole;

    protected int nbrVies;
    protected int maxVies;
    protected int force;

    protected int posX;
    protected int posY;

    /**
     * Référence vers le niveau où le personnage à été instancié. null à l'instanciation.
     */
    protected Niveau niveau;

    // MÉTHODES :

    /**
     * Constructeur de Personnage
     *
     * @param nom     nom du personnage pour afficher
     * @param maxVies nombre de points de vie maximal
     * @param force   force d'attaque
     * @param posX    coordonnée x du personnage
     * @param posY    coordonnée y du personnage
     */
    public Personnage(String nom, int maxVies, int force, int posX, int posY) {
        this.nom = nom;
        this.nbrVies = maxVies;
        this.maxVies = maxVies;
        this.force = force;
        this.posX = posX;
        this.posY = posY;
    }


    /**
     * Accède au nom du personnage
     *
     * @return nom du personnage
     */
    public String getNom() {
        return nom;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char getSymbole() {
        return symbole;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSymbole(char symbole) {
        this.symbole = symbole;
    }

    /**
     * Accède au nombre de points de vie
     *
     * @return nombre de points de vie
     */
    public int getNbrVies() {
        return nbrVies;
    }

    /**
     * Modifie le nombre de points de vie
     *
     * @param nbrVies nombre de points de vie
     */
    public void setNbrVies(int nbrVies) {
        this.nbrVies = nbrVies;
    }

    /**
     * Accède au nombre de points de vie maximal
     *
     * @return nombre de points de vie maximal
     */
    public int getMaxVies() {
        return maxVies;
    }

    /**
     * Accède à la force d'attque
     *
     * @return force d'attaque
     */
    public int getForce() {
        return force;
    }


    /**
     * Accède à la coordonnée x du personnage
     *
     * @return coordonnée x du personnage
     */

    public int getPosX() {
        return this.posX;
    }

    ;

    /**
     * Accède à la coordonnée y du personnage
     *
     * @return coordonnée y du personnage
     */
    public int getPosY() {
        return this.posY;
    }

    /**
     * Modifie la coordonnée x du personnage
     *
     * @param posX coordonnée x du personnage à modifier
     */
    void setPosX(int posX) {

        if (posX >= 0 && posX < LevelGenerator.LARGEUR) {
            this.posX = posX;
        }
    }

    /**
     * Modifie la coordonnée y du personnage
     *
     * @param posY coordonnée y du personnage à modifier
     */
    void setPosY(int posY) {
        if (posY >= 0 && posY < LevelGenerator.HAUTEUR) {
            this.posY = posY;
        }
    }

    /**
     * Modifie la référence du niveau où le personnage a été instancié
     *
     * @param niveau référence du niveau où le personnage a été instanciée
     */
    protected void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    /**
     * Vérifie si le personnage est en vie
     *
     * @param nbrVies nombre de points de vies {@link #getNbrVies()} {@link #setNbrVies(int)}
     * @return boolean : true si le personnage est en vie, false sinon
     */
    public boolean enVie(int nbrVies) {
        if (nbrVies > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gère les déplacements du personnage
     *
     * @param posX coordonnée en x du déplacement {@link #setPosX(int)} {@link #getPosX()}
     * @param posY coordonnée en y du déplacement {@link #setPosY(int)} {@link #getPosY()}
     * @see #deplacerValide(int, int)
     */
    public abstract void deplacer(int posX, int posY);

    /**
     * Vérifie si le déplacement est valide
     *
     * @param posX coordonnée en x du déplacement
     * @param posY coordonnée en y du déplacement
     * @return boolean : true si valide, false sinon
     * @see #deplacer(int, int)
     */
    public boolean deplacerValide(int posX, int posY) {
        if (posX >= 0 && posY >= 0 && posX < niveau.getLargeur() && posY < niveau.getHauteur()) {
            if (niveau.getGrille()[posY][posX] == null) {
                return true;
            } else {
                return (!(niveau.getGrille()[posY][posX].getFirst() instanceof Bloc));
                // Impossible de se déplacer sur un bloc (MUR, COFFRE ou SORTIE)
            }
        } else {
            return false;
        }
    }

    /**
     * Perdre des points de vie
     *
     * @param nbrVies nombre de points de vie {@link #getNbrVies()} {@link #setNbrVies(int)}
     * @param force   force d'attaque {@link #getForce()}
     */
    public abstract void perdreVie(int nbrVies, int force);

    /**
     * Déclenche les événements de la mort du personnage
     */
    public abstract void mourir();

}
