import java.util.Scanner;
import java.util.ArrayList;

public class Partie {
    Train train;

    private int numeroManche;

    public int getNumeroTour() {
        return this.numeroManche;
    }

    boolean mode_extra;






    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n");
        System.out.println("Hello gamer! would you like to play the special mode or not?");
        System.out.println("Answer by 1 (for yes) or 0 (for no): ");
        Partie partie = new Partie();
        partie.mode_extra = (scanner.nextInt() != 0);
        scanner.nextLine();
        partie.initialisation_partie();
        System.out.println(partie.train);
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
        System.out.print("Enter the number of players: ");
        int numOfPersons = scanner.nextInt();
        System.out.println(numOfPersons);
        train = new Train(numOfPersons);
        scanner.nextLine(); // Consume newline character
        if (!this.mode_extra){
            for (int i = 0; i < numOfPersons; i++) {
                System.out.print("Enter name of player " + (i + 1) + ": ");
                String name = scanner.nextLine();
                Bandit bandit = new Bandit(name);
                train.get_Wagon()[bandit.position].interieur.add(bandit);
            }
        }
        else{
            return ;
        }
    }


}



