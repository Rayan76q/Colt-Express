package Vue;

import Modele.*;


import javax.swing.*;
import java.awt.*;

public class CVue {

    private JFrame frame;

    private VuePlateau plateau;
    private VueCommandes tableau_de_bord;


    public CVue(Partie p) {

        frame = new JFrame();
        frame.setTitle("Colt Express");

        frame.setLayout(new FlowLayout());


        plateau = new VuePlateau(p.getTrain());
        frame.add(plateau);
        tableau_de_bord = new VueCommandes(p);
        frame.add(tableau_de_bord);

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

    private final static int TAILLE = 30;


    public VuePlateau(Train t) {
        this.train = t;

        train.addObserver(this);

        Dimension dim = new Dimension(TAILLE*Partie.NB_WAGON,
                TAILLE*2);
        this.setPreferredSize(dim);
    }


    public void update() { repaint(); }


    public void paintComponent(Graphics g) {
        super.repaint();
        for(int i=0; i<Partie.NB_WAGON; i++) {
                paint(g, train.get_Wagon()[i], i*TAILLE);

        }
    }

    //TODO
    private void paint(Graphics g, Wagon w, int x) {
        return ;
    }
}
