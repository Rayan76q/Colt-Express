package Controleur;

import Modele.Partie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ControleurMouvement extends ControleurAction {
    
    public ControleurMouvement(Partie p , CardLayout switcher , JPanel j) {
        super(p,switcher,j);
    }

    //needs changing
    public void actionPerformed(ActionEvent e) {
        partie.setActionChoisie(0);
        super.actionPerformed(e);
    }
}
