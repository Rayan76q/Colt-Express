public class Personne {
    private int id;
    private int position;
    private String name;
}


class Bandit extends Personne{
    private boolean toit;
    private int ammo;
    private Butin[] poches;

}

class Marchal extends Personne{
    float nevrosite;
}