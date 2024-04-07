package Modele;

import java.util.Random;
public enum Butin {

    BOURSE("Bourse" , (new Random()).nextInt(500),"../Vue/Images/bourse.png" ) ,
    BIJOUX("Bijoux",500,
            (new String[] {"../Vue/Images/bijoux1.png","../Vue/Images/bijoux2.png", "../Vue/Images/bijoux3.png"})[(new Random()).nextInt(3)]),
    MAGOT("Magot",1000,"../Vue/Images/magotLoot.png");

    private final String nom;
    private final int valeur;
    private String sprite ;


    private Butin(String name , int value,String sp){
        nom = name;
        valeur = value;
        sprite = sp;
    }

    public int valeur(){return valeur;};
    @Override
    public String toString(){return nom;}

    public String getSprite() {
        return sprite;
    }
}


