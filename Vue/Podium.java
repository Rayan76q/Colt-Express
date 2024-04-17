package Vue;

import Modele.Joueur;
import Modele.Partie;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.max;

public class Podium extends JPanel implements Observer{
    Partie p;
    List<Joueur> gagnants;

    private List<Integer> podiumHeight = new LinkedList<>();
    
    Podium(Partie par){
        p = par;
        gagnants = p.joueur_en_tete();
        this.setPreferredSize(new Dimension(CVue.screenWidth/3 ,CVue.screenWidth/3 ));
        for (int i = 0; i < p.getJoueurs().length; i++) {
            podiumHeight.add(max(60-i*20,0));
        }
    }
    
    @Override 
    public void paintComponent(Graphics g){
        int startX = this.getWidth()/7 , startY = 200 , decH = 70;

        for (int i = 0; i < p.getJoueurs().length; i++) {
            int x = startX + (i%4) * VuePlateau.spriteW*2;
            g.drawString(("J"+(gagnants.get(i).getId()+1)),x+VuePlateau.spriteW/3 , startY-podiumHeight.get(i) - VuePlateau.spriteH + (i>=4 ? 1 :0)*(VuePlateau.spriteH + decH)-10 );
            g.drawImage((new ImageIcon(getClass().getResource(p.getJoueurs()[i].getPionAct().getSprite()))).getImage(),
                     x, startY-podiumHeight.get(i) - VuePlateau.spriteH + (i>=4 ? 1 :0)*(VuePlateau.spriteH + decH),
                    VuePlateau.spriteW,VuePlateau.spriteH,this);
            g.fillRect(x, startY-podiumHeight.get(i) + (i>=4 ? 1 :0)*(VuePlateau.spriteH + decH),VuePlateau.spriteW,podiumHeight.get(i));
            g.drawString(gagnants.get(i).compte_argent()+"$",x+VuePlateau.spriteW/3 , startY + (i>=4 ? 1 :0)*(VuePlateau.spriteH + decH)+20 );
        }
    }

    @Override
    public void update() {
        repaint();
    }

    @Override
    public void update(String str) {
        repaint();
    }
}
