package Controleur;

import Modele.Partie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ControleurTir extends ControleurAction {

    public ControleurTir(Partie p , CardLayout switcher , JPanel j) {
        super(p,switcher,j);
    }

    //needs changing
    public void actionPerformed(ActionEvent e) {
        partie.setActionChoisie(1);
        super.actionPerformed(e);
    }
}
