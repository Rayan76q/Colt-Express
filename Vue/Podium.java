package Vue;

import Modele.Joueur;
import Modele.Partie;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Podium extends JPanel{
    private final Partie p;
    private final Joueur[] gagnants;
    private final List<Integer> podiumHeight = new LinkedList<>();
    private final Image background = new ImageIcon(Objects.requireNonNull(getClass().getResource("Images/backGroundCommandes.jpg"))).getImage();
    
    Podium(Partie par){
        p = par;
        gagnants = p.getJoueurs();
        Arrays.sort(gagnants, Comparator.reverseOrder());
        this.setPreferredSize(new Dimension(CVue.screenWidth/3 ,CVue.screenWidth/3 ));
        int height = 60;
        podiumHeight.add(height);
        for (int i = 1; i < p.getJoueurs().length; i++) {
            if(gagnants[i].compte_argent() < gagnants[i-1].compte_argent() && height >0) height -=20;
            podiumHeight.add(height);
        }
    }
    
    @Override 
    public void paintComponent(Graphics g){
        int startX = this.getWidth()/7 , startY = 200 , decH = 70;
        g.drawImage(background,0,0,getWidth(),getHeight(),this);
        for (int i = 0; i < p.getJoueurs().length; i++) {
            Color c = Color.black;
            switch (podiumHeight.get(i)){
                case 60: c = new Color(255,215,0);break;
                case 40: c = new Color(113,112,110);break;
                case 20: c = new Color(160,113,85);break;
                default: break;
            }

            int x = startX + (i%4) * VuePlateau.spriteW*2;
            int y = (i>=4 ? 1 :0)*(VuePlateau.spriteH + decH);
            g.setColor(Color.WHITE);
            g.drawString(("J"+(gagnants[i].getId()+1)),x+VuePlateau.spriteW/3 , startY-podiumHeight.get(i) - VuePlateau.spriteH + y-10 );
            g.drawImage((new ImageIcon(Objects.requireNonNull(getClass().getResource(p.getJoueurs()[i].getPionAct().getSprite())))).getImage(),
                     x, startY-podiumHeight.get(i) - VuePlateau.spriteH + y,
                    VuePlateau.spriteW,VuePlateau.spriteH,this);
            g.setColor(c);
            g.fillRect(x, startY-podiumHeight.get(i) + y,VuePlateau.spriteW,podiumHeight.get(i));
            g.setColor(Color.GREEN);
            g.drawString(gagnants[i].compte_argent()+"$",x+VuePlateau.spriteW/3 , startY + y+20 );
        }
    }


}
