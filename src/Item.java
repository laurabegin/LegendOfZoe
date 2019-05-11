

//Item permet de representer un objet porté par un monstre ou dans un coffre
public class Item {

    public static enum Contenu {VIDE, COEUR, POTION, HEXAFORCE}

    ;
    public Contenu contenu;


    /**
     * Constructeur de Item
     *
     * @param nom nom de l'item qui spécifie son type
     */
    public Item(String nom) {
        switch (nom) {
            case "coeur":
                this.contenu = Contenu.COEUR;
                break;

            case "potionvie":
                this.contenu = Contenu.POTION;
                break;

            case "hexaforce":
                this.contenu = Contenu.HEXAFORCE;
                break;
        }
    }
}
