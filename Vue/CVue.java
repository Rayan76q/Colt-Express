package Vue;

import Modele.*;
import Controleur.*;
import Modele.Action;

import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;

import static Modele.Partie.DEFAULT_HP;
import static Modele.Personne.spriteH;
import static Modele.Personne.spriteW;


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

    private final ImageIcon[] sprites;
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
        this.sprites  = new ImageIcon[]{new ImageIcon(getClass().getResource("Images/coeur.png")),new ImageIcon(getClass().getResource("Images/ammo.png")),
        new ImageIcon(getClass().getResource("Images/wound.png"))};
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
        JPanel promptPlanification = new JPanel();
        JPanel promptExecution = new JPanel();

        //Switch le panel de boutons
        CardLayout switcher = new CardLayout();
        boutons.setLayout(switcher);
        boutons.add(actions,"card 1");
        boutons.add(fleches,"card 2");

        //Prompts
        CardLayout switcher2 = new CardLayout();
        prompt.setLayout(switcher2);
        prompt.add(promptPlanification, "p1");
        prompt.add(promptExecution, "p2");

        //Panel de titre
        text.setLayout(new GridLayout(3,1));
        JLabel t1 = new JLabel("Tour du Joueur N°"+(partie.getJoueurAct()+1));
        Bandit b = partie.getJoueurs()[0].getPions().get(0);
        JLabel t2 = new JLabel("Bandit : " + b );
        JPanel t3 = new JPanel(new GridLayout(1,5));
        t3.setBorder(new EmptyBorder(0, dim.width/3, 0, dim.width/3));
        JLabel l1 = new JLabel();
        JLabel l2 = new JLabel(": " +DEFAULT_HP);
        JLabel l3 = new JLabel(b.compte_butins()+"$");
        JLabel l4 = new JLabel();
        JLabel l5 = new JLabel(": " +b.get_ammo());
        l1.setIcon(sprites[0]);
        l4.setIcon(sprites[1]);
        t3.add(l1);
        t3.add(l2);
        t3.add(l3);
        t3.add(l4);
        t3.add(l5);
        t1.setFont(font1);
        t2.setFont(font2);
        t3.setFont(font2);
        t1.setHorizontalAlignment(JLabel.CENTER);
        t2.setHorizontalAlignment(JLabel.CENTER);
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
        promptPlanification.setLayout(new GridLayout(1,DEFAULT_HP));
        JLabel promptText = new JLabel();
        promptText.setFont(font2);
        for (int i = 1; i <= DEFAULT_HP; i++) {
            promptText= new JLabel("Action N°"+i);
            promptText.setHorizontalAlignment(JLabel.CENTER);
            promptPlanification.add(promptText);
        }

        //Pour L'execution
        promptExecution.add(new JLabel("Au tour de "+partie.getJoueurs()[0].getPions().get(0)+" ( J1 )"));


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
        boutonAction2.addActionListener(e -> {partie.confirmeAction();switcher.show(boutons,"card 1");});
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


    public void paintStats(){
        JPanel panel1 = (JPanel) this.getComponent(0);
        ((JLabel)panel1.getComponent(0)).setText("Tour du Joueur N°"+(partie.getJoueurAct()+1));
        Bandit b = partie.getJoueurs()[partie.getJoueurAct()].getPionAct();
        ((JLabel)panel1.getComponent(1)).setText("Bandit : "+b);

        JPanel stats = (JPanel) panel1.getComponent(2);
        JLabel statcoeur = ((JLabel)stats.getComponent(0));
        statcoeur.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel statammo = ((JLabel)stats.getComponent(3));
        statammo.setHorizontalAlignment(4);
        ((JLabel)stats.getComponent(1)).setText(": " +b.get_hitPoints());
        JLabel cash  = ((JLabel)stats.getComponent(2));
        cash.setText(b.compte_butins()+"$");
        cash.setHorizontalAlignment(0);
        ((JLabel)stats.getComponent(4)).setText(": " +b.get_ammo());
    }

    @Override
    public void paintComponent(Graphics g){
        paintStats();
        JPanel panel = (JPanel) this.getComponent(2);
        ((CardLayout)panel.getLayout()).show(panel,"p1");
        Bandit b = partie.getJoueurs()[partie.getJoueurAct()].getPionAct();
        JPanel panel2 = (JPanel) panel.getComponent(0);
        for (int i = 0; i < DEFAULT_HP; i++) {
            JLabel p = ((JLabel) panel2.getComponent(i));
            p.setIcon(null);
            p.setText("");
            p.revalidate();

            if(i >= b.get_hitPoints() ){
                p.setIcon(sprites[2]);
            }
            else {
                Action a = partie.getMatrice_action()[b.get_id()][i];
                if (a != null) {
                    p.setText(a.toString());
                }
                else{
                    p.setText("Action N°" + (i+1));
                }
            }

        }

    }


    public void update() {
        repaint();
    }

    @Override
    public void update(String str) {
        paintStats();
        JPanel panel = (JPanel) this.getComponent(2);
        ((CardLayout)panel.getLayout()).show(panel,"p2");
        JPanel panel2 = (JPanel) panel.getComponent(1);

        JLabel textPrompt = (JLabel) panel2.getComponent(0);
        textPrompt.setText(str);
    }
}

class VuePlateau extends JPanel implements Observer {

    private Train train;

    //Hauteur et largeur d'un étage d'un wagon
    private static int dec = 30;
    private final static int WIDTH= (int) (CVue.screenWidth/Partie.NB_WAGON - dec);
    private final static int HEIGHT= CVue.screenHeight/8;


    private HashMap<Integer , ImageIcon> spriteMapPersonnes = new HashMap<>();
    private HashMap<Butin , ImageIcon> spriteMapButins = new HashMap<>();
    private ImageIcon[] coffreSprite = {new ImageIcon(getClass().getResource("Images/safe.png")) , new ImageIcon(getClass().getResource("Images/openSafe.png"))};

    public VuePlateau(Train t){
        Dimension dim = new Dimension(CVue.screenWidth,
                CVue.screenHeight*6/10 );
        this.setPreferredSize(dim);

        this.train = t;
        for(Wagon w : t.get_Wagon()){
            for (Bandit b : w.getToit()){
                spriteMapPersonnes.put(b.get_id() , new ImageIcon(getClass().getResource(b.getSprite())));
            }
            for(Personne p : w.getInterieur()){
                spriteMapPersonnes.put(p.get_id() , new ImageIcon(getClass().getResource(p.getSprite())));
                if(p instanceof Passager){
                    Butin b = ((Passager) p).getPoche();
                    spriteMapButins.put(b , new ImageIcon(getClass().getResource(b.getSprite())));
                }
            }
        }
        Butin magot = ((Locomotive)t.get_Wagon()[0]).getMag();
        spriteMapButins.put( magot , new ImageIcon(getClass().getResource(magot.getSprite())));
        train.addObserver(this);
    }

    public void update() { repaint(); }

    @Override
    public void update(String str) {
        update();
    }



    public BufferedImage colorImage(BufferedImage loadImg, int red, int green, int blue) {
        BufferedImage img = new BufferedImage(loadImg.getWidth(), loadImg.getHeight(),
                BufferedImage.TRANSLUCENT);
        Graphics2D graphics = img.createGraphics();
        Color newColor = new Color(red, green, blue, 0);
        graphics.setXORMode(newColor);
        graphics.drawImage(loadImg, null, 0, 0);
        graphics.dispose();
        return img;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.repaint();
        super.paintComponent(g);
        for(int i=0; i<Partie.NB_WAGON; i++) {
            paintWagon(g, train.get_Wagon()[i], i*WIDTH+Partie.NB_WAGON*dec/2);
            if(i==0) paintCoffre(g, (Locomotive) train.get_Wagon()[0], Partie.NB_WAGON*dec/2);
            paintPersonne(g, train.get_Wagon()[i], i*WIDTH+Partie.NB_WAGON*dec/2);
            paintButin(g, train.get_Wagon()[i], i*WIDTH+Partie.NB_WAGON*dec/2);
        }
    }

    private void paintCoffre(Graphics g, Locomotive l, int x) {
        if(l.magot_dispo()){
            g.drawImage(coffreSprite[0].getImage(), x+20,HEIGHT*2 ,100,100, this);
        }
        else{
            g.drawImage(coffreSprite[1].getImage(), x+20, HEIGHT*2,100,100, this);
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
            g.drawImage(spriteMapPersonnes.get(w.getToit().get(i).get_id()).getImage(), x+i*(spriteW+5), HEIGHT,spriteW, spriteH, this);
        }
        //Interieur
        for (int i = 0; i < w.getInterieur().size(); i++) {
            g.drawImage(spriteMapPersonnes.get(w.getInterieur().get(i).get_id()).getImage(), x+i*(spriteW+5), HEIGHT*2,spriteW,spriteH, this);
        }
    }

    private void paintButin(Graphics g, Wagon w, int x) {
        //Toit
        for (int i = 0; i < w.getLootToit().size(); i++) {
            Butin b = w.getLootToit().get(i);
            g.drawImage(spriteMapButins.get(b).getImage(), x+i*(spriteW+5), HEIGHT*2-b.getSpriteH(),b.getSpriteW(),b.getSpriteH(), this);
        }
        //Interieur
        for (int i = 0; i < w.getLootInt().size(); i++) {
            Butin b = w.getLootInt().get(i);
            g.drawImage(spriteMapButins.get(b).getImage(), x+i*(spriteW+5), HEIGHT*3 -b.getSpriteH() ,b.getSpriteW(),b.getSpriteH(), this);
        }
    }
}