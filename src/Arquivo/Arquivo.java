package Arquivo;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Arquivo {
    
    private int comp, mov, qtdeReg = 1024; 
    private String nomearquivo;
    private RandomAccessFile arquivo;
    
    public Arquivo(String nomearquivo) {
        
        try {
            
            arquivo = new RandomAccessFile(nomearquivo, "rw");
            this.nomearquivo = nomearquivo;
        } catch (IOException e) { 
            System.out.println(e);
        }
    }
    
    public void initComp() {
        comp = 0;
    }
    
    public void initMov() {
        mov = 0;
    }
    
    public int getComp() {
        return comp;
    }
    
    public int getMov() {
        return mov;
    }

    public String getNomearquivo() {
        return nomearquivo;
    }

    public void setNomearquivo(String nomearquivo) {
        this.nomearquivo = nomearquivo;
    }

    public RandomAccessFile getArquivo() {
        return arquivo;
    }

    public void setArquivo(RandomAccessFile arquivo) {
        this.arquivo = arquivo;
    }
    
    public int filesize() {
    
        try {
            return (int)arquivo.length() / Registro.length();
        } catch (IOException ex) {
            return 0;
        }
    }
    
    public void truncate(long pos) {
    
        try {
            arquivo.setLength(pos * Registro.length());
        } catch (IOException e) { 
            System.out.println(e);
        }
    }
    
    public boolean eof() {
        
        boolean retorno = false;
        
        try {
            if (arquivo.getFilePointer() == arquivo.length())
                retorno = true;                               
        } catch (IOException e) { 
            System.out.println(e);
        }
        
        return (retorno);
    }
    
    public void seekArq(int pos) {
        
        try {
            arquivo.seek(pos * Registro.length());
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public void exibirArq() {
        
        Registro aux = new Registro();
        
        seekArq(0);
        while (!eof()) {
            
            aux.leDoArq(arquivo);
            System.out.print(aux.getNumero() + " ");
        }
        System.out.println();
    }

    public void inserirRegNoFinal(Registro reg) {
        
        seekArq(filesize());
        reg.gravaNoArq(arquivo);
    }
    
    public void copiaArquivo(Arquivo arqOrigem) {
        
        Registro reg = new Registro();
        
        seekArq(0);
        arqOrigem.seekArq(0);
        while (!arqOrigem.eof()) {
            
            reg.leDoArq(arqOrigem.getArquivo());
            reg.gravaNoArq(this.getArquivo());
            mov++;
        }
    }
    
    public void geraArquivoOrdenado() {
    
        for (int i = 0 ; i < qtdeReg ; i++)
            inserirRegNoFinal(new Registro(i));
    }
    
    public void geraArquivoReverso() {
        
        for (int i = qtdeReg ; i > 0 ; i--)
            inserirRegNoFinal(new Registro(i));
    }
    
    public void geraArquivoRandomico() {
    
        for (int i = 0 ; i < qtdeReg ; i++)
            inserirRegNoFinal(new Registro((int)(Math.random() * 1000)));
    }
    
    //******* Métodos de Busca *******//
    
    private int buscaBinaria(int chave, int tl) {
        
        int inicio = 0, fim = tl - 1, meio = fim / 2;
        Registro aux = new Registro();
        
        seekArq(meio);
        aux.leDoArq(arquivo);
        while(inicio < fim && chave != aux.getNumero()) {
            
            if (chave < aux.getNumero())
                fim = meio - 1;
            else
                inicio = meio + 1;
            comp++;
            meio = (inicio + fim) / 2;
            seekArq(meio);
            aux.leDoArq(arquivo);
            comp++;
        }
        comp += 2;
        if (chave > aux.getNumero())
            return meio + 1;
        
        return meio;
    }
    
    private int buscaMaior() {
        
        int maior;
        Registro aux = new Registro();
        
        seekArq(0);
        aux.leDoArq(arquivo);
        maior = aux.getNumero();
        while (!eof()) {
            
            aux.leDoArq(arquivo);
            if (aux.getNumero() > maior)
                maior = aux.getNumero();
            comp++;
        }
        
        return maior;
    }
    
    //******* Métodos de Ordenação *******//
    
    public void insercaoDireta() {
        
        int pos, tl = filesize();
        Registro reg1 = new Registro();
        Registro reg2 = new Registro();
        
        for (int i = 1 ; i < tl ; i++) {
            
            pos = i;
            seekArq(pos-1);
            reg1.leDoArq(arquivo);
            reg2.leDoArq(arquivo);
            while (pos > 0 && reg2.getNumero() < reg1.getNumero()) {
                
                seekArq(pos--);
                reg1.gravaNoArq(arquivo);
                mov++;
                if (pos > 0) {
                    
                    seekArq(pos-1);
                    reg1.leDoArq(arquivo);
                }
                comp++;
            }
            comp++;
            seekArq(pos);
            reg2.gravaNoArq(arquivo);
            mov++;
        }
    }
    
    public void insercaoBinaria() {
        
        int tl = filesize(), pos;
        Registro reg1 = new Registro();
        Registro reg2 = new Registro();
        
        for (int i = 1 ; i < tl ; i++) {
            
            seekArq(i);
            reg1.leDoArq(arquivo);
            pos = buscaBinaria(reg1.getNumero(), i-1);
            for (int j = i ; j > pos ; j--) {
                
                seekArq(j-1);
                reg2.leDoArq(arquivo);
                reg2.gravaNoArq(arquivo);
                mov++;
            }
            seekArq(pos);
            reg1.gravaNoArq(arquivo);
            mov++;
        }
    }
    
    public void selecaoDireta() {
        
        int tl = filesize(), posmenor, menor;
        Registro reg1 = new Registro();
        Registro reg2 = new Registro();
        
        for (int i = 0 ; i < tl - 1 ; i++) {
            
            posmenor = i;
            seekArq(i);
            reg1.leDoArq(arquivo);
            menor = reg1.getNumero();
            for (int j = i + 1 ; j < tl ; j++) {
                
                seekArq(j);
                reg1.leDoArq(arquivo);
                if (reg1.getNumero() < menor) {
                    
                    posmenor = j;
                    menor = reg1.getNumero();
                }
                comp++;
            }
            seekArq(posmenor);
            reg1.leDoArq(arquivo);
            seekArq(i);
            reg2.leDoArq(arquivo);
            seekArq(posmenor);
            reg2.gravaNoArq(arquivo);
            seekArq(i);
            reg1.gravaNoArq(arquivo);
            mov += 2;
        }
    }
    
    public void bubbleSort() {
        
        Registro reg1 = new Registro();
        Registro reg2 = new Registro();
        
        for (int tl = filesize() ; tl > 1 ; tl--) {
            
            for (int i = 0 ; i < tl - 1 ; i++) {
                
                seekArq(i);
                reg1.leDoArq(arquivo);
                reg2.leDoArq(arquivo);
                if (reg1.getNumero() > reg2.getNumero()) {
                    
                    seekArq(i);
                    reg2.gravaNoArq(arquivo);
                    reg1.gravaNoArq(arquivo);
                    mov += 2;
                }
                comp++;
            }
        }
    }
    
    public void shakeSort() {
        
        Registro reg1 = new Registro();
        Registro reg2 = new Registro();
        
        for (int inicio = 0, fim = filesize() - 1 ; inicio < fim ;) {
            
            for (int i = inicio ; i < fim ; i++) {
                
                seekArq(i);
                reg1.leDoArq(arquivo);
                reg2.leDoArq(arquivo);
                if (reg1.getNumero() > reg2.getNumero()) {
                    
                    seekArq(i);
                    reg2.gravaNoArq(arquivo);
                    reg1.gravaNoArq(arquivo);
                    mov += 2;
                }
                comp++;
            }
            fim--;
            for (int i = fim + 1 ; i > inicio ; i--) {
                
                seekArq(i-1);
                reg2.leDoArq(arquivo);
                reg1.leDoArq(arquivo);
                if (reg1.getNumero() < reg2.getNumero()) {
                    
                    seekArq(i-1);
                    reg1.gravaNoArq(arquivo);
                    reg2.gravaNoArq(arquivo);
                    mov += 2;
                }
                comp++;
            }
            inicio++; 
        }
    }
    
    public void shellSort() {
        
        int tl = filesize(), k;
        Registro reg1 = new Registro();
        Registro reg2 = new Registro();
        
        for (int dist = 4 ; dist > 0 ; dist /= 2) {
            
            for (int i = 0; i < dist; i++) {
                
                for (int j = i ; j + dist < tl; j += dist) {
                    
                    seekArq(j);
                    reg1.leDoArq(arquivo);
                    seekArq(j+dist);
                    reg2.leDoArq(arquivo);
                    if (reg1.getNumero() > reg2.getNumero()){
                        
                        seekArq(j);
                        reg2.gravaNoArq(arquivo);
                        seekArq(j+dist);
                        reg1.gravaNoArq(arquivo);
                        mov += 2;
                        k = j;
                        if (k - dist >= i) {
                            
                            seekArq(k);
                            reg1.leDoArq(arquivo);
                            seekArq(k-dist);
                            reg2.leDoArq(arquivo);
                            while (k - dist >= i && reg1.getNumero() < reg2.getNumero()) {

                                seekArq(k);
                                reg2.gravaNoArq(arquivo);
                                seekArq(k-dist);
                                reg1.gravaNoArq(arquivo);
                                mov += 2;
                                k -= dist;
                                if (k - dist >= i) {

                                    seekArq(k);
                                    reg2.leDoArq(arquivo);
                                    seekArq(k-dist);
                                    reg2.leDoArq(arquivo);
                                }
                                comp++;
                            }
                            comp++;
                        }
                    }
                    comp++;
                }
            }
        }
    }
    
    public void heapSort() {
        
        int fe, fd, maiorf;
        Registro reg1 = new Registro();
        Registro reg2 = new Registro();
        
        for (int tl = filesize() ; tl > 1 ; tl--) {
            
            for (int pai = tl / 2 - 1 ; pai >= 0 ; pai--) {
                
                fe = 2 * pai + 1;
                fd = fe + 1;
                maiorf = fe;
                seekArq(fd);
                reg1.leDoArq(arquivo);
                seekArq(fe);
                reg2.leDoArq(arquivo);
                if (fd < tl && reg1.getNumero() > reg2.getNumero())
                    maiorf = fd;
                comp++;
                seekArq(maiorf);
                reg1.leDoArq(arquivo);
                seekArq(pai);
                reg2.leDoArq(arquivo);
                if (reg1.getNumero() > reg2.getNumero()) {
                    
                    seekArq(pai);
                    reg1.gravaNoArq(arquivo);
                    seekArq(maiorf);
                    reg2.gravaNoArq(arquivo);
                    mov += 2;
                }
                comp++;
            }
            seekArq(0);
            reg1.leDoArq(arquivo);
            seekArq(tl-1);
            reg2.leDoArq(arquivo);
            seekArq(0);
            reg2.gravaNoArq(arquivo);
            seekArq(tl-1);
            reg1.gravaNoArq(arquivo);
            mov += 2;
        }
    }
    
    public void quickComPivo() {
        
        quickComPivo(0, filesize()-1);
    }
    
    private void quickComPivo(int ini, int fim) {
        
        int i = ini, j = fim, pivo = (i + j) / 2;
        Registro reg1 = new Registro();
        Registro reg2 = new Registro();
        Registro regpivo = new Registro();
        
        seekArq(pivo);
        regpivo.leDoArq(arquivo);
        while (i <= j) {
            
            seekArq(i);
            reg1.leDoArq(arquivo);
            seekArq(j);
            reg2.leDoArq(arquivo);
            while (i < fim && reg1.getNumero() < regpivo.getNumero()) {
                
                seekArq(++i);
                reg1.leDoArq(arquivo);
                comp++;
            }
            comp++;
            while (j > ini && reg2.getNumero() > regpivo.getNumero()) {
                
                seekArq(--j);
                reg2.leDoArq(arquivo);
                comp++;
            }
            comp++;
            if (i <= j) {
                
                if (i < j){
                    
                    seekArq(i);
                    reg2.gravaNoArq(arquivo);
                    seekArq(j);
                    reg1.gravaNoArq(arquivo);
                    mov += 2;
                }
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
        
        quickSemPivo(0, filesize()-1);
    }
    
    private void quickSemPivo(int ini, int fim) {
        
        int i = ini, j = fim;
        boolean flag = true;
        Registro reg1 = new Registro();
        Registro reg2 = new Registro();
        
        while (i < j) {
            
            seekArq(i);
            reg1.leDoArq(arquivo);
            seekArq(j);
            reg2.leDoArq(arquivo);
            if (flag) {
                
                while (i < j && reg1.getNumero() < reg2.getNumero()) {
                    
                    seekArq(++i);
                    reg1.leDoArq(arquivo);
                    comp++;
                }
                comp++;
            }
            else {
                
                while (i < j && reg1.getNumero() < reg2.getNumero()) {
                    
                    seekArq(--j);
                    reg1.leDoArq(arquivo);
                    comp++;
                }
                comp++;
            }
            flag = !flag;
            if (i < j) {
                
                seekArq(i);
                reg2.gravaNoArq(arquivo);
                seekArq(j);
                reg1.gravaNoArq(arquivo);
                mov += 2;
            }
        }
        if (i - 1 > ini)
            quickSemPivo(ini, i-1);
        if (j + 1 < fim)
            quickSemPivo(j+1, fim);
    }
    
    public void mergeSort1() {
        
        int tl = filesize();
        Arquivo arq1 = new Arquivo("arquivosaux\\particao1.txt");
        Arquivo arq2 = new Arquivo("arquivosaux\\particao2.txt");
        
        for (int seq = 1 ; seq < tl ; seq *= 2) {
            
            particao(arq1,arq2);
            fusao(arq1,arq2,seq);
        }
    }
    
    private void particao(Arquivo arq1, Arquivo arq2) {
        
        int tl = filesize();
        Registro reg = new Registro();
        
        arq1.seekArq(0);
        arq2.seekArq(0);
        
        for (int i = 0 ; i < tl / 2 ; i++) {
                
            seekArq(i);
            reg.leDoArq(arquivo);
            reg.gravaNoArq(arq1.getArquivo()); 
            mov++;
            seekArq(i+tl/2);
            reg.leDoArq(arquivo);
            reg.gravaNoArq(arq2.getArquivo());
            mov++;
        }
    }
    
    private void fusao(Arquivo arq1, Arquivo arq2, int seq) {
        
        int i = 0, j = 0, auxseq = seq, tl = filesize();
        Registro reg1 = new Registro();
        Registro reg2 = new Registro();
        
        for (int k = 0 ; k < tl ;) {
            
            seekArq(k);
            while (i < auxseq && j < auxseq && i < tl && j < tl) {
                
                arq1.seekArq(i);
                reg1.leDoArq(arq1.getArquivo());
                arq2.seekArq(j);
                reg2.leDoArq(arq2.getArquivo());
                if (reg1.getNumero() < reg2.getNumero()) {
                    
                    reg1.gravaNoArq(arquivo);
                    mov++;
                    i++;
                }  
                else {
                    
                    reg2.gravaNoArq(arquivo);
                    mov++;
                    j++;
                }
                comp++;
                k++; 
            }
            while (i < auxseq && i < tl / 2) {
                
                arq1.seekArq(i);
                reg1.leDoArq(arq1.getArquivo());
                reg1.gravaNoArq(arquivo);
                mov++;
                i++;
                k++;
            }
            while (j < auxseq && j < tl) {
                
                arq2.seekArq(j);
                reg2.leDoArq(arq2.getArquivo());
                reg2.gravaNoArq(arquivo);
                mov++;
                j++; 
                k++;
            }
            auxseq += seq;
        }
    }
    
    public void mergeSort2() {
        
        Arquivo arq = new Arquivo("arquivosaux\\particao.txt");
        
        mergeSort2(arq,0,filesize()-1);
    }
    
    private void mergeSort2(Arquivo arq, int esq, int dir) {
        
        int meio;
        
        if (esq < dir) {
            
            meio = (esq + dir) / 2;
            mergeSort2(arq,esq,meio);
            mergeSort2(arq,meio+1,dir);
            fusao(arq,esq,meio,meio+1,dir);
        }
    }
    
    private void fusao(Arquivo arq, int ini1, int fim1, int ini2, int fim2) {
        
        int i = ini1, j = ini2, k = 0, tl = filesize();
        Registro reg1 = new Registro();
        Registro reg2 = new Registro();

        arq.seekArq(0);
        while (i <= fim1 && j <= fim2) {
            
            seekArq(i);
            reg1.leDoArq(arquivo);
            seekArq(j);
            reg2.leDoArq(arquivo);
            if (reg1.getNumero() < reg2.getNumero()) {
                
                reg1.gravaNoArq(arq.getArquivo());
                mov++;
                i++;
            }
            else {
                
                reg2.gravaNoArq(arq.getArquivo());
                mov++;
                j++;
            }
            comp++;
            k++;
        }
        while (i <= fim1 && i < tl) {
            
            reg1.gravaNoArq(arq.getArquivo());
            mov++;
            i++;
            k++;
            seekArq(i);
            reg1.leDoArq(arquivo);
        }
        while (j <= fim2 && j < tl) {
            
            reg2.gravaNoArq(arq.getArquivo());
            mov++;
            j++;
            k++;
            seekArq(j);
            reg2.leDoArq(arquivo);
        }
        seekArq(ini1);
        arq.seekArq(0);
        for (i = 0 ; i < k ; i++) {
            
            reg1.leDoArq(arq.getArquivo());
            reg1.gravaNoArq(arquivo);
            mov++;
        }
    }
    
    public void countingSort() {
        
        int index[] = new int[1034];
        Arquivo places = new Arquivo("arquivosaux\\places.txt");
        Registro reg = new Registro();
        
        places.copiaArquivo(this);
        seekArq(0);
        while (!eof()) {
            
            reg.leDoArq(arquivo);
            index[reg.getNumero()]++;
        }
        for (int i = 1 ; i < index.length ; i++)
            index[i] += index[i-1];
        places.seekArq(0);
        while (!places.eof()) {
            
            reg.leDoArq(places.getArquivo());
            seekArq(--index[reg.getNumero()]);
            reg.gravaNoArq(arquivo);
            mov++;
        }
    }
    
    public void bucketSort() {
        
        int div = 10;
        Registro reg = new Registro();
        ListaRegistro[] buckets = new ListaRegistro[qtdeReg];
        
        for (int i = 0 ; i < buckets.length ; i++) 
            buckets[i] = new ListaRegistro();
        seekArq(0);
        while (!eof()) {
            
            reg.leDoArq(arquivo);
            if (reg.getNumero() / div < qtdeReg)
                buckets[(int)(reg.getNumero() / div)].insereNoFinal(reg);
            else
                buckets[qtdeReg-1].insereNoFinal(reg);
            comp++;
        }
        seekArq(0);
        for (int i = 0 ; i < qtdeReg ; i++) {
            
            if (buckets[i].getInicio()!= null) {
                
                buckets[i].setMov(mov);
                buckets[i].setComp(comp);
                buckets[i].insercaoDireta();
                buckets[i].pushArq(this);
                comp = buckets[i].getComp();
                mov = buckets[i].getMov();
            }
        }
    }
    
    public void radixSort() {
        
        int maior = buscaMaior();
        
        for (int i = 1 ; maior / i > 0 ; i *= 10)
            countingSort(i);
    }
    
    private void countingSort(int div) {
        
        int index[] = new int[10];
        Arquivo places = new Arquivo("arquivosaux\\placesRadix.txt");
        Registro reg = new Registro();
        
        places.copiaArquivo(this);
        seekArq(0);
        while (!eof()) {
            
            reg.leDoArq(arquivo);
            index[(reg.getNumero()/div)%10]++;
        }
        for (int i = 1 ; i > index.length ; i++)
            index[i] += index[i-1];
        places.seekArq(0);
        while (!places.eof()) {
            
            reg.leDoArq(places.getArquivo());
            seekArq(--index[(reg.getNumero()/div)%10]);
            reg.gravaNoArq(arquivo);
            mov++;
        }
    }
    
    public void combSort() {
        
        int tl = filesize();
        Registro reg1 = new Registro();
        Registro reg2 = new Registro();
        
        for (int gap = (int)(tl / 1.3) ; gap > 0 ; gap /= 1.3) {
            
            for (int i = 0 ; i + gap < tl ; i++) {
                
                seekArq(i);
                reg1.leDoArq(arquivo);
                seekArq(i+gap);
                reg2.leDoArq(arquivo);
                if (reg1.getNumero() > reg2.getNumero()) {
                    
                    seekArq(i);
                    reg2.gravaNoArq(arquivo);
                    seekArq(i+gap);
                    reg1.gravaNoArq(arquivo);
                    mov += 2;
                }
                comp++;
            }
        }
    }
    
    public void gnomeSort() {
        
        int tl = filesize();
        boolean flag = true;
        Registro reg1 = new Registro();
        Registro reg2 = new Registro();
        
        for (int i = 0 ; i < tl - 1 ; i++) {
            
            seekArq(i);
            reg1.leDoArq(arquivo);
            reg2.leDoArq(arquivo);
            if(reg2.getNumero() < reg1.getNumero()) {
                
                seekArq(i);
                reg2.gravaNoArq(arquivo);
                reg1.gravaNoArq(arquivo);
                mov += 2;
                for (int j = i - 1 ; j >= 0 && flag ; j--) {
                    
                    seekArq(j);
                    reg1.leDoArq(arquivo);
                    reg2.leDoArq(arquivo);
                    if (reg1.getNumero() > reg2.getNumero()) {
                        
                        seekArq(j);
                        reg2.gravaNoArq(arquivo);
                        reg1.gravaNoArq(arquivo);
                        mov += 2;
                    }
                    else
                        flag = false;
                    comp++;
                }
                flag = true;
            }
            comp++;
        }
    }
    
    public void timSort() {
        
        int i, tl = filesize();
        boolean b = true;
        Arquivo aux = new Arquivo("arquivosaux\\auxTim.txt");
        
        for (i = 0 ; b ; i += 2) 
            b = insercaoDireta(i);
        aux.copiaArquivo(this);
        for (i = 2 ; i < tl ; i *= 2) {
            
            for (int j = 0 ; j < tl ; j += i * 2) {
                
                mergeSort(aux,this,j,j+i,j+i*2); 
                aux.copiaArquivo(this);
            }
        }
    }
    
    private boolean insercaoDireta(int i) {
        
        int j, k, tl = filesize();
        boolean flag = true;
        Registro reg1 = new Registro();
        Registro reg2 = new Registro();
        
        for (j = i + 1 ; j < i + 2 && j < tl ; j++) {
            
            seekArq(j);
            reg1.leDoArq(arquivo);
            for (k = j ; k > i && flag ; k--) {
                
                seekArq(k-1);
                reg2.leDoArq(arquivo);
                if (reg2.getNumero() > reg1.getNumero()) {
                    
                    reg2.gravaNoArq(arquivo);
                    mov++;
                }
                else {
                    
                    reg1.gravaNoArq(arquivo);
                    mov++;
                    flag = false;
                }
                comp++;
            }
            if(flag) {
                
                seekArq(k);
                reg1.gravaNoArq(arquivo);
                mov++;
            }
            flag = true;
        }
        
        return j < tl;
    }
    
    private void mergeSort(Arquivo ori, Arquivo dest, int ini, int meio, int fim) {
        
        int auxini = ini, auxmeio = meio, oritl = ori.filesize();
        boolean flag = true;
        Registro reg1 = new Registro();
        Registro reg2 = new Registro();
        
        while (auxini < meio && auxmeio < oritl && auxmeio < fim && auxini < oritl) {
            
            ori.seekArq(auxini);
            reg1.leDoArq(ori.getArquivo());
            ori.seekArq(auxmeio);
            reg2.leDoArq(ori.getArquivo());
            dest.seekArq(ini++);
            if (reg1.getNumero() <= reg2.getNumero()) {
                
                reg1.gravaNoArq(dest.getArquivo());
                mov++;
                auxini++;
            }   
            else {
                
                reg2.gravaNoArq(dest.getArquivo());
                mov++;
                auxmeio++;
            }
            comp++;
        }
        ori.seekArq(auxini);
        dest.seekArq(ini);
        while (auxini < meio && auxini < oritl) {
            
            reg1.leDoArq(ori.getArquivo());
            reg1.gravaNoArq(dest.getArquivo());
            mov++;
            auxini++;
            flag = false;
        }
        ori.seekArq(auxmeio);
        while (auxmeio < ori.filesize() && auxmeio < fim && flag) {
            
            reg2.leDoArq(ori.getArquivo());
            reg2.gravaNoArq(dest.getArquivo());
            mov++;
            auxmeio++;
        }
    }
}