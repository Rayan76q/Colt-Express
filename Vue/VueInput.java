package Vue;

import Modele.Partie;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Arrays;

public class VueInput extends JPanel {
    private CVue vue;

    private Boolean[] flags = new Boolean[]{false,false,false,false,false,false};
    private String[][] nomsBandits;


    private JPanel createLabelTextFieldPanel(String labelText) {
        JPanel labelTextFieldPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(labelText);
        JTextField textField = new JTextField();
        labelTextFieldPanel.add(label, BorderLayout.WEST);
        labelTextFieldPanel.add(textField, BorderLayout.CENTER);
        return labelTextFieldPanel;
    }

    private void getNB_JOUEUR(JPanel names,  JTextField nb_joueurs, JButton play ){
        try {
            String input = nb_joueurs.getText();
            int number = Integer.parseInt(input);
            if(number < 0)throw new RuntimeException();
            nb_joueurs.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            Partie.NB_JOUEURS = number;
            flags[0]= true;
            names.setLayout(new GridLayout(Partie.NB_JOUEURS*Partie.NB_BANDITS_JOUEUR/8+1,8));
            createGrid(names);
            if(!Arrays.asList(flags).contains(false)) {play.setEnabled(true);}
        } catch (Exception ex) {
            flags[0] = false;
            nb_joueurs.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            play.setEnabled(false);
        }
    }

    private void getNb_Manches(JTextField nb_manches , JButton play){
        try {
            String input = nb_manches.getText();
            int number = Integer.parseInt(input);
            if(number < 3)throw new RuntimeException();
            nb_manches.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            Partie.NB_MANCHES = number;
            flags[1] = true;
            if(!Arrays.asList(flags).contains(false)) {play.setEnabled(true);}
        } catch (Exception ignored) {
            flags[1] = false;
            nb_manches.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            play.setEnabled(false);
        }
    }

    private void getHP(JTextField field , JButton play){
        try {
            String input = field.getText();
            int number = Integer.parseInt(input);
            if(number < 2 || number > 8)throw new RuntimeException();
            field.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            Partie.DEFAULT_HP = number;
            flags[2] = true;
            if(!Arrays.asList(flags).contains(false)) {play.setEnabled(true);}
        } catch (Exception ignored) {
            flags[2] = false;
            field.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            play.setEnabled(false);
        }
    }
    private void getAmmo(JTextField field, JButton play){
        try {
            String input = field.getText();
            int number = Integer.parseInt(input);
            if(number < 0 || number > 12)throw new RuntimeException();
            field.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            Partie.NB_MUNITIONS = number;
            flags[3] = true;
            if(!Arrays.asList(flags).contains(false)) {play.setEnabled(true);}
        } catch (Exception ignored) {
            flags[3] = false;
            field.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            play.setEnabled(false);
        }
    }

    private void getPrecision(JTextField field , JButton play){
        try {
            String input = field.getText();
            double number = Double.parseDouble(input);
            if(number < 0.0|| number > 1.0)throw new RuntimeException();
            field.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            Partie.DEFAULT_PRECISION = number;
            flags[4] = true;
            if(!Arrays.asList(flags).contains(false)) {play.setEnabled(true);}
        } catch (Exception ignored) {
            flags[4] = false;
            field.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            play.setEnabled(false);
        }
    }

    private void getNevrosite(JTextField field , JButton play){
        try {
            String input = field.getText();
            double number = Double.parseDouble(input);
            if(number < 0.0|| number > 1.0)throw new RuntimeException();
            field.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            Partie.NEVROSITE_MARSHALL = number;
            flags[5] = true;
            if(!Arrays.asList(flags).contains(false)) {play.setEnabled(true);}
        } catch (Exception ignored) {
            flags[5] = false;
            field.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            play.setEnabled(false);
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


        //Bouton play
        JButton playButton = new JButton("Play");
        playButton.setEnabled(false);
        playButton.addActionListener(e -> vue.switchToGame(nomsBandits)); //Commence la partie avec les parametres


        JPanel names = new JPanel();
        JTextField nb_joueurs = (JTextField) ((JPanel)constants.getComponent(0)).getComponent(1);
        nb_joueurs.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}
            @Override
            public void focusLost(FocusEvent e) {
                if (!flags[0] && nb_joueurs.getText().isEmpty()) {
                    nb_joueurs.setText("");
                }
                else if(flags[0] && nb_joueurs.getText().isEmpty()) nb_joueurs.setText(String.valueOf(Partie.NB_JOUEURS));

            }
        });
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
                if (text != null && !text.isEmpty()) getNB_JOUEUR(names, nb_joueurs,playButton);
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
                if (!flags[1] && nb_manches.getText().isEmpty()) {
                    nb_manches.setText(">= 3");
                    nb_manches.setForeground(Color.GRAY);
                }
                else if(flags[1] && nb_manches.getText().isEmpty()) nb_manches.setText(String.valueOf(Partie.NB_MANCHES));

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
                if (text != null && !text.isEmpty() &&  !text.equals(">= 3")) getNb_Manches(nb_manches,playButton);

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
                if (!flags[2] && hp.getText().isEmpty()) {
                    hp.setText(">= 2 et <= 8");
                    hp.setForeground(Color.GRAY);
                }
                else if(flags[2] && hp.getText().isEmpty()) hp.setText(String.valueOf(Partie.DEFAULT_HP));
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
                if (text != null && !text.isEmpty() &&  !text.equals(">= 2 et <= 8")) getHP(hp,playButton);

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
                if (!flags[3] && ammo.getText().isEmpty()) {
                    ammo.setText(">= 0 et <= 12");
                    ammo.setForeground(Color.GRAY);
                }
                else if(flags[3] && ammo.getText().isEmpty()) ammo.setText(String.valueOf(Partie.NB_MUNITIONS));

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
                if (text != null && !text.isEmpty() &&  !text.equals(">= 0 et <= 12")) getAmmo(ammo,playButton);

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
                if (!flags[4] && precision.getText().isEmpty()) {
                    precision.setText(">= 0.0 et <= 1.0");
                    precision.setForeground(Color.GRAY);
                }
                else if (flags[4] && precision.getText().isEmpty()) precision.setText(String.valueOf(Partie.DEFAULT_PRECISION));

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
                if (text != null && !text.isEmpty() &&  !text.equals(">= 0.0 et <= 1.0")) getPrecision(precision,playButton);

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
                if (!flags[5] && nev.getText().isEmpty()) {
                    nev.setText(">= 0.0 et <= 1.0");
                    nev.setForeground(Color.GRAY);
                }
                else if(flags[5] && nev.getText().isEmpty()) nev.setText(String.valueOf(Partie.NEVROSITE_MARSHALL));
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
                if (text != null && !text.isEmpty() &&  !text.equals(">= 0.0 et <= 1.0")) getNevrosite(nev,playButton);

            }
        });

        JPanel play = new JPanel();
        play.setPreferredSize(dim2);


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
        Partie.NB_BANDITS_JOUEUR = (Partie.NB_JOUEURS <= 2 ? 2 : 1);
        nomsBandits = new String[Partie.NB_JOUEURS][Partie.NB_BANDITS_JOUEUR];
        for (int i = 0; i <Partie.NB_JOUEURS*Partie.NB_BANDITS_JOUEUR; i++) {
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