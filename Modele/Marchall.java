package Modele;

import java.util.ArrayList;
import java.util.List;

public class Marchall extends Personne implements Movable{
    double nevrosite;

    public Marchall(){
        id = -1;
        nom = "Marshall";
        position = 0;
        nevrosite = Partie.NEVROSITE_MARSHALL;
        sprite = "../Vue/Images/marshall.jpg";
    }

    @Override
    public List<Direction> mouvements_possibles() {
        List<Direction> res = new ArrayList<Direction>();
        if (position == 0)
            res.add(Direction.ARRIERE);
        else if (position == Partie.NB_WAGON - 1)
            res.add(Direction.AVANT);
        else {
            res.add(Direction.AVANT);
            res.add(Direction.ARRIERE);
        }
        return res;
    }

    @Override
    public void move(Train T , Direction d){
        if(position+d.dir()<0 || position+d.dir()== Partie.NB_WAGON){
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
