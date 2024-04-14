package Vue;

import Modele.*;
import Controleur.*;
import Modele.Action;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;



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

class VueInput extends JPanel {
    private CVue vue;

    private boolean[] flags = new boolean[]{false,false,false,false,false,false};
    private String[][] nomsBandits;


    private JPanel createLabelTextFieldPanel(String labelText) {
        JPanel labelTextFieldPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(labelText);
        JTextField textField = new JTextField();
        labelTextFieldPanel.add(label, BorderLayout.WEST);
        labelTextFieldPanel.add(textField, BorderLayout.CENTER);
        return labelTextFieldPanel;
    }

    private void getNB_JOUEUR(JPanel names,  JTextField nb_joueurs){
        try {
            String input = nb_joueurs.getText();
            int number = Integer.parseInt(input);
            if(number < 0)throw new RuntimeException();
            nb_joueurs.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            Partie.NB_JOUEURS = number;
            flags[0]= true;
            if(!Arrays.asList(flags).contains(false)) {names.setLayout(new GridLayout(Partie.NB_JOUEURS/8+1,8));createGrid(names);}
        } catch (Exception ex) {
            flags[0] = false;
            nb_joueurs.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        }
    }

    private void getNb_Manches(JPanel names,  JTextField nb_manches){
        try {
            String input = nb_manches.getText();
            int number = Integer.parseInt(input);
            if(number < 3)throw new RuntimeException();
            nb_manches.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            Partie.NB_MANCHES = number;
            flags[1] = true;
            if(!Arrays.asList(flags).contains(false)){names.setLayout(new GridLayout(Partie.NB_JOUEURS/8+1,8));createGrid(names);}
        } catch (Exception ignored) {
            flags[1] = false;
            nb_manches.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        }
    }

    private void getHP(JPanel names,  JTextField field){
        try {
            String input = field.getText();
            int number = Integer.parseInt(input);
            if(number < 2 || number > 8)throw new RuntimeException();
            field.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            Partie.DEFAULT_HP = number;
            flags[2] = true;
            if(!Arrays.asList(flags).contains(false)){names.setLayout(new GridLayout(Partie.NB_JOUEURS/8+1,8));createGrid(names);}
        } catch (Exception ignored) {
            flags[2] = false;
            field.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        }
    }
    private void getAmmo(JPanel names,  JTextField field){
        try {
            String input = field.getText();
            int number = Integer.parseInt(input);
            if(number < 0 || number > 12)throw new RuntimeException();
            field.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            Partie.NB_MUNITIONS = number;
            flags[3] = true;
            if(!Arrays.asList(flags).contains(false)){names.setLayout(new GridLayout(Partie.NB_JOUEURS/8+1,8));createGrid(names);}
        } catch (Exception ignored) {
            flags[3] = false;
            field.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        }
    }

    private void getPrecision(JPanel names,  JTextField field){
        try {
            String input = field.getText();
            double number = Double.parseDouble(input);
            if(number < 0.0|| number > 1.0)throw new RuntimeException();
            field.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            Partie.DEFAULT_PRECISION = number;
            flags[4] = true;
            if(!Arrays.asList(flags).contains(false)){names.setLayout(new GridLayout(Partie.NB_JOUEURS/8+1,8));createGrid(names);}
        } catch (Exception ignored) {
            flags[4] = false;
            field.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        }
    }

    private void getNevrosite(JPanel names,  JTextField field){
        try {
            String input = field.getText();
            double number = Double.parseDouble(input);
            if(number < 0.0|| number > 1.0)throw new RuntimeException();
            field.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            Partie.NEVROSITE_MARSHALL = number;
            flags[5] = true;
            if(!Arrays.asList(flags).contains(false)){names.setLayout(new GridLayout(Partie.NB_JOUEURS/8+1,8));createGrid(names);}
        } catch (Exception ignored) {
            flags[5] = false;
            field.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        }
    }

    public VueInput(CVue c){
        vue = c;
        this.setLayout(new BorderLayout());

        Dimension dim1 = new Dimension(CVue.screenWidth,
                CVue.screenHeight * 2/ 10);

        Dimension dim2 = new Dimension(CVue.screenWidth,
                CVue.screenHeight * 4/ 10);


        JPanel title = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new BorderLayout());

        //Title
        JLabel t = new JLabel("Colt Express");
        t.setFont(CVue.font1);
        t.setHorizontalAlignment(0);
        title.add(t , BorderLayout.SOUTH);
        title.setPreferredSize(dim1);
        title.setBorder(BorderFactory.createLineBorder(Color.black));
        this.add(title,BorderLayout.NORTH);

        JPanel constants = new JPanel(new GridLayout(2,3,20,10));
        constants.setBorder(new EmptyBorder(0,30,0,30));

        constants.add(createLabelTextFieldPanel("Nombre de joueurs : "));
        constants.add(createLabelTextFieldPanel("Nombre de manches : "));
        constants.add(createLabelTextFieldPanel("HP : "));
        constants.add(createLabelTextFieldPanel("Munitions : "));
        constants.add(createLabelTextFieldPanel("Precision Bandit : "));
        constants.add(createLabelTextFieldPanel("Nevrosite Marshall : "));


        JPanel names = new JPanel();
        JTextField nb_joueurs = (JTextField) ((JPanel)constants.getComponent(0)).getComponent(1);
        nb_joueurs.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTextFieldContent();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTextFieldContent();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }

            private void updateTextFieldContent() {
                String text = nb_joueurs.getText();
                if (text != null && !text.isEmpty()) getNB_JOUEUR(names , nb_joueurs);
            }
        });

        JTextField nb_manches = (JTextField) ((JPanel)constants.getComponent(1)).getComponent(1);
        nb_manches.setText(">= 3");
        nb_manches.setForeground(Color.GRAY);
        nb_manches.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (nb_manches.getText().equals(">= 3")) {
                    nb_manches.setText("");
                    nb_manches.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (nb_manches.getText().isEmpty()) {
                    nb_manches.setText(">= 3");
                    nb_manches.setForeground(Color.GRAY);
                }
            }
        });
        nb_manches.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTextFieldContent();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTextFieldContent();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }

            private void updateTextFieldContent() {
                String text = nb_manches.getText();
                if (text != null && !text.isEmpty() &&  !text.equals(">= 3")) getNb_Manches(names , nb_manches);

            }
        });

        JTextField hp = (JTextField) ((JPanel)constants.getComponent(2)).getComponent(1);
        hp.setText(">= 2 et <= 8");
        hp.setForeground(Color.GRAY);
        hp.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (hp.getText().equals(">= 2 et <= 8")) {
                    hp.setText("");
                    hp.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (hp.getText().isEmpty()) {
                    hp.setText(">= 2 et <= 8");
                    hp.setForeground(Color.GRAY);
                }
            }
        });
        hp.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTextFieldContent();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTextFieldContent();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }

            private void updateTextFieldContent() {
                String text = hp.getText();
                if (text != null && !text.isEmpty() &&  !text.equals(">= 2 et <= 8")) getHP(names ,hp);

            }
        });

        JTextField ammo = (JTextField) ((JPanel)constants.getComponent(3)).getComponent(1);
        ammo.setText(">= 0 et <= 12");
        ammo.setForeground(Color.GRAY);
        ammo.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (ammo.getText().equals(">= 0 et <= 12")) {
                    ammo.setText("");
                    ammo.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (ammo.getText().isEmpty()) {
                    ammo.setText(">= 0 et <= 12");
                    ammo.setForeground(Color.GRAY);
                }
            }
        });
        ammo.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTextFieldContent();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTextFieldContent();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }

            private void updateTextFieldContent() {
                String text = ammo.getText();
                if (text != null && !text.isEmpty() &&  !text.equals(">= 0 et <= 12")) getAmmo(names ,ammo);

            }
        });

        JTextField precision = (JTextField) ((JPanel)constants.getComponent(4)).getComponent(1);
        precision.setText(">= 0.0 et <= 1.0");
        precision.setForeground(Color.GRAY);
        precision.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (precision.getText().equals(">= 0.0 et <= 1.0")) {
                    precision.setText("");
                    precision.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (precision.getText().isEmpty()) {
                    precision.setText(">= 0.0 et <= 1.0");
                    precision.setForeground(Color.GRAY);
                }
            }
        });
        precision.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTextFieldContent();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTextFieldContent();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }

            private void updateTextFieldContent() {
                String text = precision.getText();
                if (text != null && !text.isEmpty() &&  !text.equals(">= 0.0 et <= 1.0")) getPrecision(names ,precision);

            }
        });

        JTextField nev = (JTextField) ((JPanel)constants.getComponent(5)).getComponent(1);
        nev.setText(">= 0.0 et <= 1.0");
        nev.setForeground(Color.GRAY);
        nev.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (nev.getText().equals(">= 0.0 et <= 1.0")) {
                    nev.setText("");
                    nev.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (nev.getText().isEmpty()) {
                    nev.setText(">= 0.0 et <= 1.0");
                    nev.setForeground(Color.GRAY);
                }
            }
        });
        nev.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTextFieldContent();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTextFieldContent();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }

            private void updateTextFieldContent() {
                String text = nev.getText();
                if (text != null && !text.isEmpty() &&  !text.equals(">= 0.0 et <= 1.0")) getNevrosite(names ,nev);

            }
        });

        JPanel play = new JPanel();
        play.setPreferredSize(dim2);
        JButton playButton = new JButton("Play");
        playButton.addActionListener(e -> vue.switchToGame(nomsBandits)); //Commence la partie avec les parametres

        play.add(playButton);
        panel.add(constants, BorderLayout.NORTH);
        panel.add(names, BorderLayout.CENTER);
        panel.add(play , BorderLayout.SOUTH);
        panel.setPreferredSize(new Dimension(CVue.screenWidth,
                CVue.screenHeight * 7/ 10));
        panel.setBorder(new EmptyBorder(30,0,30,0));
        this.add(panel, BorderLayout.SOUTH);
    }

    private void createGrid(JPanel names) {
        names.removeAll();
        names.revalidate();
        names.repaint();
        nomsBandits = new String[Partie.NB_JOUEURS][Partie.NB_BANDITS_JOUEUR];
        for (int i = 0; i <Partie.NB_JOUEURS; i++) {
            names.add(new JLabel());
            JPanel cell = new JPanel(new BorderLayout());
            int joueurNb = i /Partie.NB_BANDITS_JOUEUR;
            JLabel joueur = new JLabel("J"+(joueurNb+1));
            joueur.setHorizontalAlignment(0);
            int banditNb = i % Partie.NB_BANDITS_JOUEUR;
            JLabel bandit = new JLabel("Bandit "+(banditNb+1));
            bandit.setHorizontalAlignment(0);
            String defaultName = "J"+(joueurNb+1)+"b"+(banditNb+1);
            JTextField nom_bandit = new JTextField(defaultName);
            nom_bandit.setForeground(Color.GRAY);
            nomsBandits[joueurNb][banditNb] = defaultName;
            nom_bandit.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (nom_bandit.getText().equals(defaultName)) {
                        nom_bandit.setText("");
                        nom_bandit.setForeground(Color.BLACK);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (nom_bandit.getText().isEmpty()) {
                        nom_bandit.setText(defaultName);
                        nom_bandit.setForeground(Color.GRAY);
                    }
                }
            });
            nom_bandit.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    updateTextFieldContent();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    updateTextFieldContent();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                }

                private void updateTextFieldContent() {
                    String text = nom_bandit.getText();
                    if (text != null && !text.isEmpty()) {
                        String input = nom_bandit.getText();
                        nomsBandits[joueurNb][banditNb] = input;
                    }

                }
            });

            cell.add(joueur , BorderLayout.NORTH);
            cell.add(bandit, BorderLayout.CENTER);
            cell.add(nom_bandit , BorderLayout.SOUTH);
            names.add(cell);
            names.add(new JLabel());


        }


    }


}

class VueCommandes extends JPanel implements Observer{

    private final ImageIcon[] sprites;

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
        JLabel l2 = new JLabel(": " +Partie.DEFAULT_HP);
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
        t1.setFont(CVue.font1);
        t2.setFont(CVue.font2);
        t3.setFont(CVue.font2);
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
        promptPlanification.setLayout(new GridLayout(1,Partie.DEFAULT_HP));
        JLabel promptText = new JLabel();
        promptText.setFont(CVue.font2);
        for (int i = 1; i <=Partie.DEFAULT_HP; i++) {
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
        for (int i = 0; i < Partie.DEFAULT_HP; i++) {
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

    //Hauteur et largeur d'un Ã©tage d'un wagon
    private static int dec = 30;
    private final static int WIDTH = (int) (CVue.screenWidth / Partie.NB_WAGON - dec);
    private final static int HEIGHT = CVue.screenHeight / 8;


    private HashMap<Integer, ImageIcon> spriteMapPersonnes = new HashMap<>();
    private HashMap<Butin, ImageIcon> spriteMapButins = new HashMap<>();
    private ImageIcon[] coffreSprite = {new ImageIcon(getClass().getResource("Images/safe.png")), new ImageIcon(getClass().getResource("Images/openSafe.png"))};

    public VuePlateau(Train t) {
        Dimension dim = new Dimension(CVue.screenWidth,
                CVue.screenHeight * 6 / 10);
        this.setPreferredSize(dim);

        this.train = t;
        for (Wagon w : t.get_Wagon()) {
            for (Bandit b : w.getToit()) {
                spriteMapPersonnes.put(b.get_id(), new ImageIcon(getClass().getResource(b.getSprite())));
            }
            for (Personne p : w.getInterieur()) {
                spriteMapPersonnes.put(p.get_id(), new ImageIcon(getClass().getResource(p.getSprite())));
                if (p instanceof Passager) {
                    Butin b = ((Passager) p).getPoche();
                    spriteMapButins.put(b, new ImageIcon(getClass().getResource(b.getSprite())));
                }
            }
        }
        Butin magot = ((Locomotive) t.get_Wagon()[0]).getMag();
        spriteMapButins.put(magot, new ImageIcon(getClass().getResource(magot.getSprite())));
        train.addObserver(this);

    }

    public void update() { repaint(); }

    @Override
    public void update(String str) {
        update();
    }


    public static BufferedImage toBufferedImage(Image img) {
        // Create a buffered image with the same dimensions and transparency as the original image
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image onto the buffered image
        Graphics2D g2d = bimage.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        return bimage;
    }

    public static BufferedImage colorImage(BufferedImage loadImg, int red, int green, int blue) {
        int width = loadImg.getWidth();
        int height = loadImg.getHeight();
        BufferedImage tintedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = tintedImg.createGraphics();
        g2d.drawImage(loadImg, 0, 0, null);
        Color tint = new Color(red, green, blue);
        g2d.setColor(tint);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP));
        g2d.fillRect(0, 0, width, height);
        g2d.dispose();

        return tintedImg;
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

    private void drawBandit(Graphics g, Bandit b ,Wagon w, int x , int y){
        if(b.isTargeted()) {
            BufferedImage originalSprite = toBufferedImage(spriteMapPersonnes.get(b.get_id()).getImage());
            BufferedImage redSprite = colorImage(originalSprite, 255, 0, 0);
            g.drawImage(redSprite, x, y, Personne.spriteW, Personne.spriteH, this);
            Thread hitThread = new Thread(() -> {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                g.drawImage(spriteMapPersonnes.get(b.get_id()).getImage(), x, y, Personne.spriteW, Personne.spriteH, this);
                b.setTargeted(false);
            });
            hitThread.start();
        }
        else g.drawImage(spriteMapPersonnes.get(b.get_id()).getImage(), x, y,Personne.spriteW, Personne.spriteH, this);
        g.drawString(b.toString() ,x,y-10);
    }

    private void paintPersonne(Graphics g, Wagon w, int x) {
        //Toit
        for (int i = 0; i < w.getToit().size(); i++) {
            Bandit b = w.getToit().get(i);
            drawBandit(g,b,w,x+ i* (Personne.spriteW + 5),HEIGHT);
        }
        //Interieur
        for (int i = 0; i < w.getInterieur().size(); i++) {
            Personne p = w.getInterieur().get(i);
            if(p instanceof Bandit){
               drawBandit(g,(Bandit) p,w,  x+i* (Personne.spriteW + 5) , 2*HEIGHT+20);
            }
            else g.drawImage(spriteMapPersonnes.get(p.get_id()).getImage(), x+i*(Personne.spriteW+5), HEIGHT*2,Personne.spriteW,Personne.spriteH, this);
        }
    }

    private void paintButin(Graphics g, Wagon w, int x) {
        //Toit
        for (int i = 0; i < w.getLootToit().size(); i++) {
            Butin b = w.getLootToit().get(i);
            g.drawImage(spriteMapButins.get(b).getImage(), x+i*(Personne.spriteW+5), HEIGHT*2-b.getSpriteH(),b.getSpriteW(),b.getSpriteH(), this);
        }
        //Interieur
        for (int i = 0; i < w.getLootInt().size(); i++) {
            Butin b = w.getLootInt().get(i);
            g.drawImage(spriteMapButins.get(b).getImage(), x+i*(Personne.spriteW+5), HEIGHT*3 -b.getSpriteH() ,b.getSpriteW(),b.getSpriteH(), this);
        }
    }
}