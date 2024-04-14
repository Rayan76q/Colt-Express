package Vue;

import Modele.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class VuePlateau extends JPanel implements Observer {

    private Train train;

    //Hauteur et largeur d'un etage de wagon
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
            paintWagon(g, i*WIDTH+Partie.NB_WAGON*dec/2);
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


    private void paintWagon(Graphics g, int x) {
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

    private void drawBandit(Graphics g, Bandit b , int x , int y){
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
            drawBandit(g,b,x+ i* (Personne.spriteW + 5),HEIGHT);
        }
        //Interieur
        for (int i = 0; i < w.getInterieur().size(); i++) {
            Personne p = w.getInterieur().get(i);
            if(p instanceof Bandit){
               drawBandit(g,(Bandit) p,x+i* (Personne.spriteW + 5) , 2*HEIGHT+20);
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
