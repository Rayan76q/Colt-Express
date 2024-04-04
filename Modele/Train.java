package Modele;


public class Train extends Observable {

    private Wagon[] WAGON = new Wagon[Partie.NB_WAGON];

    public Train(){
        WAGON[0] = new Locomotive();
        for (int i = 1; i < Partie.NB_WAGON ; i++) {
            WAGON[i] = new Cabine(i);
        }
    }
    public Train(int player_number){
        assert(player_number <=8 && player_number>=2):"On ne peut pas jouer avec ce nombre de joeurs.";
        WAGON[0] = new Locomotive();
        for (int i = 1; i < Partie.NB_WAGON ; i++)  WAGON[i] = new Cabine(i);
    }


    public Wagon[] get_Wagon(){
        return WAGON;
    }

    @Override
    public String toString(){
//        String acc = "this train has: \n - "+NB_WAGON +" wagons.\n - "+
//                NB_JOEURS + " players.\n - "+NB_MUNITIONS +" munitions.\n - "
//                +NB_PASSAGER_PAR_WAGON_MAX+" passagers maximum in one wagon.\n - "
//                +"One marshall that is "+NEVROSITE_MARSHALL +" over 1 nevrosite.\n"+"Now for the wagons:\n";
        String acc = "Les wagons du trains : \n";
        for (int i = 0; i < Partie.NB_WAGON ; i++) {
            acc += "----------- \n";
            acc += WAGON[i].toString();
        }
        acc += " END. \n";
        return acc;

    }

}


