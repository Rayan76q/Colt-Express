package Modele;


import java.util.*;

public class Partie extends Observable {
    private static final int DELAY = 1000;

    public static int NB_WAGON = 4;
    public static final int NB_MUNITIONS = 6;
    public static final double DEFAULT_PRECISION = 0.9;
    public static final int DEFAULT_HP = 3;
    public static int NB_JOUEURS = 4;
    public static int NB_MANCHES = 5;   //Revoir le Caractere public de certaines
    public static final int NB_BANDITS_JOUEUR = 1;
    public static final double NEVROSITE_MARSHALL = 0.3;
    public static final int NB_PASSAGER_PAR_WAGON_MAX = 4;
    public static final double PROBA_PERTE_LOOT_TOIT = 0.05;
    public static List<Class<? extends Action>> Actions = new ArrayList<>();

    private Train train;

    private int numeroManche;

    private Action[][] matrice_action;
    private Joueur[] joueurs;
    private int joueurAct = 0;
    private int tempo = 0;
    private int actionChoisie = -1;
    private Direction directionChoisie ;

    private boolean mode_extra;



    //Main avec afffichage textuelle
    public static void main(String[] args) {
        System.out.println("\n\n");
        Partie partie = new Partie();
        System.out.println(partie.train);
        partie.run(NB_MANCHES);
    }


    public Partie() {
        Actions.add(Deplacement.class);
        Actions.add(Tir.class);
        Actions.add(Braquage.class);
        Actions.add(Frappe.class);

        initialisation_partie();
    }

    public Joueur[] getJoueurs() {
        return joueurs;
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
        NB_WAGON = numOfPlayers+1;
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
            evenementsPassifs(true);
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

    public int getJoueurAct() {
        return joueurAct;
    }

    public int getActionChoisie() {
        return actionChoisie;
    }

    public void setActionChoisie(int actionChoisie) {
        assert  actionChoisie>=0 && actionChoisie<4 ;


        this.actionChoisie = actionChoisie;
    }


    public Action[][] getMatrice_action(){return matrice_action;}


    public Action creeActionFinale(){
        if(actionChoisie == 2 || actionChoisie == 3 ){
            try {
                return Actions.get(actionChoisie).getConstructor(Bandit.class , Train.class).newInstance(joueurs[joueurAct].getPionAct() , train);
            } catch (Exception e) {
                return null;
            }
        }
        else if(directionChoisie != null){
            if (actionChoisie == 0) {
                try {
                    return Actions.get(actionChoisie).getConstructor(Movable.class, Train.class, Direction.class).newInstance(joueurs[joueurAct].getPionAct(), train, directionChoisie);
                } catch (Exception e) {
                    return null;
                }
            } else {
                try {
                    return Actions.get(actionChoisie).getConstructor(Bandit.class, Train.class, Direction.class).newInstance(joueurs[joueurAct].getPionAct(), train, directionChoisie);
                } catch (Exception e) {
                    return null;
                }
            }
        }
        return null;
    }

    public void confirmeAction() {

        Action actionFinale = creeActionFinale();
        if(actionFinale != null) {
            Bandit pionActuelle = joueurs[joueurAct].getPionAct();
            matrice_action[pionActuelle.get_id()][tempo] = actionFinale;
            tempo++;
            if (tempo > pionActuelle.get_hitPoints() - 1) {
                for (int i = tempo; i < matrice_action[pionActuelle.get_id()].length; i++) {
                    matrice_action[pionActuelle.get_id()][i] = null;  //blessure
                }
                tempo = 0;
                //le joueur a entrer toutes les actions du pion
                if (!joueurs[joueurAct].getNextPion()) {
                    getNextJoueur();
                    if (joueurAct == 0) { //la phase de planification est terminé , on execute
                        executerMatrice();
                    }
                }
            }

            //passage à l'action suivante
            actionChoisie = -1;
            directionChoisie = null;
            notifyObservers();
        }

    }

    public void sleep(){
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void executerMatrice(){
        notifyObservers("Au tour de "+getJoueurs()[0].getPions().get(0)+" ( J1 )");
        Thread actionThread = new Thread(() -> {
            for (int j = 0; j < matrice_action[0].length; j++) {
                for (int i = 0; i < matrice_action.length; i++) {
                    joueurAct = i/NB_BANDITS_JOUEUR;//switch les joueurs pour VueCommandes
                    Bandit b = joueurs[joueurAct].getPionAct();
                    notifyObservers("Au tour de "+b+" ( J"+(joueurAct+1)+" )");
                    sleep();
                    if (matrice_action[i][j] != null) {
                        String message = matrice_action[i][j].executer();
                        notifyObservers(message);
                        matrice_action[i][j] = null;
                        sleep();
                        notifyObservers(evenementsPassifs(false));
                        sleep();
                    }
                }
            }
            String r = "";
            for(Joueur j:joueur_en_tete()){
                r += j.toString()+" ";
            }
            System.out.println(r+" en tête pour ce tour.\n");
            numeroManche++;
            notifyObservers(evenementsPassifs(true));
            notifyObservers();
            joueurAct =0;
        });

        actionThread.start();

    }

    private String evenementsPassifs(boolean endTurn) {


        if(endTurn) {
            //Gère tous ce qui se passe entre les manches comme les deplacements du marshall
            for (Wagon w : train.get_Wagon()) {
                w.perte_loot_toit();
            }
            //Mouvement du marchall
            Random r = new Random();
            Marchall m = train.getMarchall();
            List<Direction> dirs = m.mouvementsPossibles(train);
            String message = m.move(train, dirs.get(r.nextInt(dirs.size())));
            //Fuite
            Wagon wagonMarshallAct = train.get_Wagon()[m.position];
            for (Bandit b : wagonMarshallAct.liste_bandits_int()) {
                b.fuit_marshall(wagonMarshallAct);
            }
            return message;
        }
        else{
            Wagon wagonMarshall = train.get_Wagon()[train.getMarchall().getPosition()];
            for(Bandit b : wagonMarshall.liste_bandits_int()){
                return b.fuit_marshall(wagonMarshall);
            }
            return " ";
        }


    }


    public void getNextJoueur(){
        joueurAct  = (joueurAct+1)%NB_JOUEURS;
    }

    public void annuleAction() {
        if(tempo>0){ //revient au tempo précédent
            tempo--;

        }
        else{
            if(joueurs[joueurAct].getPrevPion()){  //revient au pions précédent
                tempo = matrice_action[joueurs[joueurAct].getPionAct().get_id()].length-1;
            }
        }
        matrice_action[joueurs[joueurAct].getPionAct().get_id()][tempo] = null;
        actionChoisie=-1;
        directionChoisie = null;
        notifyObservers();
    }

    public void setDirectionChoisie(Direction directionChoisie) {
        this.directionChoisie = directionChoisie;
    }

    public int getTempo() {return tempo;}

    public List<Direction> mouvementsPossiblesPostPlan(){
        Bandit b = joueurs[joueurAct].getPionAct();
        int positionDep = b.getPosition() +  (b.getToit()? 0 :1)*NB_WAGON;
        for (int i = 0; i <tempo; i++) {
            if(matrice_action[b.get_id()][i] instanceof Deplacement){
                positionDep += ((Deplacement)matrice_action[b.get_id()][i]).getDir().dir();
            }
        }
        return train.mouvementPossibles(true,positionDep%NB_WAGON,positionDep < NB_WAGON);
    }

}



