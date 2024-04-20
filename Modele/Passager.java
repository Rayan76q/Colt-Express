package Modele;

import java.util.Random;



public class Passager extends Personne implements Hitable{

    //Sprites possible pour un passager
    private static final String[] sprites = {"../Vue/Images/passagerBarbu.png" , "../Vue/Images/passagerFemme.png" , "../Vue/Images/passagerJournal.png",
                            "../Vue/Images/passagerRiche.png" , "../Vue/Images/passagerVieux.png"};


    private Butin poche;

    public Passager(int p){
        super("passager"+getCurrent_id_passager--);
        Random r = new Random();
        id = getCurrent_id_passager+1;
        position = p;

        poche =  Butin.values()[r.nextInt(2)];
        sprite = sprites[r.nextInt(sprites.length)];
    }


    public void setButin(Butin butin){
        poche = butin;
    }

    public Butin getPoche(){
        return poche;
    }


    /**Cede le butin du passager au bandit b <br>
     * @param b un bandit qui est en train de braquer le passager
     */
    public void cede(Bandit b){
        if(poche != null) {
            b.ajoute_butin(poche);
            poche = null;
        }
    }

    /**
     * @see Hitable
     */
    @Override
    public void drop_butin(Wagon w) {
        if(poche != null){
            Butin dropped_loot = poche;
            w.loot_int.add(dropped_loot);
            poche = null;
        }
    }

    /**@see Hitable
     */
    @Override
    public void est_vise(Wagon wagon){
        drop_butin(wagon);
        targeted = false;
        wagon.interieur.remove(this); //passager est cliniquement mort
    }
}