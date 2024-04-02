import java.util.List;
import java.util.Random ;
import java.util.LinkedList;
import java.util.ArrayList ;

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

    static protected int current_id_bandit = 0 ;
    static protected int getCurrent_id_passager = -2;



    public int get_id(){ return this.id;}

    @Override
    public String toString() {
        return nom;
    }
}


class Bandit extends Personne implements Movable, Hitable{
    private boolean toit;
    private int ammo;
    private double precision;

    private int hitPoints;
    private List<Butin> poches = new LinkedList<Butin>();


    public Bandit(String name,int pos){
        id = current_id_bandit++ ;
        nom = name;
        position = pos;
        ammo = Partie.NB_MUNITIONS;
        toit = true;
        hitPoints = Partie.DEFAULT_HP;
        precision = Partie.DEFAULT_PRECISION;
    }

    public int get_hitPoints(){
        return this.hitPoints;
    }
    public int get_ammo(){return this.ammo;}

    public List<Butin> getPoches() { return this.poches;}



    public void ajoute_butin(Butin b){
        poches.add(b);
    }
    public void drop_butin(Wagon w){
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
        if(this.hitPoints>0)
            this.hitPoints -= 1;
        drop_butin(wagon);
    }


    public void braque(Train T){
        Random r = new Random();
        Wagon wagon_actuelle = T.get_Wagon()[position];
        int index_butin;
        if(toit){
            int nb_butins = wagon_actuelle.loot_toit.size();
            if(nb_butins<1) return;
            index_butin = r.nextInt(wagon_actuelle.loot_toit.size());
            poches.add(wagon_actuelle.loot_toit.get(index_butin));
            wagon_actuelle.loot_toit.remove(index_butin);
        }
        else{
            if(r.nextBoolean()){ //Braque un passager
                index_butin = r.nextInt(wagon_actuelle.liste_passagers().size());
                wagon_actuelle.liste_passagers().get(index_butin).cede(this);
            }
            else{
                index_butin = r.nextInt(wagon_actuelle.loot_int.size());
                poches.add(wagon_actuelle.loot_int.get(index_butin));
                wagon_actuelle.loot_int.remove(index_butin);
            }
        }
    }


    @Override
    public List<Direction> mouvements_possibles() {
        List<Direction> res = new ArrayList<Direction>();
        if(position==0)
            res.add(Direction.ARRIERE);
        else if(position==Partie.NB_WAGON-1)
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
            if(position+d.dir()<0 || position+d.dir()==Partie.NB_WAGON){
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
                while (list.isEmpty() && !(current_wagg.position == 0 || current_wagg.position ==Partie.NB_WAGON - 1)) {
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
            int rand_index = rand.nextInt(w.toit.size());
            w.toit.get(rand_index).est_vise(w);
        }
        else {
            List<Bandit> bandits_cibles = w.liste_bandits_int();
            int rand_index = rand.nextInt(bandits_cibles.size());
            bandits_cibles.get(rand_index).est_vise(w);
        }
    }

    public void fuit_marshall(Wagon w) {
        this.hitPoints--;
        this.drop_butin(w);
        this.toit = true;
    }


    public int compte_butins(){
        int somme = 0;
        for (Butin b : poches){
            somme += b.valeur();
        }
        return somme;
    }
}





class Marchall extends Personne implements Movable{
    double nevrosite;

    public Marchall(){
        id = -1;
        nom = "Marshall";
        position = 0;
        nevrosite = Partie.NEVROSITE_MARSHALL;

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
    public void move(Train T ,Direction d){
        if(position+d.dir()<0 || position+d.dir()==Partie.NB_WAGON){
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

class Passager extends Personne implements Hitable{

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