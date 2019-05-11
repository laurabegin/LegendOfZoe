
//Classe enfant de bloc
public class Coffre extends Bloc implements Prenable {

    Item item;


    /**
     * Change le item du coffre après qu'il est été looté, ainsi que sa représentation
     */
    public void estPris() {
        this.getItem().contenu = Item.Contenu.VIDE;
        this.setSymbole('_');
    }

    /**
     * Getter de Item
     *
     * @return retourne litem dans le coffre
     */
    public Item getItem() {
        return this.item;

    }


    /**
     * Constructeur de Coffre
     * @param item item qu'on veut mettre dans le coffre
     * */
    public Coffre(Item item) {
        super(Type.COFFRE);
        this.item = item;
    }


}

