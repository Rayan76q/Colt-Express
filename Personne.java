import java.util.List;
import java.util.Random ;
import java.util.LinkedList;
import java.util.ArrayList ;

interface Movable {
    public List<Direction> mouvements_possibles();
    public void move(Train T, Direction d);
}

public abstract class Personne {
    protected int id;
    protected int position;
    protected String nom;

    static protected List<Integer> ids = new ArrayList<Integer>();


    protected int create_id() {
        int id=0;
        while(ids.contains(id)){
            id++;
        }
        return id;
    }

    @Override
    public String toString() {
        return nom;
    }
}


class Bandit extends Personne implements Movable{
    private boolean toit;
    private int ammo;
    private List<Butin> poches = new LinkedList<Butin>();


    public Bandit(String name){
        id = super.create_id();
        nom = name;
        position = Train.NB_WAGON-1;
        ammo = Train.NB_MUNITIONS;
        toit = true;
    }


    public void ajoute_butin(Butin b){
        poches.add(b);
    }


    public void braque(Train T){
        Random r = new Random();
        Wagon wagon_actuelle = T.get_Wagon()[position];
        int index_butin;
        if(toit){
            index_butin = r.nextInt(wagon_actuelle.loot_toit.size());
            poches.add(wagon_actuelle.loot_toit.get(index_butin));
            wagon_actuelle.loot_toit.remove(index_butin);
        }
        else{
            if(r.nextBoolean()){ //Braque un passager
                index_butin = r.nextInt(wagon_actuelle.liste_passager().size());
                wagon_actuelle.liste_passager().get(index_butin).cede(this);
            }
            else{
                index_butin = r.nextInt(wagon_actuelle.loot_int.size());
                poches.add(wagon_actuelle.loot_int.get(index_butin));
                wagon_actuelle.loot_int.remove(index_butin);
            }
        }
    }


    @Override
    public List<Direction> mouvements_possibles() {
        List<Direction> res = new ArrayList<Direction>();
        if(position==0)
            res.add(Direction.ARRIERE);
        else if(position==Train.NB_WAGON-1)
            res.add(Direction.AVANT);
        else {
            res.add(Direction.AVANT);
            res.add(Direction.ARRIERE);
        }

        if(toit)
            res.add(Direction.BAS);
        else
            res.add(Direction.HAUT);
        return res;

    }

    @Override
    public void move(Train T , Direction d){
        Wagon[] wagons = T.get_Wagon();

        if(d.dir()==2) toit = true;
        else if (d.dir()==-2) toit = false;
        else {
            if(position+d.dir()<0 || position+d.dir()==Train.NB_WAGON){
                return ;
            }
            wagons[position].enleve_personne(this);
            position += d.dir();
        }
        wagons[position].ajoute_personne(this,toit);
    };

    public void tir(Train train, Direction dir) {
        return;
    }
}

class Marchall extends Personne implements Movable{
    double nevrosite;

    public Marchall(){
        id = super.create_id();
        nom = "Marshall";
        position = 0;
        nevrosite = Train.NEVROSITE_MARSHALL;

    }

    @Override
    public List<Direction> mouvements_possibles() {
        List<Direction> res = new ArrayList<Direction>();
        if (position == 0)
            res.add(Direction.ARRIERE);
        else if (position == Train.NB_WAGON - 1)
            res.add(Direction.AVANT);
        else {
            res.add(Direction.AVANT);
            res.add(Direction.ARRIERE);
        }
        return res;
    }

    @Override
    public void move(Train T ,Direction d){
        if(position+d.dir()<0 || position+d.dir()==Train.NB_WAGON){
            return ;
        }
        position += d.dir();
    };



}

class Passager extends Personne{

    private Butin poche;

    public Passager(int p){
        Random r = new Random();
        id = super.create_id();
        position = p;
        nom = "damn";
        poche =  Butin.values()[r.nextInt(2)];
    }


    public void cede(Bandit b){
        b.ajoute_butin(poche);
        poche = null;
    }
}