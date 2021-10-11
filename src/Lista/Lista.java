package Lista;

public class Lista {
    
    private int length, qtdeNo = 8;
    private No inicio, fim;
    private Lista prox;

    public Lista() {
        inicio = fim = null;
        length = 0;
    }

    public Lista(No inicio, No fim, int length) {
        this.inicio = inicio;
        this.fim = fim;
        this.length = length;
    }

    public No getInicio() {
        return inicio;
    }

    public void setInicio(No inicio) {
        this.inicio = inicio;
    }

    public No getFim() {
        return fim;
    }

    public void setFim(No fim) {
        this.fim = fim;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Lista getProx() {
        return prox;
    }

    public void setProx(Lista prox) {
        this.prox = prox;
    }
    
    public No seekPont(int pos) {
        
        No aux = inicio;
        
        for (int i = 0 ; i < pos ; i++)
            aux = aux.getProx();
          
        return aux;
    }
    
    public void exibe() {
        
        for (No aux = inicio ; aux != null ; aux = aux.getProx())
            System.out.print(aux.getInfo() + " ");
        System.out.println();
    }
    
    public void inserirNoInicio(int info) {
        
        No nova = new No(null,inicio,info);
        
        if (inicio == null)
            inicio = fim = nova;
        else {
            
            inicio.setAnt(nova);
            inicio = nova;
        }
        length++;
    }
    
    public void inserirNoFinal(int info) {
        
        No nova = new No(fim,null,info);
        
        if (fim == null)
            inicio = fim = nova;
        else {
            
            fim.setProx(nova);
            fim = nova;
        }
        length++;
    }
    
    private Lista copiaLista(Lista ori) {
        
        Lista dest = new Lista(new No(null, null, ori.getInicio().getInfo()), new No(null, null, ori.getFim().getInfo()), ori.getLength());
        No no1 = ori.getInicio().getProx(), no2 = dest.getInicio();
        
        for (; no1 != ori.getFim() ; no1 = no1.getProx(), no2 = no2.getProx())
            no2.setProx(new No(no1.getAnt(),no1.getProx(),no1.getInfo()));
        no2.setProx(dest.getFim());
        dest.getFim().setAnt(no2);
        
        return dest;
    }
    
    public void geraListaOrdenada() {
    
        for (int i = 0 ; i < qtdeNo ; i++)
            inserirNoFinal(i);
    }
    
    public void geraListaReversa() {
        
        for (int i = qtdeNo ; i > 0 ; i--)
            inserirNoFinal(i);
    }
    
    public void geraListaRandomica() {
    
        for (int i = 0 ; i < qtdeNo ; i++)
            inserirNoFinal((int)(Math.random() * 1000));
    }
    
    //******* Métodos de Busca *******//
    
    private No buscaExaustiva(int info) {
        
        No aux = inicio;
        
        for ( ;aux != null && aux.getInfo() != info ; aux = aux.getProx()) {}
        
        return aux;
    }
    
    private No buscaExaustivaSentinela(int info) {
        
        No aux = inicio, sentinela = new No(fim, null, info);
        
        fim.setProx(sentinela);
        for ( ; aux.getInfo() != info ; aux = aux.getProx()) {}
        fim.setProx(null);
        if (aux.getInfo() != info)
            return aux;
        
        return null;
    }
    
    private No buscaSequencial(int info) {
        
        No aux = inicio;
        
        for ( ; aux != fim && info > aux.getInfo() ; aux = aux.getProx()) {}
        if (aux != fim && info == aux.getInfo())
            return aux;
        
        return null;
    }
    
    private int buscaBinaria(int chave, int tl) {
        
        int inicio = 0, fim = tl - 1, meio = fim / 2;
        No aux;
        
        aux = seekPont(meio);
        while(inicio < fim && chave != aux.getInfo()) {
            
            if (chave < aux.getInfo())
                fim = meio - 1;
            else
                inicio = meio + 1;
            meio = (inicio + fim) / 2;
            aux = seekPont(meio);
        }
        if (chave > aux.getInfo())
            return meio + 1;
        
        return meio;
    }
    
    private int buscaMaior() {
        
        int maior;
        No no = inicio;
        
        maior = no.getInfo();
        for (; no != null ; no = no.getProx())
            if (no.getInfo() > maior)
                maior = no.getInfo();
        
        return maior;
    }
            
    //******* Métodos de Ordenação *******//
    
    public void insercaoDireta() {
        
        int aux;
        No no;
        
        for (No i = inicio.getProx() ; i != null ; i = i.getProx()) {
            
            aux = i.getInfo();
            for (no = i ; no != inicio && aux < no.getAnt().getInfo() ; no = no.getAnt())
                no.setInfo(no.getAnt().getInfo());
            no.setInfo(aux);
        }
    }
    
    public void insercaoBinaria() {
        
        int pos, aux;
        
        for (int i = 1 ; i < length ; i++) {
            
            aux = seekPont(i).getInfo();
            pos = buscaBinaria(aux, i);
            for (int j = i ; j > pos ; j--)
                seekPont(j).setInfo(seekPont(j-1).getInfo());
            seekPont(pos).setInfo(aux);
        }
    }
    
    public void selecaoDireta() {
        
        int menor;
        No posmenor;
        
        for (No i = inicio ; i.getProx() != null ; i = i.getProx()) {
            
            posmenor = i;
            menor = i.getInfo();
            for (No j = i.getProx() ; j != null; j = j.getProx()) {
                
                if (j.getInfo() < menor) {
                    
                    posmenor = j;
                    menor = j.getInfo();
                }
            }
            posmenor.setInfo(i.getInfo());
            i.setInfo(menor);
        }
    }
    
    public void bubbleSort() {
        
        int aux;
        
        for (int tl = length ; tl > 0 ; tl--) {
            
            for (No no = inicio ; no != fim ; no = no.getProx()) {
                
                if (no.getInfo() > no.getProx().getInfo()) {
                    
                    aux = no.getInfo();
                    no.setInfo(no.getProx().getInfo());
                    no.getProx().setInfo(aux);
                }
            }
        }
    }
    
    public void shakeSort() {
        
        int aux;
        No no;
        
        for (int inicio = 0, fim = length - 1 ; inicio < fim ;) {
            
            for (int i = inicio ; i < fim ; i++) {
                
                no = seekPont(i);
                if (no.getInfo() > no.getProx().getInfo()) {
                    
                    aux = no.getInfo();
                    no.setInfo(no.getProx().getInfo());
                    no.getProx().setInfo(aux);
                }
            }
            fim--;
            for (int i = fim ; i > inicio ; i--) {
                
                no = seekPont(i);
                if (no.getInfo() < no.getAnt().getInfo()) {
                    
                    aux = no.getInfo();
                    no.setInfo(no.getAnt().getInfo());
                    no.getAnt().setInfo(aux);
                }
            }
            inicio++; 
        }
    }
    
    public void shellSort() {
        
        int aux;
        No no1, no2;
        
        for (int dist = 4 ; dist > 0 ; dist /= 2) {
            
            for (int i = 0; i < dist; i++) {
                
                for (int j = i ; j + dist < length; j += dist) {
                    
                    no1 = seekPont(j);
                    no2 = seekPont(j+dist);
                    if(no1.getInfo() > no2.getInfo()){
                        
                        aux = no1.getInfo();
                        no1.setInfo(no2.getInfo());
                        no2.setInfo(aux);
                        if (j - dist >= i) {
                            
                            no2 = seekPont(j-dist);
                            for (int k = j ; k - dist >= i && no1.getInfo() < no2.getInfo() ;) {
                                
                                seekPont(k).setInfo(no2.getInfo());
                                seekPont(k-dist).setInfo(no1.getInfo());
                                k -= dist;
                                if (k - dist >= i)
                                    no2 = seekPont(k-dist);
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void heapSort() {
        
        int fe, fd, maiorf, aux;
        No no1, no2;
        
        for (int tl = length ; tl > 1 ; tl--) {
            
            for (int pai = tl / 2 - 1 ; pai >= 0 ; pai--) {
                
                fe = 2 * pai + 1;
                fd = fe + 1;
                maiorf = fe;
                no1 = seekPont(fd);
                no2 = seekPont(fe);
                if (fd < tl && no1.getInfo() > no2.getInfo())
                    maiorf = fd;
                no1 = seekPont(maiorf);
                no2 = seekPont(pai);
                if (no1.getInfo() > no2.getInfo()) {
                    
                    aux = no1.getInfo();
                    no1.setInfo(no2.getInfo());
                    no2.setInfo(aux);
                }
            }
            no1 = seekPont(0);
            no2 = seekPont(tl-1);
            aux = no1.getInfo();
            no1.setInfo(no2.getInfo());
            no2.setInfo(aux);
        }
    }
    
    public void quickComPivo() {
        
        quickComPivo(0, length-1);
    }
    
    private void quickComPivo(int ini, int fim) {
        
        int i = ini, j = fim, pivo = (i + j) / 2, aux;
        No no1, no2, nopivo = seekPont(pivo);
        
        while (i <= j) {
            
            no1 = seekPont(i);
            no2 = seekPont(j);
            while (i < fim && no1.getInfo() < no2.getInfo())
                no1 = seekPont(++i);
            while (j > ini && no2.getInfo() > no1.getInfo())
                no2 = seekPont(--j);
            if (i <= j) {
                
                no1 = seekPont(i);
                no2 = seekPont(j);
                aux = no1.getInfo();
                no1.setInfo(no2.getInfo());
                no2.setInfo(aux);
                i++;
                j--;
            }
        }
        if(ini < j)
            quickComPivo(ini,j);
        if(i < fim)
            quickComPivo(i,fim);
    }
    
    public void quickSemPivo() {
        
        quickSemPivo(0, length-1);
    }
    
    private void quickSemPivo(int ini, int fim) {
        
        int i = ini, j = fim, aux;
        boolean flag = true;
        No no1, no2;
        
        while (i < j) {
            
            no1 = seekPont(i);
            no2 = seekPont(j);
            if (flag)
                while (i < fim && no1.getInfo() < no2.getInfo())
                    no1 = seekPont(++i);
            else
                while (j > ini && no2.getInfo() > no1.getInfo())
                    no2 = seekPont(--j);
            flag = !flag;
            no1 = seekPont(i);
            no2 = seekPont(j);
            aux = no1.getInfo();
            no1.setInfo(no2.getInfo());
            no2.setInfo(aux);
        }
        if (i - 1 > ini)
            quickSemPivo(ini, i-1);
        if (j + 1 < fim)
            quickSemPivo(j+1, fim);
    }
    
    public void mergeSort1() {
        
        Lista l1 = new Lista();
        Lista l2 = new Lista();
        
        for (int seq = 1 ; seq < length ; seq *= 2) {
            
            particao(l1,l2);
            fusao(l1,l2,seq);
        }
    }
    
    private void particao(Lista l1, Lista l2) {
        
        No no;
        
        for (int i = 0 ; i < length / 2 ; i++) {
                
            no = seekPont(i);
            l1.inserirNoFinal(no.getInfo());
            no = seekPont(i+length/2);
            l2.inserirNoFinal(no.getInfo());
        }
    }
    
    private void fusao(Lista l1, Lista l2, int seq) {
        
        int i = 0, j = 0, auxseq = seq;
        No no1, no2;
        
        for (No aux = inicio ; aux != null ;) {
            
            while (i < auxseq && j < auxseq && i < l1.getLength() && j < l2.getLength()) {
                
                no1 = l1.seekPont(i);
                no2 = l2.seekPont(j);
                if (no1.getInfo() < no2.getInfo()) {
                    
                    aux.setInfo(no1.getInfo());
                    i++;
                }  
                else {
                    
                    aux.setInfo(no2.getInfo());
                    j++;
                }
                aux = aux.getProx();
            }
            while (i < auxseq && i < l1.getLength()) {
                
                no1 = l1.seekPont(i);
                aux.setInfo(no1.getInfo());
                i++;
                aux = aux.getProx();
            }
            while (j < auxseq && j < l2.getLength()) {
                
                no2 = l2.seekPont(j);
                aux.setInfo(no2.getInfo());
                j++; 
                aux = aux.getProx();
            }
            auxseq += seq;
        }
    }
    
    public void mergeSort2() {
        
        Lista l = new Lista();
        
        mergeSort2(l, 0, length);
    }
    
    private void mergeSort2(Lista l, int esq, int dir) {
        
        int meio;
        
        if (esq < dir) {
            
            meio = (esq + dir) / 2;
            mergeSort2(l,esq,meio);
            mergeSort2(l,meio+1,dir);
            fusao(l,esq,meio,meio+1,dir);
        }
    }
    
    private void fusao(Lista l, int ini1, int fim1, int ini2, int fim2) {
        
        
    }
    
    public void countingSort() {
        
        int index[] = new int[length+1];
        Lista places = copiaLista(this);
        No no1, no2;
        
        for (no1 = inicio ; no1 != null ; no1 = no1.getProx())
            index[no1.getInfo()]++;
        for (int i = 1 ; i < index.length ; i++)
            index[i] += index[i-1];
        for (no2 = places.getInicio() ; no2 != null ; no2 = no2.getProx())
            seekPont(--index[no2.getInfo()]).setInfo(no2.getInfo());
    }
    
    public void bucketSort() {
        
        int div = 10;
        ListadeLista buckets = new ListadeLista();
        
        for (No i = inicio ; i != null ; i = i.getProx()) 
            buckets.inserirNoIndice(i.getInfo()/div, i.getInfo());
        
        buckets.insercaoDireta();
        buckets.pushList(this);
    }
    
    public void radixSort() {
        
        int maior = buscaMaior();
        
        for (int i = 1 ; maior / i > 0 ; i *= 10)
            countingSort(i);
    }
    
    private void countingSort(int div) {
        
        int index[] = new int[10];
        Lista places = copiaLista(this);
        No no1, no2;
        
        for (no1 = inicio ; no1 != null ; no1 = no1.getProx())
            index[(no1.getInfo()/div)%10]++;
        for (int i = 1 ; i > index.length ; i++)
            index[i] += index[i-1];
        for (no2 = places.getInicio() ; no2 != null ; no2 = no2.getProx())
            seekPont(--index[(no2.getInfo()/div)%10]).setInfo(no2.getInfo());
    }
    
    public void combSort() {
        
        int aux;
        No no1, no2;
        
        for (int gap = (int)(length / 1.3) ; gap > 0 ; gap /= 1.3) {
            
            for (int i = 0 ; i + gap < length ; i++) {
                
                no1 = seekPont(i);
                no2 = seekPont(i+gap);
                if (no1.getInfo() > no2.getInfo()) {
                    
                    aux = no1.getInfo();
                    no1.setInfo(no2.getInfo());
                    no2.setInfo(aux);
                }
            }
        }
    }
    
    public void gnomeSort() {
        
        int aux;
        boolean flag = true;
        No no1, no2;
        
        for (int i = 0 ; i < length - 1 ; i++) {
            
            no1 = seekPont(i);
            no2 = no1.getProx();
            if(no2.getInfo() < no1.getInfo()) {
                
                aux = no1.getInfo();
                no1.setInfo(no2.getInfo());
                no2.setInfo(aux);
                for (int j = i - 1 ; j >= 0 && flag ; j--) {
                    
                    no1 = seekPont(j);
                    no2 = no1.getProx();
                    if (no1.getInfo() > no2.getInfo()) {
                        
                        aux = no1.getInfo();
                        no1.setInfo(no2.getInfo());
                        no2.setInfo(aux);
                    }
                    else
                        flag = false;
                }
                flag = true;
            }
        }
    }
    
    public void timSort() {
        
    }
}