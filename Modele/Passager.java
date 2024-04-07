package Modele;

import java.util.Random;

public class Passager extends Personne implements Hitable{

    private static String[] sprites = {"../Vue/Images/passagerBarbu.jpg" , "../Vue/Images/passagerFemme.jpg" , "../Vue/Images/passagerJournal.jpg",
                            "../Vue/Images/passagerRiche.jpg" , "../Vue/Images/passagerVieux.jpg","../Vue/Images/passgerCouple.jpg"};


    private Butin poche;

    public void setButin(Butin butin){
        poche = butin;
    }

    public Butin getPoche(){
        return poche;
    }

    public Passager(int p){
        Random r = new Random();
        id = getCurrent_id_passager--;
        position = p;
        nom = "passager"+id;
        poche =  Butin.values()[r.nextInt(2)];
        sprite = sprites[r.nextInt(sprites.length)];
    }


    public void cede(Bandit b){
        if(poche != null) {
            b.ajoute_butin(poche);
            poche = null;
        }
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