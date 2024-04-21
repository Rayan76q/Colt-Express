package Modele;


import java.util.List;
import java.util.Scanner;

public class Joueur implements Comparable<Joueur> {
    protected static int current_id = 0;

    protected Train train;
    protected List<Bandit> pions;
    protected Bandit pionAct;
    protected int id = current_id;


    public Joueur(Train t , List<Bandit> b){
        train = t;
        pions = b;
        pionAct = pions.get(0);
        current_id++;
    }

    //Pour tester avec affichage textuelle
    public Direction choisie_dir(Bandit b){
        System.out.println("Choisit entre: " +b.mouvementsPossibles(train) +"\n");
        System.out.println("0->devant / 1->derriere/ 2->en haut / 3->en bas / 4-> ici \n");
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            choice = scanner.nextInt();
            scanner.nextLine();
        }while(!b.mouvementsPossibles(train).contains(Direction.values()[choice]));
        return Direction.values()[choice];
    }

    public void joue_manche(Action[][] mat_manche){
        for (Bandit b : pions){ //si il joue plusieurs pions en meme temps (par équipe)
            for (int i = 0; i < b.get_hitPoints(); i++) {
                System.out.println("Action N°" + (i+1) + "\n");
                System.out.println("0->braque / 1->bouge / 2->Tir / 3->frappe\n");
                //Version switch
                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();
                scanner.nextLine();


                if(choice == 0){ //Braquage
                    mat_manche[b.get_id()][i] = new Braquage(b,train);
                }
                else if(choice == 1){ //Mouvement
                    mat_manche[b.get_id()][i] = new Deplacement(b,train , choisie_dir(b));
                }
                else if(choice == 2){ // tir
                    mat_manche[b.get_id()][i] = new Tir(b,train , choisie_dir(b));
                }
                else{//Frappe
                    mat_manche[b.get_id()][i] = new Frappe(b, train);
                }
            }
            for (int i = b.get_hitPoints(); i < Partie.DEFAULT_HP; i++) {
                mat_manche[b.get_id()][i] = null; //blessures
            }
        }
    }

    public int compte_argent(){
        int somme = 0;
        for (Bandit b : pions){
            somme += b.compte_butins();
        }
        return somme;
    }

    public int getId() {
        return id;
    }

    public List<Bandit> getPions(){return pions;}

    @Override
    public String toString(){
        return "Joueur N°" + (id+1);
    }

    public Bandit getPionAct(){return pionAct;}
    public boolean getNextPion(){
        //Si renvoie 0 alors le joueur a fini de jouer
        int index = getPions().indexOf(pionAct)+1;
        try {
            pionAct = getPions().get(index);
            return true;
        }
        catch (IndexOutOfBoundsException e){
            pionAct = getPions().get(0);
            return false;
        }

    }

    public boolean getPrevPion(){

        int index = getPions().indexOf(pionAct)-1;
        try {
            pionAct = getPions().get(index);
            return true;
        }
        catch (IndexOutOfBoundsException e){
            pionAct = getPions().get(0);
            return false;
        }
    }

    public void setPionAct(Bandit b){
        assert pions.contains(b);
        pionAct = b;
    }

    @Override
    public int compareTo(Joueur other) {
        return Integer.compare(this.compte_argent(), other.compte_argent());
    }
    public static void reintialise(){current_id =0;}
}
