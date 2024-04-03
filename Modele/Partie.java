package Modele;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
public class Partie {

    public static int NB_WAGON = 4;
    public static final int NB_MUNITIONS = 6;
    public static final double DEFAULT_PRECISION = 0.9;
    public static final int DEFAULT_HP = 6;
    public static int NB_JOUEURS = 4;
    public static int NB_MANCHES = 5;   //Revoir le Caractere public de certaines
    public static final int NB_BANDITS_JOUEUR = 1;
    public static final double NEVROSITE_MARSHALL = 0.3;
    public static final int NB_PASSAGER_PAR_WAGON_MAX = 4;
    public static final double PROBA_PERTE_LOOT_TOIT = 0.05;

    private Train train;

    private int numeroManche;

    private Action[][] matrice_action;
    private Joueur[] joueurs;
    private boolean mode_extra;




    public static void main(String[] args) {
        System.out.println("\n\n");
        Partie partie = new Partie();
        partie.initialisation_partie();
        System.out.println(partie.train);
        partie.run(NB_MANCHES);
    }

    //    public static void main(String[] args) {
//        Train train = new Train();
//        System.out.println(train);
//        System.out.println(train.get_Wagon()[0]);
//        Deplacement dep = new Deplacement((Marchall)train.get_Wagon()[0].interieur.get(0),train,Direction.ARRIERE);
//        dep.executer();
//        System.out.println(train.get_Wagon()[0]);
//        System.out.println(train.get_Wagon()[1]);
//        Bandit bandit= new Bandit("Natasha");
//        Bandit bandit2 = new Bandit("Paul");
//        train.get_Wagon()[bandit.position].interieur.add(bandit);
//        train.get_Wagon()[bandit2.position].interieur.add(bandit2);
//        System.out.println(train.get_Wagon()[bandit.position]);
//        System.out.println(train.get_Wagon()[bandit2.position]);
//        dep = new Deplacement(bandit,train,Direction.AVANT);
//        dep.executer();
//        System.out.println(train.get_Wagon()[bandit.position]);
//        System.out.println(train.get_Wagon()[bandit2.position]);
//        dep = new Deplacement(bandit,train,Direction.HAUT);
//        dep.executer();
//        dep = new Deplacement(bandit,train,Direction.BAS);
//        dep.executer();
//        System.out.println(train.get_Wagon()[bandit.position]);
//        Tir tir = new Tir(bandit2,train,Direction.AVANT);
//        tir.executer();
//        dep = new Deplacement(bandit,train,Direction.HAUT);
//        dep.executer();
//        System.out.println(train.get_Wagon()[bandit.position]);
//        System.out.println(bandit.get_hitPoints());
//    }

    public void initialisation_partie(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n");
        System.out.println("Hello gamer! would you like to play the special mode or not?");
        System.out.println("Answer by 1 (for yes) or 0 (for no): ");
        mode_extra = (scanner.nextInt() != 0);
        scanner.nextLine();
        System.out.print("Enter the number of players (default is 4): ");
        int numOfPlayers = scanner.nextInt();
        assert numOfPlayers >0;
        NB_JOUEURS = numOfPlayers;
        NB_WAGON = numOfPlayers;
        this.joueurs = new Joueur[numOfPlayers];
        System.out.print("Enter the number of turns (default is 5 , min is 3): ");
        int tours = scanner.nextInt();
        assert tours >= 3;
        NB_MANCHES = tours;
        this.matrice_action = new Action[numOfPlayers*NB_BANDITS_JOUEUR][DEFAULT_HP];
        train = new Train();
        scanner.nextLine(); // Consume newline character
        if (!this.mode_extra){
            for (int i = 0; i < numOfPlayers; i++) {
                System.out.print("Enter the name of player " + (i + 1) + ": ");
                String name = scanner.nextLine();
                List<Bandit> pions = new ArrayList<Bandit>();
                for (int j = 0; j < NB_BANDITS_JOUEUR; j++) {
                    Bandit bandit = new Bandit(name,Partie.NB_WAGON-1-i%2);
                    pions.add(bandit);
                    System.out.print("Welcome aboard: " + name +"\n");
                    train.get_Wagon()[bandit.position].toit.add(bandit);
                }
                joueurs[i] = new Joueur(train,pions);
            }
        }
        else{ //special mode to be implemented
            return ;
        }
    }

    private void run(int nb_manches) {
        for (int k = 0; k < nb_manches; k++) {
            numeroManche = k;
            System.out.println(train);
            //Planification
            for (Joueur j : joueurs){
                System.out.println("It's player's " + j.getId() + " Turn: \n");
                j.joue_manche(matrice_action);
            }

            //Execution

            for (int i = 0; i < DEFAULT_HP; i++) {
                for (int j = 0; j < NB_JOUEURS; j++) {
                    matrice_action[j][i].executer();
                }
            }

            System.out.println(joueur_en_tete()+" in the lead.\n");
        }

    }

    public List<Joueur> joueur_en_tete(){
        int max = joueurs[0].compte_argent();
        List<Joueur> premiers = new LinkedList<Joueur>();
        for (Joueur j : joueurs){
            int v = j.compte_argent();
            if(v > max){
                premiers = new LinkedList<Joueur>();
                premiers.add(j);
                max = v;
            }
            else if(v == max){
                premiers.add(j);
            }
        }
        return premiers;
    }






    public int getNumeroTour() {
        return this.numeroManche;
    }
    public Train getTrain(){return this.train;}

}


