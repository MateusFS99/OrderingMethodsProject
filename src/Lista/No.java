package Lista;

public class No {
    
    private int info;
    private No ant, prox;

    public No() {
        ant = prox = null;
        info = -1;
    }
    
    public No(No ant, No prox, int info) {
        this.ant = ant;
        this.prox = prox;
        this.info = info;
    }

    public No getAnt() {
        return ant;
    }

    public void setAnt(No ant) {
        this.ant = ant;
    }

    public No getProx() {
        return prox;
    }

    public void setProx(No prox) {
        this.prox = prox;
    }

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }
}