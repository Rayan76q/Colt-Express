import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Train {
    public static final int NB_WAGON = 4;
    public static final int NB_MUNITIONS = 6;
    public static final int NB_JOEURS = 4;
    public static final double NEVROSITE_MARSHALL = 0.3;
    public static final int NB_PASSAGER_PAR_WAGON_MAX = 4;
}




abstract class Wagon {
    static protected int size = 0;    //compteur pour la construction
    protected int position;

    protected List<Personne> interieur;
    protected List<Butin> loot_int;  //loot droppé à l'interieur du wagon
    protected List<Bandit> toit;
    protected List<Butin> loot_toit; //loot droppé sur le toit du wagon



}



class Cabine extends Wagon{


    public Cabine(){
        assert size > 0;
        Random r = new Random();
        position = size;
        size++;
        loot_int = new LinkedList<Butin>();
        loot_toit = new LinkedList<Butin>();
        toit = new ArrayList<Bandit>();
        interieur = new ArrayList<Personne>();
        for (int i = 0; i < r.nextInt(Train.NB_PASSAGER_PAR_WAGON_MAX-1)+1; i++) {
            interieur.add(new Passager());
        }
    }
}


class Locomotive extends Wagon{
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
    }
}