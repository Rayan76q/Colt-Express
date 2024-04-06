package Modele;

import java.util.Random;
public enum Butin {

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


