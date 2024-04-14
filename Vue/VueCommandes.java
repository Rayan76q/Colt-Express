package Vue;

import Controleur.ControleurMouvement;
import Controleur.ControleurTir;
import Modele.Action;
import Modele.Bandit;
import Modele.Direction;
import Modele.Partie;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class VueCommandes extends JPanel implements Observer{

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

        //Racourcis Clavier
        createKeyShortcut(boutonSeDeplacer , KeyEvent.VK_D);
        createKeyShortcut(boutonTir , KeyEvent.VK_T);
        createKeyShortcut(boutonBraque , KeyEvent.VK_B);
        createKeyShortcut(boutonFrappe, KeyEvent.VK_F);
        createKeyShortcut(boutonAction1, KeyEvent.VK_ENTER);
        createKeyShortcut(boutonAction2, KeyEvent.VK_ENTER);
        createKeyShortcut(boutonRetour, KeyEvent.VK_ESCAPE);
        createKeyShortcut(haut , KeyEvent.VK_UP);
        createKeyShortcut(bas , KeyEvent.VK_DOWN);
        createKeyShortcut(gauche , KeyEvent.VK_LEFT);
        createKeyShortcut(droite , KeyEvent.VK_RIGHT);


        partie.addObserver(this);
    }

    private void createKeyShortcut(JButton b , int key){
        AbstractAction action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                b.doClick();
            }
        };

        KeyStroke keyStroke = KeyStroke.getKeyStroke(key, 0);
        InputMap inputMap = b.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = b.getActionMap();
        inputMap.put(keyStroke, "clickButton");
        actionMap.put("clickButton", action);
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
