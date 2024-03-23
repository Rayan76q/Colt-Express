import java.util.List;
import java.util.Random ;
import java.util.LinkedList;
import java.util.ArrayList ;

interface Movable {
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


    public void braque(Train T){
        return ;
    }

    @Override
    public void move(Train T , Direction d){
        if(d.dir()==2 && !toit) toit = true;
        else if (d.dir()==-2 && toit) toit = false;
        else {
            if(position+d.dir()<0 || position+d.dir()==Train.NB_WAGON){
                return ;
            }
            position += d.dir();
        }
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
    public void move(Train T ,Direction d){
        if(position+d.dir()<0 || position+d.dir()==Train.NB_WAGON){
            return ;
        }
        position += d.dir();
    };

}

class Passager extends Personne{

    private Butin poche;

    public Passager(){
        Random r = new Random();
        id = super.create_id();
        position = r.nextInt(Train.NB_WAGON-1)+1;
        nom = "damn";
        poche =  Butin.values()[r.nextInt(2)];
    }
}