package Arquivo;

public class ListaRegistro {
    
    private int comp, mov;
    private Registro inicio, fim;

    public ListaRegistro() {}

    public ListaRegistro(Registro inicio, Registro fim) {
        this.inicio = inicio;
        this.fim = fim;
    }

    public Registro getInicio() {
        return inicio;
    }

    public void setInicio(Registro inicio) {
        this.inicio = inicio;
    }

    public Registro getFim() {
        return fim;
    }

    public void setFim(Registro fim) {
        this.fim = fim;
    }

    public int getComp() {
        return comp;
    }

    public void setComp(int comp) {
        this.comp = comp;
    }

    public int getMov() {
        return mov;
    }

    public void setMov(int mov) {
        this.mov = mov;
    }
    
    public void insereNoFinal(Registro reg) {
        
        if (getInicio() == null)
            inicio = fim = new Registro(reg.getNumero());
        else {
            
            fim.setProx(new Registro(reg.getNumero()));
            fim.getProx().setAnt(fim);
            fim = fim.getProx();
        }
    }
    
    public void pushArq(Arquivo arquivo) {
        
        for (Registro reg = inicio ; reg != null ; reg = reg.getProx()) 
            reg.gravaNoArq(arquivo.getArquivo());
    }
    
    //******* Métodos de Ordenação *******//
    
    public void insercaoDireta() {
        
        int aux;
        Registro reg, reg2;
        
        for (reg = inicio ; reg != fim ; reg = reg.getProx()) {
            
            if (reg.getNumero() > reg.getProx().getNumero()) {
                
                aux = reg.getNumero();
                reg.setNumero(reg.getProx().getNumero());
                reg.getProx().setNumero(aux);
                for(reg2 = reg ; reg2 != inicio && reg2.getNumero() < reg2.getAnt().getNumero() ; reg2 = reg2.getAnt()) {
                    
                    aux = reg2.getNumero();
                    reg2.setNumero(reg2.getAnt().getNumero());
                    reg2.getAnt().setNumero(aux);
                }
            }
        }
    }
}