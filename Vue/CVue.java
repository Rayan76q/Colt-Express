package Vue;

import Modele.*;
import Controleur.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        frame.setLayout(new BorderLayout());


        plateau = new VuePlateau(p.getTrain());
        frame.add(plateau, BorderLayout.NORTH);

        tableau_de_bord = new VueCommandes(p);
        frame.add(tableau_de_bord, BorderLayout.SOUTH);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class VueCommandes extends JPanel implements Observer{

    private final static Font font1 = new Font("Arial", Font.BOLD, 20);
    private final static Font font2 = new Font("Arial", Font.BOLD, 14);

    private Partie partie;


    private static void addComponent(Container container, Component component, GridBagConstraints gbc,
                                     int gridx, int gridy, int gridwidth, int gridheight) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        container.add(component, gbc);
    }

    public VueCommandes(Partie p) {
        this.partie = p;
        Dimension dim = new Dimension(CVue.screenWidth,
                CVue.screenHeight*3/10 );
        this.setPreferredSize(dim);
        this.setLayout(new GridBagLayout());

        //Les Panels
        JPanel text = new JPanel();
        JPanel boutons = new JPanel();
        JPanel actions = new JPanel();
        JPanel fleches = new JPanel();
        JPanel prompt = new JPanel();

        //Switch le panel de boutons
        CardLayout switcher = new CardLayout();
        boutons.setLayout(switcher);
        boutons.add(actions,"panel 1");
        boutons.add(fleches,"panel 2");

        //Panel de titre
        text.setLayout(new GridLayout(3,1));
        JLabel t1 = new JLabel("Tour du Joueur N°"+(partie.getJoueurAct()+1));
        Bandit b = partie.getJoueurs()[0].getPions().get(0);
        JLabel t2 = new JLabel("Bandit : " + b );
        JLabel t3 = new JLabel(b.compte_butins()+"$");
        t1.setFont(font1);
        t2.setFont(font2);
        t3.setFont(font2);
        t1.setHorizontalAlignment(JLabel.CENTER);
        t2.setHorizontalAlignment(JLabel.CENTER);
        t3.setHorizontalAlignment(JLabel.CENTER);
        text.add(t1);
        text.add(t2);
        text.add(t3);


        //Panels des boutons d'actions
        boutons.setBorder(new EmptyBorder(0, dim.width/5, 0, dim.width/5));

        actions.setLayout(new GridLayout(2,4,50,5));
        JButton boutonAction1 = new JButton("Action");
        JButton boutonAction2 = new JButton("Action");
        JButton boutonSeDeplacer = new JButton("Se Deplacer");
        JButton boutonTir = new JButton("Tirer");
        JButton boutonFrappe = new JButton("Frapper");
        JButton boutonBraque = new JButton("Braquer");
        JButton boutonRetour1 = new JButton("Retour");
        JButton boutonRetour = new JButton("Retour");

        actions.add(boutonSeDeplacer);
        actions.add(boutonBraque);
        actions.add(boutonTir);
        actions.add(boutonFrappe);
        actions.add(new JLabel());
        actions.add(boutonRetour);
        actions.add(boutonAction1);
        actions.add(new JLabel());


        //Action en cours
        JLabel promptText = new JLabel("Action N°"+(partie.getTempo()+1));
        promptText.setFont(font2);
        prompt.add(promptText);

        fleches.setLayout(new GridLayout(4,4,10,0));
        fleches.setPreferredSize(new Dimension(dim.width/4 , dim.height));

        //Choix de directions
        JButton gauche = new JButton("←");
        JButton droite = new JButton("→");
        JButton haut = new JButton("↑");
        JButton bas = new JButton("↓");
        JButton retourActions = new JButton("Retour");
        fleches.add(new JLabel());
        fleches.add(haut);
        fleches.add(new JLabel());
        fleches.add(gauche);
        fleches.add(new JLabel());
        fleches.add(droite);
        fleches.add(new JLabel());
        fleches.add(bas);
        fleches.add(new JLabel());
        fleches.add(retourActions);
        fleches.add(new JLabel());
        fleches.add(boutonAction2);

        //Layout globale
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        addComponent(this,text , gbc , 0,0 , 1,1);
        addComponent(this,boutons , gbc , 0,1,1,1);
        addComponent(this,prompt , gbc , 0,2,1,1);


        this.setBorder(BorderFactory.createLineBorder(Color.black));

        //EventListeners
        boutonAction1.addActionListener(e -> partie.confirmeAction());
        boutonAction2.addActionListener(e -> partie.confirmeAction());
        boutonSeDeplacer.addActionListener(new ControleurMouvement(p,switcher,boutons));
        boutonTir.addActionListener(new ControleurTir(p,switcher,boutons));
        boutonBraque.addActionListener(e -> partie.setActionChoisie(2));
        boutonFrappe.addActionListener(e -> partie.setActionChoisie(3));
        boutonRetour.addActionListener(e -> partie.annuleAction());
        haut.addActionListener(e -> partie.setDirectionChoisie(Direction.HAUT));
        bas.addActionListener(e -> partie.setDirectionChoisie(Direction.BAS));
        gauche.addActionListener(e -> partie.setDirectionChoisie(Direction.AVANT));
        droite.addActionListener(e -> partie.setDirectionChoisie(Direction.ARRIERE));
        retourActions.addActionListener(e -> switcher.next(boutons));

        partie.addObserver(this);
    }

    @Override
    public void paintComponent(Graphics g){
        JPanel panel1 = (JPanel) this.getComponent(0);
        ((JLabel)panel1.getComponent(0)).setText("Tour du Joueur N°"+(partie.getJoueurAct()+1));
        Bandit b = partie.getJoueurs()[partie.getJoueurAct()].getPionAct();
        ((JLabel)panel1.getComponent(1)).setText("Bandit : "+b);
        ((JLabel)panel1.getComponent(2)).setText(b.compte_butins() +"$");

        JPanel panel2 = (JPanel) this.getComponent(2);
        ((JLabel)panel2.getComponent(0)).setText("Action N°"+(partie.getTempo()+1));
    }


    public void update() {
        repaint();
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
        Dimension dim = new Dimension(CVue.screenWidth,
                CVue.screenHeight*6/10 );
        this.setPreferredSize(dim);

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
    }


    public void update() { repaint(); }


    public void paintComponent(Graphics g) {
        super.repaint();
        super.paintComponent(g);
        for(int i=0; i<Partie.NB_WAGON; i++) {
            paintWagon(g, train.get_Wagon()[i], i*WIDTH+Partie.NB_WAGON*dec/2);
            paintPersonne(g, train.get_Wagon()[i], i*WIDTH+Partie.NB_WAGON*dec/2);
            /*
            TODO
            paintButin();
            */

        }
    }


    private void paintWagon(Graphics g, Wagon w, int x) {
        //Dessin du toit
        g.setColor(Color.BLACK);
        int y = HEIGHT;
        g.drawRect(x, y, WIDTH, HEIGHT);

        //Dessin de l'interieur
        g.setColor(new Color(139, 69, 19, 100));
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
