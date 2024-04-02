package Modele;

import java.util.Random;
enum Butin {

    BOURSE("Bourse" , (new Random()).nextInt(500) ) ,
    BIJOUX("Bijoux",500),
    MAGOT("Magot",1000);

    private final String nom;
    private final int valeur;


    private Butin(String name , int value){
        nom = name;
        valeur = value;
    }

    public int valeur(){return valeur;};
    @Override
    public String toString(){return nom;}

}


enum Direction{
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
