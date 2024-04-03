package Controleur;

import Modele.Partie;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controleur implements ActionListener {

    private Partie partie;
    public Controleur(Partie p) { this.partie = p; }

    //needs changing
    public void actionPerformed(ActionEvent e) {
        //partie.avance();
    }
}