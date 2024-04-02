package Modele;

public abstract class Action<T> {
    protected Train train;
    protected T acteur;
    public abstract void executer();
}


class Braquage extends Action{

    public Braquage(Bandit b , Train T){
        train = T;
        acteur = b;
    }

    @Override
    public void executer() {
        ((Bandit) acteur).braque(train);
    }
}

class Deplacement extends Action{
    private Direction dir;

    public Deplacement(Movable b , Train T , Direction d){
        train = T;
        acteur = b;
        dir = d;
    }

    @Override
    public void executer() {
        ((Movable)acteur).move(train, dir);
    }
}

class Tir extends Action{
    private Direction dir ;
    public Tir(Movable b , Train T , Direction d){
        train = T;
        acteur = b;
        dir = d;
    }

    @Override
    public void executer() {
        ((Bandit) acteur).tir(train, dir);
    }
}


class Frappe extends Action{

    public Frappe(Bandit b, Train T){
        train = T;
        acteur = b;
    }

    @Override
    public void executer() {
        ((Bandit) acteur).frappe(train);
    }
}


