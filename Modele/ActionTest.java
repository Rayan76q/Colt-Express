package Modele;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ActionTest {
    Train stubTrain;
    Bandit stubBandit;
    Movable stubMovable;
    Direction direction;

    @Before
    public void setup() {

            // Mock Partie settings for test cases
            Partie.NB_WAGON = 5;  // Example setting to ensure there are enough wagons

            // Initialize Train instance
            stubTrain = new Train();

            stubBandit = new Bandit("Rayan", 1) {
                @Override
                public String braque(Train train) {
                    return "Braquage executed";
                }
                @Override
                public String tir(Train train, Direction direction) {
                    return "Shot " + direction;
                }
                @Override
                public String frappe(Train train) {
                    return "Frappe executed";
                }
            };

            stubMovable = new Movable() {
                @Override
                public String move(Train train, Direction direction) {
                    return "Moved " + direction;
                }
            @Override
            public List<Direction> mouvementsPossibles(Train t) {
                return Arrays.asList(Direction.values());
            }
        };
        direction = Direction.AVANT; // Example direction
    }

    @Test
    public void testBraquageExecuter() {
        Braquage braquage = new Braquage(stubBandit, stubTrain);
        Assert.assertEquals("Braquage executed", braquage.executer());
    }

    @Test
    public void testBraquageToString() {
        Braquage braquage = new Braquage(stubBandit, stubTrain);
        Assert.assertEquals("Braque", braquage.toString());
    }

    @Test
    public void testDeplacementExecuter() {
        Deplacement deplacement = new Deplacement(stubMovable, stubTrain, direction);
        Assert.assertEquals("Moved AVANT", deplacement.executer());
    }

    @Test
    public void testDeplacementToString() {
        Deplacement deplacement = new Deplacement(stubMovable, stubTrain, direction);
        Assert.assertEquals("Deplacement Avant", deplacement.toString());
    }

    @Test
    public void testTirExecuter() {
        Tir tir = new Tir(stubBandit, stubTrain, direction);
        Assert.assertEquals("Shot Avant", tir.executer());
    }

    @Test
    public void testTirToString() {
        Tir tir = new Tir(stubBandit, stubTrain, direction);
        Assert.assertEquals("Tir Avant", tir.toString());
    }

    @Test
    public void testFrappeExecuter() {
        Frappe frappe = new Frappe(stubBandit, stubTrain);
        Assert.assertEquals("Frappe executed", frappe.executer());
    }

    @Test
    public void testFrappeToString() {
        Frappe frappe = new Frappe(stubBandit, stubTrain);
        Assert.assertEquals("Frappe", frappe.toString());
    }
}
