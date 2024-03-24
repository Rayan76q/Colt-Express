
import static org.junit.Assert.*;
import org.junit.Test;


public class PersonneTest {


    @Test

    public void createidwork(){
        Bandit Rayan = new Bandit("Rayan");

        assertEquals(0 , Rayan.get_id());

        Bandit Antoine = new Bandit("Antoine");

        assertEquals(1 , Antoine.get_id());

        Bandit Samy = new Bandit("Samy");

        assertEquals(2 , Samy.get_id());
    }


    @Test

    public void ajoute_butin_test(){
        Bandit Rayan = new Bandit("Rayan");

        Butin b = Butin.BIJOUX ;

        Rayan.ajoute_butin(b);

        assertEquals(true , Rayan.getPoches().contains(b));
    }
}
