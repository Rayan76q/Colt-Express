package Vue;

import Modele.*;


import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class CVue {
    static Toolkit toolkit = Toolkit.getDefaultToolkit();
    static final Dimension screenSize = toolkit.getScreenSize();
    static final int screenWidth = screenSize.width;
    static final int screenHeight = screenSize.height;


    //Main graphique
    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            Partie p = new Partie();
            CVue vue = new CVue(p);
        });
    }

    private JFrame frame;

    private VuePlateau plateau;
    private VueCommandes tableau_de_bord;


    public CVue(Partie p) {

        frame = new JFrame();
        frame.setTitle("Colt Express");

        frame.setLayout(new FlowLayout());


        plateau = new VuePlateau(p.getTrain());
        frame.add(plateau);
        /*
        TODO
        tableau_de_bord = new VueCommandes(p);
        frame.add(tableau_de_bord);
        */
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class VueCommandes extends JPanel {

    private Partie partie;


    public VueCommandes(Partie p) {
        this.partie = p;

        JButton boutonAvance = new JButton("Action");
        this.add(boutonAvance);


        //TODO
        //Controleur ctrl = new Controleur(p);

        //boutonAvance.addActionListener(ctrl);

    }
}

class VuePlateau extends JPanel implements Observer {

    private Train train;

    //Hauteur et largeur d'un étage d'un wagon
    private static int dec = 30;
    private final static int WIDTH= (int) (CVue.screenWidth/Partie.NB_WAGON - dec);
    private final static int HEIGHT= CVue.screenHeight/8;

    private final static int spriteH = 120;
    private final static int spriteW = 80;

    private HashMap<Integer , ImageIcon> spriteMap = new HashMap<Integer, ImageIcon>();

    public VuePlateau(Train t){

        this.train = t;
        for(Wagon w : t.get_Wagon()){
            for (Bandit b : w.getToit()){
                spriteMap.put(b.get_id() , new ImageIcon(getClass().getResource(b.getSprite())));
            }
            for(Personne p : w.getInterieur()){
                spriteMap.put(p.get_id() , new ImageIcon(getClass().getResource(p.getSprite())));
            }
        }
        train.addObserver(this);

        Dimension dim = new Dimension(CVue.screenWidth,
                CVue.screenHeight*7/8 );
        this.setPreferredSize(dim);
    }


    public void update() { repaint(); }


    public void paintComponent(Graphics g) {
        super.repaint();
        super.paintComponent(g);
        for(int i=0; i<Partie.NB_WAGON; i++) {
            paintWagon(g, train.get_Wagon()[i], i*WIDTH+Partie.NB_WAGON*dec/2);
            paintPersonne(g, train.get_Wagon()[i], i*WIDTH+Partie.NB_WAGON*dec/2);
        }
    }

    //TODO
    private void paintWagon(Graphics g, Wagon w, int x) {
        //Dessin du toit
        g.setColor(Color.BLACK);
        int y = HEIGHT;
        g.drawRect(x, y, WIDTH, HEIGHT);

        //Dessin de l'interieur
        g.setColor(new Color(139, 69, 19, 100)); // Brown color with some transparency
        y = HEIGHT*2;
        g.fillRect(x, y, WIDTH, HEIGHT);
        g.drawRect(x, y, WIDTH, HEIGHT);
    }

    private void paintPersonne(Graphics g, Wagon w, int x) {
        //Toit
        for (int i = 0; i < w.getToit().size(); i++) {
            g.drawImage(spriteMap.get(w.getToit().get(i).get_id()).getImage(), x+i*spriteW+20, HEIGHT,spriteH,spriteW, this);
        }

        for (int i = 0; i < w.getInterieur().size(); i++) {
            g.drawImage(spriteMap.get(w.getInterieur().get(i).get_id()).getImage(), x+i*spriteW+20, HEIGHT*2,spriteH,spriteW, this);
        }




    }
}
