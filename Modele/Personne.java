package Modele;


import java.util.List;




interface Movable {
    List<Direction> mouvements_possibles();
    void move(Train T, Direction d);
}

interface Hitable{
    void drop_butin(Wagon w);
    void est_vise(Wagon wagon);
}

public abstract class Personne {
    protected int id;
    protected int position;
    protected String nom;
    protected String sprite;

    static protected int current_id_bandit = 0 ;
    static protected int getCurrent_id_passager = -2;


    public int get_id(){ return this.id;}

    @Override
    public String toString() {
        return nom;
    }
    public String getSprite(){return sprite;}
}


