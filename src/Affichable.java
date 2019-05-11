/**
 * Interface de tout ce qui sera affiché dans le jeu. S'assure que chaque objet sera représenté par un symbole
 * à l'écran.
 */

public interface Affichable {

    /**
     * Accède au symbole du personnage
     *
     * @return symbole caractère qui représente le personnage
     */
    public char getSymbole();

    /**
     * Modifie le symbole du personnage
     *
     * @param symbole caractère qui représente le personnage
     */
    public void setSymbole(char symbole);


}



