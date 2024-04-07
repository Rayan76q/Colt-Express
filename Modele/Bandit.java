package Modele;


import java.util.ArrayList;
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
        id = current_id_bandit++ ;
        nom = name;
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


    public void braque(Train T) {
        Random r = new Random();
        Wagon wagon_actuelle = T.get_Wagon()[position];
        int index_butin;
        if (toit) {
            if(!wagon_actuelle.loot_toit.isEmpty()) {
                index_butin = r.nextInt(wagon_actuelle.loot_toit.size());
                poches.add(wagon_actuelle.loot_toit.get(index_butin));
                wagon_actuelle.loot_toit.remove(index_butin);
            }
            else{
                return ; //toit vide
            }
        }
        else {
            if (position == 0 && ((Locomotive) T.get_Wagon()[0]).magot_dispo()) {
                //vole le magot
                ((Locomotive) T.get_Wagon()[0]).magot_vole(this);
            } else if ((position==0 ||  r.nextBoolean()) && !wagon_actuelle.loot_int.isEmpty()) {  //prend un loot au sol
                index_butin = r.nextInt(wagon_actuelle.loot_int.size());
                poches.add(wagon_actuelle.loot_int.get(index_butin));
                wagon_actuelle.loot_int.remove(index_butin);
            } else if(!wagon_actuelle.getInterieur().isEmpty()){
                //Braque un passager
                index_butin = r.nextInt(wagon_actuelle.liste_passagers().size());
                wagon_actuelle.liste_passagers().get(index_butin).cede(this);
            }
            else{
                return ; //wagon vide
            }
        }

    }



    @Override
    public List<Direction> mouvements_possibles() {
        List<Direction> res = new ArrayList<Direction>();
        if(position==0)
            res.add(Direction.ARRIERE);
        else if(position== Partie.NB_WAGON-1)
            res.add(Direction.AVANT);
        else {
            res.add(Direction.AVANT);
            res.add(Direction.ARRIERE);
        }

        if(toit)
            res.add(Direction.BAS);
        else
            res.add(Direction.HAUT);
        return res;

    }

    @Override
    public void move(Train T , Direction d){
        Wagon[] wagons = T.get_Wagon();
        wagons[position].enleve_personne(this);
        if(d.dir()==2) toit = true;
        else if (d.dir()==-2) toit = false;
        else {
            if(position+d.dir()<0 || position+d.dir()== Partie.NB_WAGON){
                return ;
            }
            position += d.dir();
        }
        wagons[position].ajoute_personne(this,toit);
    }

    public void tir(Train train, Direction dir) {
        Random random = new Random();
        if(this.ammo>0) { //Peut donner la possiblité de bluffer un tir , faire sembler d'avoir des balles
            assert mouvements_possibles().contains(dir);
            int d = dir.dir();
            if (d == 2) {  // tir vers le toit d'un wagon du bas
                Wagon current_wagg = train.get_Wagon()[this.position];
                List<Bandit> list = current_wagg.toit;
                if(random.nextDouble()<=0.9) {
                    int randomIndex = random.nextInt(list.size());
                    list.get(randomIndex).est_vise(current_wagg);
                }
            }
            else if(d == -2 || !toit){ //tir vers un wagon : tir venant du toit ou tir venant d'un coté
                Wagon current_wagg = train.get_Wagon()[this.position + (d < 0 ? d : (d+2)%2)];
                List<Passager> passagers_cibles = current_wagg.liste_passagers();
                List<Bandit> bandits_cibles = current_wagg.liste_bandits_int();
                if (random.nextDouble() > 0.9) {
                    int randomIndex = random.nextInt(passagers_cibles.size());
                    passagers_cibles.get(randomIndex).est_vise(current_wagg);
                } else {
                    int randomIndex = random.nextInt(bandits_cibles.size());
                    bandits_cibles.get(randomIndex).est_vise(current_wagg);
                }
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
            }
        }
    }

    public void frappe(Train train) {
        Random rand = new Random();
        Wagon w = train.get_Wagon()[position];
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
            bandits_cibles.get(rand_index).est_vise(w);
        }
    }

    public void fuit_marshall(Wagon w) {
        if(this.hitPoints>2) {
            this.hitPoints--;
        }
        this.drop_butin(w);
        this.toit = true;
        w.getInterieur().remove(this);
        w.getToit().add(this);
    }


    public int compte_butins(){
        int somme = 0;
        for (Butin b : poches){
            somme += b.valeur();
        }
        return somme;
    }
}