package Modele;

import java.util.Random;

public class Passager extends Personne implements Hitable{

    private Butin poche;

    public Passager(int p){
        Random r = new Random();
        id = getCurrent_id_passager--;
        position = p;
        nom = "passager"+id;
        poche =  Butin.values()[r.nextInt(2)];
    }


    public void cede(Bandit b){
        b.ajoute_butin(poche);
        poche = null;
    }

    @Override
    public void drop_butin(Wagon w) {
        if(poche != null){
            Butin dropped_loot = poche;
            w.loot_int.add(dropped_loot);
            poche = null;
        }
    }

    @Override
    public void est_vise(Wagon wagon){
       drop_butin(wagon);
       wagon.interieur.remove(this); //passager est cliniquement mort
    }
}
