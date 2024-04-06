package Modele;

public enum Direction{
    AVANT("Avant" , -1),
    ARRIERE("Arrière" , 1),
    HAUT("Haut" , 2),
    BAS("Bas",-2),
    ICI("Ici",0);

    private final String nom;
    private final int dir;

    private Direction(String name,int d){
        nom = name;
        dir =d;
    }


    public int dir(){return dir;};
    @Override
    public String toString(){return nom;}

}
