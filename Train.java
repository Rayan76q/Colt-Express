public class Train {
    public static final int NB_WAGON = 4;
    public static final int NB_MUNITIONS = 6;
    public static final int NB_JOEURS = 4;
    public static final double Nevrosite_MARSHALL = 0.3;

    @Override
    public String toString(){
        return "this train has: \n - "+NB_WAGON +" wagons.\n - "+
                NB_JOEURS + " players.\n - "+NB_MUNITIONS +" munitions.\n - "
                +"One marshall that is "+Nevrosite_MARSHALL +" over 1 crazy.";
    }
}




abstract class Wagon {
    private int position;
    private Personne[] interieur;
    private Bandit[] toit;
    public String toString(){
        return "This is Wagon number " + position +".\nIt has :\n  - "+ interieur.length +
                " people inside it.\n - "+ toit.length +" bandits on the roof.\n";
    }
}



class Cabine extends Wagon{

}


class Locomotive extends Wagon{
    private boolean magot_dispo;

    @Override
    public String toString() {
        if (magot_dispo){
            return "this is the locomotive.\n"+super.toString()+"and it has its magot!\n";
        }
        return "this is the locomotive.\n"+super.toString()+"and its magot got stolen!\n";
    }
}