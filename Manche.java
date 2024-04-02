import java.util.Random;

public class Manche {

    public int NombreTour;

    public Manche() {
        Random random = new Random();
        NombreTour = random.nextInt(5) + 1;
    }
}
