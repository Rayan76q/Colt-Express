package Modele;

import org.junit.Test;
import static org.junit.Assert.*;

public class PassagerTest {

    @Test
    public void testSetAndGetPoche() {
        Passager passager = new Passager(1);
        Butin butin = Butin.BIJOUX;

        passager.setButin(butin);
        assertEquals(butin, passager.getPoche());
    }

    @Test
    public void testCede() {
        Passager passager = new Passager(1);
        Bandit bandit = new Bandit("Bandit", 1);
        Butin butin = Butin.BIJOUX;

        passager.setButin(butin);
        passager.cede(bandit);

        assertNull(passager.getPoche());
        assertTrue(bandit.getPoches().contains(butin));
    }


}
