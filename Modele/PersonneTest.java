package Modele;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class PersonneTest  {


    @Test

    public void createidwork(){
        Bandit Rayan = new Bandit("Rayan",0);

        assertEquals(0 , Rayan.get_id());

        Bandit Antoine = new Bandit("Antoine",0);

        assertEquals(1 , Antoine.get_id());

        Bandit Samy = new Bandit("Samy",0);

        assertEquals(2 , Samy.get_id());
    }


    @Test

    public void ajoute_butin_test(){
        Bandit Rayan = new Bandit("Rayan",0);

        Butin b = Butin.BIJOUX ;

        Rayan.ajoute_butin(b);

        assertEquals(true , Rayan.getPoches().contains(b));
    }
}
