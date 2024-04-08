package Modele;

import java.util.ArrayList;
import java.util.List;

public class Marchall extends Personne implements Movable{
    double nevrosite;

    public Marchall(){
        super("Marshall");
        id = -1;
        position = 0;
        nevrosite = Partie.NEVROSITE_MARSHALL;
        sprite = "../Vue/Images/marshall.png";
    }

    @Override
    public List<Direction> mouvementsPossibles(Train t) {
        return t.mouvementPossibles(false,position,false);
    }

    @Override
    public void move(Train T , Direction d){
        if(!this.mouvementsPossibles(T).contains(d)){
            System.out.println("Mouvement Invalide");
            return ;
        }
        T.get_Wagon()[position].interieur.remove(this);
        position += d.dir();
        T.get_Wagon()[position].interieur.add(this);
        List<Bandit> attrape =  T.get_Wagon()[position].liste_bandits_int();
        for (Bandit b : attrape){
            b.fuit_marshall(T.get_Wagon()[position]);
        }

    }

    //@Override
    //public void est_vise(Wagon wagon){};
}