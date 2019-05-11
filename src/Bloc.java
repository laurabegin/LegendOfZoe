import java.lang.annotation.Inherited;

//Classe mère de tous les blocs du jeu
public class Bloc implements Affichable {

    public char symbole;

    @Override
    /**
     *
     * {@inheritDoc}
     *
     * */
    public char getSymbole() {
        return symbole;
    }

    @Override
    /**
     *
     * {@inheritDoc}
     *
     * */
    public void setSymbole(char symbole) {
        this.symbole = symbole;
    }

    public enum Type {MUR, COFFRE, SORTIE}

    public Type type;

    /**
     * Constructeur de bloc
     *
     * @param type type du bloc construit
     */
    public Bloc(Type type) {
        this.type = type;
        switch (type) {
            case MUR:
                symbole = '#';
                break;
            case COFFRE:
                symbole = '$';
                break;
            case SORTIE:
                symbole = 'E';
                break;

        }

    }
}
