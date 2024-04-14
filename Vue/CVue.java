package Vue;

import Modele.*;

import javax.swing.*;
import java.awt.*;


public class CVue {
    final static Font font1 = new Font("Arial", Font.BOLD, 20);
    final static Font font2 = new Font("Arial", Font.BOLD, 14);
    static Toolkit toolkit = Toolkit.getDefaultToolkit();
    static final Dimension screenSize = toolkit.getScreenSize();
    static final int screenWidth = screenSize.width;
    static final int screenHeight = screenSize.height;


    //Main graphique
    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            CVue vue = new CVue();
        });
    }

    private JFrame frame;
    private JPanel app;
    private JPanel container;
    private VuePlateau plateau;
    private VueCommandes tableau_de_bord;
    private VueInput menu;
    private Partie p;

    public CVue() {

        frame = new JFrame();
        frame.setTitle("Colt Express");

        app = new JPanel(new CardLayout());
        container = new JPanel(new BorderLayout());

        menu = new VueInput(this);
        app.add(menu);


        app.add(container);
        frame.add(app);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize( new Dimension(CVue.screenWidth,CVue.screenHeight));
        frame.setVisible(true);
    }

    public void switchToGame(String[][] names){
        p = new Partie(true , names );
        plateau = new VuePlateau(p.getTrain());
        container.add(plateau, BorderLayout.NORTH);
        tableau_de_bord = new VueCommandes(p);
        container.add(tableau_de_bord, BorderLayout.SOUTH);
        ((CardLayout)app.getLayout()).next(app);
    }
}

