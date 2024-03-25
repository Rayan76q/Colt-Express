import java.util.LinkedList;

public class Joueur {

    private  LinkedList<Action> Cartes ;


    private BanditSpe Bandit ;

    public Joueur(LinkedList<Action> Cartes , BanditSpe B ){
        this.Cartes = Cartes ;
        this.Bandit = B;
    }

    public Action getCartesid(int id) {
        if (id >= 0 && id < Cartes.size()) {
            return Cartes.get(id);
        } else {
            return null;
        }
    }

    public void A1(int nCarte) {
        if (nCarte >= 0 && nCarte < Cartes.size()) {
            Action carte = getCartesid(nCarte);
            Pile pile = new Pile();
            pile.ajouterCarte(carte);   // A Mettre dans partie plutot
            Cartes.remove(carte);

        }
    }