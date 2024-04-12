package Modele;

import java.util.List;
import java.util.Random;

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
    public String move(Train T , Direction d){
        Random r = new Random();
        if(!this.mouvementsPossibles(T).contains(d)){
            return "Le Marshall a perdu le sens de l'orientation";
        }
        if(r.nextDouble() <  nevrosite) {
            T.get_Wagon()[position].interieur.remove(this);
            position += d.dir();
            T.get_Wagon()[position].interieur.add(this);
            List<Bandit> attrape = T.get_Wagon()[position].liste_bandits_int();
            for (Bandit b : attrape) {
                b.fuit_marshall(T.get_Wagon()[position]);
            }
            return "Le Marshall se déplace, prenez gardes.";
        }
        return "Le Marshall planifie sa riposte.";


    }

    //Le marshall ne peut être la cible d'un tir
    @Override
    public boolean isTargeted(){return false;}

    @Override
    public void setTargeted(boolean b){}
}