package Modele;


public class Locomotive extends Wagon{
    private final Butin mag = Butin.MAGOT;  
    private boolean dispo = true;

    @Override
    public String toString() {
        if (dispo) {
            return "C'est la locomotive.\n"+ "elle contient le magot!\n"+ super.toString();
        }
        return "C'est la locomotive.\n"+ "elle ne contient pas le magot!\n"+ super.toString();
    }


    public boolean magot_dispo(){return dispo;}

    public Locomotive(){
        super();
        assert size==0;
        position = 0;
        size++;
        interieur.add(new Marchall());
    }

    public void magot_vole(Bandit b)
    {
        if(magot_dispo()) {
            b.ajoute_butin(mag);
            dispo = false;
        }
    }

    public Butin getMag() {
        return mag;
    }
}
