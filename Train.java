public class Train {
    public static final int NB_WAGON = 4;
    public static final int NB_MUNITIONS = 6;
    public static final int NB_JOEURS = 4;
    public static final double Nevrosite_MARSHALL = 0.3;
}




abstract class Wagon {
    private int position;
    private Personne[] interieur;
    private Bandit[] toit;
}



class Cabine extends Wagon{

}


class Locomotive extends Wagon{
    private boolean magot_dispo;
}