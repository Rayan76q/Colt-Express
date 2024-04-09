package Modele;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Bandit extends Personne implements Movable, Hitable{

    private boolean toit;
    private int ammo;
    private final double precision;

    private int hitPoints;
    private List<Butin> poches = new LinkedList<Butin>();


    //Sprites
    private static final String[] sprites = {"../Vue/Images/banditHomme.png","../Vue/Images/banditFemme.png"};


    public Bandit(String name,int pos){
        super(name);
        id = current_id_bandit++ ;
        position = pos;
        ammo = Partie.NB_MUNITIONS;
        toit = true;
        hitPoints = Partie.DEFAULT_HP;
        precision = Partie.DEFAULT_PRECISION;

        Random r = new Random();
        sprite = sprites[r.nextInt(sprites.length)];
    }

    public int get_hitPoints(){
        return this.hitPoints;
    }
    public int get_ammo(){return this.ammo;}

    public List<Butin> getPoches() { return this.poches;}

    public void setToit(boolean toit) {
        this.toit = toit;
    }

    public void ajoute_butin(Butin b){
        poches.add(b);
    }
    public void drop_butin(Wagon w){
        assert this.position == w.position;
        if (!this.poches.isEmpty()){
            Random random = new Random();
            int randomIndex = random.nextInt(this.poches.size());
            Butin dropped_loot = this.poches.get(randomIndex);
            this.poches.remove(randomIndex);
            if(toit)
                w.loot_toit.add(dropped_loot);
            else
                w.loot_int.add(dropped_loot);
        }
    }

    @Override
    public void est_vise(Wagon wagon){
        if(this.hitPoints>2)
            this.hitPoints -= 1;
        drop_butin(wagon);
    }


    public String braque(Train T) {
        Random r = new Random();
        Wagon wagon_actuelle = T.get_Wagon()[position];
        int index_butin;
        if (toit) {
            if(!wagon_actuelle.loot_toit.isEmpty()) {
                index_butin = r.nextInt(wagon_actuelle.loot_toit.size());
                poches.add(wagon_actuelle.loot_toit.get(index_butin));
                wagon_actuelle.loot_toit.remove(index_butin);
                return toString() + " récupère un butin au sol.";
            }
            else{
                return toString() + " n'a personne a braqué perché sur le toit, dommage."; //toit vide
            }
        }
        else {
            if (position == 0 && ((Locomotive) T.get_Wagon()[0]).magot_dispo()) {
                //vole le magot
                ((Locomotive) T.get_Wagon()[0]).magot_vole(this);
                return toString() + " a choppé le magot.";
            } else if ((position==0 ||  r.nextBoolean()) && !wagon_actuelle.loot_int.isEmpty()) {  //prend un loot au sol
                index_butin = r.nextInt(wagon_actuelle.loot_int.size());
                poches.add(wagon_actuelle.loot_int.get(index_butin));
                wagon_actuelle.loot_int.remove(index_butin);
                return toString() + " récupère un butin au sol.";
            } else if(!wagon_actuelle.liste_passagers().isEmpty()){
                //Braque un passager
                index_butin = r.nextInt(wagon_actuelle.liste_passagers().size());
                wagon_actuelle.liste_passagers().get(index_butin).cede(this);
                return toString() + " braque un passager.";
            }
            else{
                return toString() + " n'a personne a braquer.";
            }
        }


    }



    @Override
    public List<Direction> mouvementsPossibles(Train t) {
       return t.mouvementPossibles(true,position,toit);
    }

    @Override
    public String move(Train T , Direction d){
        if(!this.mouvementsPossibles(T).contains(d)){
            System.out.println("Mouvement Invalide");
            return toString() + " a essayé de sauter du train , mauvaise idée.";
        }
        Wagon[] wagons = T.get_Wagon();
        wagons[position].enleve_personne(this);
        if(d.dir()==-Partie.NB_WAGON) toit = true;
        else if (d.dir()==Partie.NB_WAGON) toit = false;
        else {
            position += d.dir();
        }
        wagons[position].ajoute_personne(this,toit);
        return toString() + " se déplace.";
    }

    public String tir(Train train, Direction dir) {
        Random random = new Random();
        if(this.ammo>0) {
            assert mouvementsPossibles(train).contains(dir);
            int d = dir.dir();
            if (d == -Partie.NB_WAGON) {  // tir vers le toit d'un wagon du bas
                Wagon current_wagg = train.get_Wagon()[this.position];
                List<Bandit> list = current_wagg.toit;
                if(random.nextDouble()<=0.9) {
                    int randomIndex = random.nextInt(list.size());
                    list.get(randomIndex).est_vise(current_wagg);
                }
                return toString() + " tire vers le toit du wagon.";
            }
            else if(d == Partie.NB_WAGON || !toit){ //tir vers un wagon : tir venant du toit ou tir venant d'un coté
                int numWagon = this.position + (d<0? d :  d%Partie.NB_WAGON);
                Wagon current_wagg = train.get_Wagon()[numWagon];
                List<Passager> passagers_cibles = current_wagg.liste_passagers();
                List<Bandit> bandits_cibles = current_wagg.liste_bandits_int();
                if (random.nextDouble() > 0.9) {
                    int randomIndex = random.nextInt(passagers_cibles.size());
                    passagers_cibles.get(randomIndex).est_vise(current_wagg);
                } else {
                    int randomIndex = random.nextInt(bandits_cibles.size());
                    bandits_cibles.get(randomIndex).est_vise(current_wagg);
                }
                return toString() + " tire sur le wagon N°" + numWagon;

            }
            else { //tir d'un toit vers un autre
                Wagon current_wagg = train.get_Wagon()[this.position + d];
                this.ammo--;
                List<Bandit> list = current_wagg.toit;
                while (list.isEmpty() && !(current_wagg.position == 0 || current_wagg.position == Partie.NB_WAGON - 1)) {
                    current_wagg = train.get_Wagon()[current_wagg.position + d];
                    list = current_wagg.toit;
                }
                if (!list.isEmpty()) {
                    int size_bound = list.size();
                    int randomIndex = random.nextInt(size_bound);
                    Bandit bandit = list.get(randomIndex);
                    bandit.est_vise(current_wagg);
                }
                return toString() + " tire vers l'" + dir;
            }
        }
        return "*Click*";
    }

    public String frappe(Train train) {
        Random rand = new Random();
        Wagon w = train.get_Wagon()[position];
        Bandit visé = null;
        if(toit){
            w.toit.remove(this);
            int rand_index = rand.nextInt(w.toit.size());
            w.toit.get(rand_index).est_vise(w);
            w.toit.add(this);
        }
        else {
            List<Bandit> bandits_cibles = w.liste_bandits_int();
            bandits_cibles.remove(this);
            int rand_index = rand.nextInt(bandits_cibles.size());
             visé= bandits_cibles.get(rand_index);
            visé.est_vise(w);
        }
        if(visé == null) {
            return toString() + " frappe " + visé;
        }
        else{
            return toString() + " brasse de l'air";
        }
    }

    public String fuit_marshall(Wagon w) {

        if(this.hitPoints>2) {
            this.hitPoints--;
        }
        this.drop_butin(w);
        this.toit = true;
        w.getInterieur().remove(this);
        w.getToit().add(this);

        return "*PAN*, "+toString() + " a pris la fuite en se prenant une balle du Marshall.";
    }


    public int compte_butins(){
        int somme = 0;
        for (Butin b : poches){
            somme += b.valeur();
        }
        return somme;
    }

    public boolean getToit(){return toit;}
}