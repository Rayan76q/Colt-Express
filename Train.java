public class Train {
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