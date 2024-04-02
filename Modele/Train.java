package Modele;

import java.util.*;


public class Train extends Observable {

    private Wagon[] WAGON = new Wagon[Partie.NB_WAGON];

    public Train(){
        WAGON[0] = new Locomotive();
        for (int i = 1; i < Partie.NB_WAGON ; i++) {
            WAGON[i] = new Cabine(i);
        }
    }
    public Train(int player_number){
        assert(player_number <=8 && player_number>=2):"On ne peut pas jouer avec ce nombre de joeurs.";
        WAGON[0] = new Locomotive();
        for (int i = 1; i < Partie.NB_WAGON ; i++)  WAGON[i] = new Cabine(i);
    }


    public Wagon[] get_Wagon(){
        return WAGON;
    }


    @Override
    public String toString(){
//        String acc = "this train has: \n - "+NB_WAGON +" wagons.\n - "+
//                NB_JOEURS + " players.\n - "+NB_MUNITIONS +" munitions.\n - "
//                +NB_PASSAGER_PAR_WAGON_MAX+" passagers maximum in one wagon.\n - "
//                +"One marshall that is "+NEVROSITE_MARSHALL +" over 1 nevrosite.\n"+"Now for the wagons:\n";
        String acc = "Les wagons du trains : \n";
        for (int i = 0; i < Partie.NB_WAGON ; i++) {
            acc += "----------- \n";
            acc += WAGON[i].toString();
        }
        acc += " END. \n";
        return acc;

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
        if(r.nextDouble() < Partie.PROBA_PERTE_LOOT_TOIT){
            int rand_index = r.nextInt(toit.size());
            toit.remove(rand_index);
        }
    }



    public abstract void magot_vole();
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
        for (int i = 0; i < r.nextInt(Partie.NB_PASSAGER_PAR_WAGON_MAX-1)+1; i++) {
            interieur.add(new Passager(p));
        }
    }
}


class Locomotive extends Wagon{
    private Butin mag = Butin.MAGOT ;

    @Override
    public String toString() {
        if (mag != null) {
            return "C'est la locomotive.\n"+ "elle contient le magot!\n"+ super.toString();
        }
        return "C'est la locomotive.\n"+ "elle ne contient pas le magot!\n"+ super.toString();
    }


    public boolean magot_dispo(){return Butin.MAGOT!=null;}

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

    public void magot_vole(){
        mag = null;
    }
}