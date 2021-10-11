package Lista;

public class ListadeLista {
    
    private Lista inicio;

    public ListadeLista() {}

    public ListadeLista(Lista inicio) {
        this.inicio = inicio;
    }

    public Lista getInicio() {
        return inicio;
    }

    public void setInicio(Lista inicio) {
        this.inicio = inicio;
    }
    
    public void inserirNoIndice(int i, int info) {
        
        Lista aux = inicio;
        
        if (aux == null) {
            
            inicio = new Lista();
            aux = inicio;
        }
        for (int j = 0 ; j < i ; j++) {
            
            if(aux.getProx() == null)
                aux.setProx(new Lista());
            aux = aux.getProx();
        }
        aux.inserirNoFinal(info);
    }
    
    public void pushList(Lista l) {
        
        Lista auxl = this.getInicio();
        No no = auxl.getInicio();
        
        for (No i = l.getInicio() ; i != null && auxl != null ; i = i.getProx()) {
            
            if (no != null) {
                
                i.setInfo(no.getInfo());
                no = no.getProx();
            } 
            else {
                
                auxl = auxl.getProx();
                i = i.getAnt();
                no = auxl.getInicio();
            }  
        }
    }
    
    //******* Métodos de Ordenação *******//
    
    public void insercaoDireta() {
        
        for (Lista i = inicio ; i != null ; i = i.getProx()) 
            if (i.getInicio() != null)
                i.insercaoDireta();
    }
}