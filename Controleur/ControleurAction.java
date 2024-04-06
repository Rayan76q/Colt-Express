package Controleur;

import Modele.Direction;
import Modele.Partie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControleurAction implements ActionListener {

    protected Partie partie;
    protected Direction dirChoisie;
    private JPanel VueBoutons;
    private CardLayout switcher;
    public ControleurAction(Partie p , CardLayout switcher , JPanel j) { this.partie = p; this.switcher=switcher ; this.VueBoutons = j;}

    //needs changing
    public void actionPerformed(ActionEvent e) {
        switcher.next(VueBoutons);
    }
}


