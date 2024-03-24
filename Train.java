import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Train {
    public static int NB_WAGON = 4;
    public static final int NB_MUNITIONS = 6;
    public static final double DEFAULT_PRECISION = 0.9;
    public static final int DEFAULT_HP = 6;
    public static int NB_JOUEURS = 4;
    public static final double NEVROSITE_MARSHALL = 0.3;
    public static final int NB_PASSAGER_PAR_WAGON_MAX = 4;
    public static final double PROBA_PERTE_LOOT_TOIT = 0.05;



    private Wagon[] WAGON = new Wagon[NB_WAGON];

    @Override
    public String toString(){
//        String acc = "this train has: \n - "+NB_WAGON +" wagons.\n - "+
//                NB_JOEURS + " players.\n - "+NB_MUNITIONS +" munitions.\n - "
//                +NB_PASSAGER_PAR_WAGON_MAX+" passagers maximum in one wagon.\n - "
//                +"One marshall that is "+NEVROSITE_MARSHALL +" over 1 nevrosite.\n"+"Now for the wagons:\n";
        String acc = "Les wagons du trains : \n";
        for (int i = 0; i <NB_WAGON ; i++) {
            acc += "----------- \n";
            acc += WAGON[i].toString();
        }
        acc += " END. \n";
        return acc;

    }

    public Train(){
        WAGON[0] = new Locomotive();
        for (int i = 1; i < NB_WAGON ; i++) {
            WAGON[i] = new Cabine(i);
        }
    }
    public Train(int player_number){
        assert(player_number <=6 && player_number>=3):"On ne peut pas jouer avec ce nombre de joeurs.";
        this.NB_JOUEURS = player_number;
        this.NB_WAGON = player_number;
        WAGON[0] = new Locomotive();
        for (int i = 1; i < NB_WAGON ; i++)  WAGON[i] = new Cabine(i);
    }


    public Wagon[] get_Wagon(){
        return WAGON;
    }


}




abstract class Wagon {
    static protected int size = 0;    //compteur pour la construction
    protected int position;

    protected List<Personne> interieur;
    protected List<Butin> loot_int;  //loot droppé à l'interieur du wagon
    protected List<Bandit> toit;
    protected List<Butin> loot_toit; //loot droppé sur le toit du wagon

    public String toString(){
        String inside = "";
        String top = " ";
        for (Personne personne : interieur) {
            inside += (personne.toString()+", ");
        }
        for (Bandit bandit : toit) {
            top += (bandit.toString() +", ");
        }

        return "C'est le Wagon no " + position +".\nil a :\n - "+ interieur.size() +
                " individus dedans : "+inside+"\n - "+ toit.size() +" bandits sur le toit : "+top+"\n";
    }


    public List<Passager> liste_passagers(){
        List<Passager> res = new LinkedList<Passager>();
        for (Personne p : interieur){
            if(p instanceof Passager)
                res.add((Passager) p);
        }
        return res;
    }

    public List<Bandit> liste_bandits_int(){
        List<Bandit> res = new LinkedList<Bandit>();
        for (Personne p : interieur){
            if(p instanceof Bandit)
                res.add((Bandit) p);
        }
        return res;
    }



    public void enleve_personne(Personne p){
        interieur.remove(p);
        toit.remove((Bandit)p);
    }

    public void ajoute_personne(Personne p , Boolean roof){
        if(roof) {
            assert  p instanceof Bandit;
            toit.add((Bandit) p);}
        else interieur.add(p);
    }


    public void perte_loot_toit(){  //Un loot du toit disparait avec une certaine proba
        Random r = new Random();
        if(r.nextDouble() < Train.PROBA_PERTE_LOOT_TOIT){
            int rand_index = r.nextInt(toit.size());
            toit.remove(rand_index);
        }
    }

}



class Cabine extends Wagon{

    public Cabine(int p){
        assert size > 0;
        Random r = new Random();
        position = size;
        size++;
        loot_int = new LinkedList<Butin>();
        loot_toit = new LinkedList<Butin>();
        toit = new ArrayList<Bandit>();
        interieur = new ArrayList<Personne>();
        for (int i = 0; i < r.nextInt(Train.NB_PASSAGER_PAR_WAGON_MAX-1)+1; i++) {
            interieur.add(new Passager(p));
        }
    }
}


class Locomotive extends Wagon{
    private boolean magot_dispo;

    @Override
    public String toString() {
        if (magot_dispo) {
            return "C'est la locomotive.\n"+ "elle contient le magot!\n"+ super.toString();
        }
        return "C'est la locomotive.\n"+ "elle ne contient pas le magot!\n"+ super.toString();
    }

    private Butin mag = Butin.MAGOT ;

    public Locomotive(){
        assert size==0;
        position = 0;
        size++;
        interieur = new ArrayList<Personne>();
        interieur.add(new Marchall());
        loot_int = new LinkedList<Butin>();
        loot_toit = new LinkedList<Butin>();
        toit = new ArrayList<Bandit>();
        magot_dispo = true;
    }
}