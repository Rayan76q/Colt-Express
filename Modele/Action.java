package Modele;

public abstract class Action<T> {
    protected Train train;
    protected T acteur;

    Action(T act , Train t){
        acteur = act;
        train = t;
    }

    public abstract void executer();
}


class Braquage extends Action{

    public Braquage(Bandit b , Train T){
        super(b,T);
    }

    @Override
    public void executer() {
        ((Bandit) acteur).braque(train);
    }

    @Override
    public String toString(){
        return "Braque";
    }
}


abstract class ActionDir extends Action{
    protected final Direction dir;
    ActionDir(Object act, Train t,Direction d) {
        super(act, t);
        dir = d;
    }
    public Direction getDir(){return dir;}
}
class Deplacement extends ActionDir{

    public Deplacement(Movable b , Train T , Direction d){
        super(b,T,d);
    }

    @Override
    public void executer() {
        ((Movable)acteur).move(train, dir);
    }

    @Override
    public String toString() {return "Deplacement " + dir;}
}

class Tir extends ActionDir{

    public Tir(Movable b , Train T , Direction d){
        super(b,T,d);
    }

    @Override
    public void executer() {
        ((Bandit) acteur).tir(train, dir);
    }

    @Override
    public String toString() {return "Tir " + dir;}
}


class Frappe extends Action{

    public Frappe(Bandit b, Train T){
        super(b,T);
    }

    @Override
    public void executer() {
        ((Bandit) acteur).frappe(train);
    }

    @Override
    public String toString() {return "Frappe";}
}


