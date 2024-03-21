import java.util.List;
import java.util.Random ;
import java.util.LinkedList;
import java.util.ArrayList ;

interface Movable {
    public void move(Direction d);
}

public abstract class Personne {
    protected int id;
    protected int position;
    protected String nom;

    static protected List<Integer> ids = new ArrayList<Integer>();


    protected int create_id() {
        Random r = new Random();
        int id;
        do{
            id = r.nextInt(Train.NB_JOEURS+ Train.NB_WAGON*100);
        }while(ids.contains(id)) ;
        return id;
    }

    @Override
    public String toString() {return nom;}
}


class Bandit extends Personne implements Movable{
    private boolean toit;
    private int ammo;
    private List<Butin> poches = new LinkedList<Butin>();


    public Bandit(String name , int taille_train , int ammo_init){
        id = super.create_id();
        nom = name;
        position = taille_train-1;
        ammo = ammo_init;
        toit = true;
    }

    @Override
    public void move(Direction d){return ;};
}

class Marchall extends Personne implements Movable{
    double nevrosite;

    public Marchall(double nev){
        id = super.create_id();
        nom = "Marshall";
        position = 0;
        nevrosite = nev;

    }

    @Override
    public void move(Direction d){return ;};

}

class Passager extends Personne{

    private Butin poche;

    public Passager(){
        Random r = new Random();
        id = super.create_id();
        position = r.nextInt(Train.NB_WAGON-1)+1;
        nom = "";
        poche =  Butin.values()[r.nextInt(2)];
    }



}