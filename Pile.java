import java.util.LinkedList;

public class Pile {

    private LinkedList<Action> cartes;

    private LinkedList<Action> faceCacheYugi ;

    public Pile() {
        cartes = new LinkedList<>();

        faceCacheYugi = new LinkedList<>();
    }

    public void ajouterCarte(Action c) {
        cartes.add(c);
    }

    public void  ajouterCarteFaceCache(Action c ){
        faceCacheYugi.add(c);
    }

    public void retirerCarte() {
        if (!cartes.isEmpty()) {
            cartes.removeLast();
        } else {
            System.out.println("La pile est déjà vide !");
        }
    }

    public void retirerCarte() {
        if (!faceCacheYugi.isEmpty()) {
           faceCacheYugi.removeLast();
        } else {
            System.out.println("La pile cache  est déjà vide !");
        }
    }

    public boolean estVide() {
        return cartes.isEmpty();
    }

    public boolean estVideFaceCache() {
        return faceCacheYugi.isEmpty();
    }


}
