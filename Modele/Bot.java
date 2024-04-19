package Modele;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Bot extends Joueur{

    public Bot(Train t, List<Bandit> b) {
        super(t, b);
    }

    @Override
    public void joue_manche(Action[][] mat_manche){
        for (Bandit b : pions){ //si il joue plusieurs pions en meme temps (par équipe)
            List<Action> actions = ((Bandit_Bot)b).analyse_game();
            int k = 0;
            for (Action j : actions) {
                System.out.println("Action N°" + (k+1) + "\n");
                System.out.println("braque / bouge / Tir / frappe\n");
                //Version switch
                System.out.println(j);
                mat_manche[b.get_id()][k] = j;
            }
            for (int i = b.get_hitPoints(); i < Partie.DEFAULT_HP; i++) {
                mat_manche[b.get_id()][i] = null; //blessures
            }
        }
    }
}

abstract class Bandit_Bot extends Bandit{

    protected Partie partie;
    public Bandit_Bot(String name, int pos, Partie partie) {
        super(name, pos);
        this.partie = partie;
    }

    abstract List<Action> analyse_game();
    public int dist(Bandit src, int tgt,int toit, Train train){
       //pour verifier si on peut arriver avant d'utiliser toutes nos actions
        int dist =((src.getToit()?1:0)-toit);
        dist = (dist>0 ?dist :-dist);
        int pos = src.position - tgt;
        dist += (pos>0? pos:-pos);
        return dist;
    }
}
class Random_Bot extends Bandit_Bot{

    public Direction choisie_dir(Bandit b){
        System.out.println("Choose between: " +b.mouvementsPossibles(partie.getTrain()) +"\n");
        System.out.println("0->forward / 1->backward / 2->up / 3->down / 4-> here\n");
        Random random = new Random();
        int choice;
        do {
            choice = random.nextInt(4);
        }while(!b.mouvementsPossibles(partie.getTrain()).contains(Direction.values()[choice]));
        System.out.println(choice);
        return Direction.values()[choice];
    }


    public Random_Bot(int pos, Partie partie) {
        super("Randy", pos, partie);
    }
    @Override
    List<Action> analyse_game() {
        LinkedList<Action> acts = new LinkedList<>();
        Train train = partie.getTrain();
        for (int i = 0; i < this.get_hitPoints(); i++) {
            Random random = new Random();
            int choice = random.nextInt(4);
            List<Direction> dirs = this.mouvementsPossibles(train);

            if(choice == 0){ //Braquage
                acts.add( new Braquage(this,train));
            }
            else if(choice == 1){ //Mouvement
                acts.add( new Deplacement(this,train , choisie_dir(this)));
            }
            else if(choice == 2){ // tir
                acts.add( new Tir(this,train , choisie_dir(this)) );
            }
            else{//Frappe
                acts.add( new Frappe(this, train));
            }
        }
        return acts;
    }
}

class Blood_thirsty_Bot extends Bandit_Bot{

    public Blood_thirsty_Bot(int pos, Partie partie) {
        super("Lucia", pos, partie);
    }

    @Override
    List<Action> analyse_game() {
        return null;
    }
}

class Goblin_Bot extends Bandit_Bot{
    public Goblin_Bot(int pos, Partie partie) {
        super("Goblin", pos, partie);
    }

    @Override
    List<Action> analyse_game() {
        return null;
    }
}