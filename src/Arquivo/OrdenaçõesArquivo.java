package Arquivo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class OrdenaçõesArquivo {
    
    private final String metodos[];
    private Arquivo arqOrd, arqRev, arqRand, auxRev, auxRand;
    private RandomAccessFile tabela;

    public OrdenaçõesArquivo() {
        this.metodos = new String[]{"Inserção Direta", "Inserção Binária", "Seleção Direta", "Bubble Sort", "Shake Sort", 
            "Shell Sort", "Heap Sort", "Quick Sort com Pivo", "Quick Sort sem Pivo", "Merge Sort 1", "Merge Sort 2", 
            "Counting Sort", "Bucket Sort", "Radix Sort", "Comb Sort", "Gnome Sort", "Tim Sort"};
    }
    
    public void testaOrdenacoesArquivo() {
        
        long tini, tfim;
        Arquivo arq;
        
        for (int i = 0 ; i < metodos.length ; i++) {
            
            arq = new Arquivo("arquivos\\" + metodos[i] + ".txt");
            
            arq.geraArquivoReverso();
            
            //arq.exibirArq();
            System.out.println(metodos[i] + ":");
            tini = System.currentTimeMillis();
            switch (i) {
                case 0: arq.insercaoDireta();break;
                case 1: arq.insercaoBinaria();break;
                case 2: arq.selecaoDireta();break;
                case 3: arq.bubbleSort();break;
                case 4: arq.shakeSort();break;
                case 5: arq.shellSort();break;
                case 6: arq.heapSort();break;
                case 7: arq.quickComPivo();break;
                case 8: arq.quickSemPivo();break;
                case 9: arq.mergeSort1();break;
                case 10: arq.mergeSort2();break;
                case 11: arq.countingSort();break;
                case 12: arq.bucketSort();break;
                case 13: arq.radixSort();break;
                case 14: arq.combSort();break;
                case 15: arq.gnomeSort();break;
                case 16: arq.timSort();break;
                default: break;
            }
            tfim = System.currentTimeMillis();
            arq.exibirArq();
            System.out.println("Tempo: " + (tfim - tini)/1000 + "s");
        }
    }
    
    private void initArquivos() {
        
        arqOrd = new Arquivo("execucao\\ordenado.txt");
        arqRev = new Arquivo("execucao\\reverso.txt");
        arqRand = new Arquivo("execucao\\randomico.txt");
        auxRev = new Arquivo("execucao\\auxreverso.txt");
        auxRand = new Arquivo("execucao\\auxrandomico.txt");
        
        arqOrd.geraArquivoOrdenado();
        arqRev.geraArquivoReverso();
        arqRand.geraArquivoRandomico();
    }
    
    private void geraTabela() throws FileNotFoundException, IOException {
        
        String str;
        
        tabela = new RandomAccessFile("execucao\\tabela.txt", "rw");
        str = "  Métodos Ordenação  |                Arquivo Ordenado                 |             Arquivo em Ordem Reversa            |                Arquivo Randômico                |\n";
        tabela.writeBytes(str);
        str = "                     | CompProg | CompEqua | MovProg | MovEqua | Tempo | CompProg | CompEqua | MovProg | MovEqua | Tempo | CompProg | CompEqua | MovProg | MovEqua | Tempo |\n";
        tabela.writeBytes(str);
    }
    
    private void gravaLinhaTabela(String metodo, int cprogO, int cequaO, int mprogO, int mequaO, long tempoO,
            int cprogRe, int cequaRe, int mprogRe, int mequaRe, long tempoRe, int cprogRa, int cequaRa, int mprogRa,
            int mequaRa, long tempoRa) throws FileNotFoundException, IOException {
        
        String linha = metodo + " | " + cprogO + " | " + cequaO + " | " + mprogO + " | " + mequaO + " | " + tempoO + "s | " 
                + cprogRe + " | " + cequaRe + " | " + mprogRe + " | " + mequaRe + " | " + tempoRe + "s | " + cprogRa + " | " 
                + cequaRa + " | " + mprogRa + " | " + mequaRa + " | " + tempoRa + "s |\n";
        
        tabela = new RandomAccessFile("execucao\\tabela.txt","rw");
        tabela.seek(tabela.length());
        tabela.writeBytes(linha);
    }
    
    private void execInsercaoDireta() throws IOException {
        
        int compO, movO, compRev, movRev, compRand, movRand;
        long tini, tfim, ttotalO, ttotalRev, ttotalRand;
        
        //Arquivo Ordenado//
        arqOrd.initComp();
        arqOrd.initMov();
        
        tini = System.currentTimeMillis();
        arqOrd.insercaoDireta();
        tfim = System.currentTimeMillis();
        
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = (tfim - tini) / 1000;
        
        //Arquivo Reverso//
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        
        tini = System.currentTimeMillis();
        auxRev.insercaoDireta();
        tfim = System.currentTimeMillis();
        
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        ttotalRev = (tfim - tini) / 1000;
        
        //Arquivo Randômico//
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        
        tini = System.currentTimeMillis();
        auxRand.insercaoDireta();
        tfim = System.currentTimeMillis();
        
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        ttotalRand = (tfim - tini) / 1000;
        
        gravaLinhaTabela("   Inserção Direta  ", compO, calculaCompInsDirMin(arqOrd.filesize()), movO, 
                calculaMovInsDirMin(arqOrd.filesize()), ttotalO, compRev, calculaCompInsDirMax(arqRev.filesize()), 
                movRev, calculaMovInsDirMax(arqRev.filesize()), ttotalRev, compRand, 
                calculaCompInsDirMed(arqRand.filesize()), movRand, calculaMovInsDirMed(arqRand.filesize()), ttotalRand);
    }
    
    private void execInsercaoBinaria() throws IOException {
        
        int compO, movO, compRev, movRev, compRand, movRand;
        long tini, tfim, ttotalO, ttotalRev, ttotalRand;
        
        //Arquivo Ordenado//
        arqOrd.initComp();
        arqOrd.initMov();
        
        tini = System.currentTimeMillis();
        arqOrd.insercaoBinaria();
        tfim = System.currentTimeMillis();
        
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = (tfim - tini) / 1000;
        
        //Arquivo Reverso//
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        
        tini = System.currentTimeMillis();
        auxRev.insercaoBinaria();
        tfim = System.currentTimeMillis();
        
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        ttotalRev = (tfim - tini) / 1000;
        
        //Arquivo Randômico//
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        
        tini = System.currentTimeMillis();
        auxRand.insercaoBinaria();
        tfim = System.currentTimeMillis();
        
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        ttotalRand = (tfim - tini) / 1000;
        
        gravaLinhaTabela("  Inserção Binaria", compO, calculaCompInsBin(arqOrd.filesize()), movO, 
                calculaMovInsBinMin(arqOrd.filesize()), ttotalO, compRev, 
                calculaCompInsBin(arqRev.filesize()), movRev, calculaMovInsBinMax(arqRev.filesize()), ttotalRev, compRand, 
                calculaCompInsBin(arqRand.filesize()), movRand, calculaMovInsBinMed(arqRand.filesize()), ttotalRand);
    }

    private void execSelecaoDireta() throws IOException {
        
        int compO, movO, compRev, movRev, compRand, movRand;
        long tini, tfim, ttotalO, ttotalRev, ttotalRand;
        
        //Arquivo Ordenado//
        arqOrd.initComp();
        arqOrd.initMov();
        
        tini = System.currentTimeMillis();
        arqOrd.selecaoDireta();
        tfim = System.currentTimeMillis();
        
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = (tfim - tini) / 1000;
        
        //Arquivo Reverso//
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        
        tini = System.currentTimeMillis();
        auxRev.selecaoDireta();
        tfim = System.currentTimeMillis();
        
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        ttotalRev = (tfim - tini) / 1000;
        
        //Arquivo Randômico//
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        
        tini = System.currentTimeMillis();
        auxRand.selecaoDireta();
        tfim = System.currentTimeMillis();
        
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        ttotalRand = (tfim - tini) / 1000;
        
        gravaLinhaTabela("  Seleção Direta", compO, calculaCompSeleDir(arqOrd.filesize()), movO, 
                calculaMovSeleDirMin(arqOrd.filesize()), ttotalO, compRev, calculaCompSeleDir(arqRev.filesize()), movRev, 
                calculaMovSeleDirMax(arqRev.filesize()), ttotalRev, compRand, calculaCompSeleDir(arqRand.filesize()), 
                movRand, calculaMovSeleDirMed(arqRand.filesize()), ttotalRand);
    }

    private void execBubbleSort() throws IOException {
        
        int compO, movO, compRev, movRev, compRand, movRand;
        long tini, tfim, ttotalO, ttotalRev, ttotalRand;
        
        //Arquivo Ordenado//
        arqOrd.initComp();
        arqOrd.initMov();
        
        tini = System.currentTimeMillis();
        arqOrd.bubbleSort();
        tfim = System.currentTimeMillis();
        
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = (tfim - tini) / 1000;
        
        //Arquivo Reverso//
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        
        tini = System.currentTimeMillis();
        auxRev.bubbleSort();
        tfim = System.currentTimeMillis();
        
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        ttotalRev = (tfim - tini) / 1000;
        
        //Arquivo Randômico//
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        
        tini = System.currentTimeMillis();
        auxRand.bubbleSort();
        tfim = System.currentTimeMillis();
        
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        ttotalRand = (tfim - tini) / 1000;
        
        gravaLinhaTabela("  Bubble Sort", compO, calculaCompBubble(arqOrd.filesize()), movO, calculaMovBubbleMin(), ttotalO,
                compRev, calculaCompBubble(arqRev.filesize()), movRev, calculaMovBubbleMax(arqRev.filesize()), ttotalRev, 
                compRand, calculaCompBubble(arqRand.filesize()), movRand, calculaMovBubbleMed(arqRand.filesize()), ttotalRand);
    }

    private void execShakeSort() throws IOException {
        
        int compO, movO, compRev, movRev, compRand, movRand;
        long tini, tfim, ttotalO, ttotalRev, ttotalRand;
        
        //Arquivo Ordenado//
        arqOrd.initComp();
        arqOrd.initMov();
        
        tini = System.currentTimeMillis();
        arqOrd.shakeSort();
        tfim = System.currentTimeMillis();
        
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = (tfim - tini) / 1000;
        
        //Arquivo Reverso//
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        
        tini = System.currentTimeMillis();
        auxRev.shakeSort();
        tfim = System.currentTimeMillis();
        
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        ttotalRev = (tfim - tini) / 1000;
        
        //Arquivo Randômico//
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        
        tini = System.currentTimeMillis();
        auxRand.shakeSort();
        tfim = System.currentTimeMillis();
        
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        ttotalRand = (tfim - tini) / 1000;
        
        gravaLinhaTabela("  Shake Sort", compO, -1, movO, -1, ttotalO, compRev, -1, 
                movRev, -1, ttotalRev, compRand, -1, movRand, -1, ttotalRand);
    }

    private void execShellSort() throws IOException {
        
        int compO, movO, compRev, movRev, compRand, movRand;
        long tini, tfim, ttotalO, ttotalRev, ttotalRand;
        
        //Arquivo Ordenado//
        arqOrd.initComp();
        arqOrd.initMov();
        
        tini = System.currentTimeMillis();
        arqOrd.shellSort();
        tfim = System.currentTimeMillis();
        
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = (tfim - tini) / 1000;
        
        //Arquivo Reverso//
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        
        tini = System.currentTimeMillis();
        auxRev.shellSort();
        tfim = System.currentTimeMillis();
        
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        ttotalRev = (tfim - tini) / 1000;
        
        //Arquivo Randômico//
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        
        tini = System.currentTimeMillis();
        auxRand.shellSort();
        tfim = System.currentTimeMillis();
        
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        ttotalRand = (tfim - tini) / 1000;
        
        gravaLinhaTabela("  Shell Sort", compO, -1, movO, -1, ttotalO, compRev, -1, 
                movRev, -1, ttotalRev, compRand, -1, movRand, -1, ttotalRand);
    }

    private void execHeapSort() throws IOException {
       
        int compO, movO, compRev, movRev, compRand, movRand;
        long tini, tfim, ttotalO, ttotalRev, ttotalRand;
        
        //Arquivo Ordenado//
        arqOrd.initComp();
        arqOrd.initMov();
        
        tini = System.currentTimeMillis();
        arqOrd.heapSort();
        tfim = System.currentTimeMillis();
        
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = (tfim - tini) / 1000;
        
        //Arquivo Reverso//
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        
        tini = System.currentTimeMillis();
        auxRev.heapSort();
        tfim = System.currentTimeMillis();
        
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        ttotalRev = (tfim - tini) / 1000;
        
        //Arquivo Randômico//
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        
        tini = System.currentTimeMillis();
        auxRand.heapSort();
        tfim = System.currentTimeMillis();
        
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        ttotalRand = (tfim - tini) / 1000;
        
        gravaLinhaTabela("  Heap Sort", compO, -1, movO, -1, ttotalO, compRev, -1, 
                movRev, -1, ttotalRev, compRand, -1, movRand, -1, ttotalRand);
    }

    private void execQuickComPivo() throws IOException {
        
        int compO, movO, compRev, movRev, compRand, movRand;
        long tini, tfim, ttotalO, ttotalRev, ttotalRand;
        
        //Arquivo Ordenado//
        arqOrd.initComp();
        arqOrd.initMov();
        
        tini = System.currentTimeMillis();
        arqOrd.quickComPivo();
        tfim = System.currentTimeMillis();
        
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = (tfim - tini) / 1000;
        
        //Arquivo Reverso//
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        
        tini = System.currentTimeMillis();
        auxRev.quickComPivo();
        tfim = System.currentTimeMillis();
        
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        ttotalRev = (tfim - tini) / 1000;
        
        //Arquivo Randômico//
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        
        tini = System.currentTimeMillis();
        auxRand.quickComPivo();
        tfim = System.currentTimeMillis();
        
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        ttotalRand = (tfim - tini) / 1000;
        
        gravaLinhaTabela("  Quick Sort com Pivo", compO, -1, movO, -1, ttotalO, compRev, -1, 
                movRev, -1, ttotalRev, compRand, -1, movRand, -1, ttotalRand);
    }

    private void execQuickSemPivo() throws IOException {
        
        int compO, movO, compRev, movRev, compRand, movRand;
        long tini, tfim, ttotalO, ttotalRev, ttotalRand;
        
        //Arquivo Ordenado//
        arqOrd.initComp();
        arqOrd.initMov();
        
        tini = System.currentTimeMillis();
        arqOrd.quickSemPivo();
        tfim = System.currentTimeMillis();
        
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = (tfim - tini) / 1000;
        
        //Arquivo Reverso//
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        
        tini = System.currentTimeMillis();
        auxRev.quickSemPivo();
        tfim = System.currentTimeMillis();
        
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        ttotalRev = (tfim - tini) / 1000;
        
        //Arquivo Randômico//
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        
        tini = System.currentTimeMillis();
        auxRand.quickSemPivo();
        tfim = System.currentTimeMillis();
        
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        ttotalRand = (tfim - tini) / 1000;
        
        gravaLinhaTabela("  Quick Sort sem Pivo", compO, -1, movO, -1, ttotalO, compRev, -1, 
                movRev, -1, ttotalRev, compRand, -1, movRand, -1, ttotalRand);
    }

    private void execMergeSort1() throws IOException {
        
        int compO, movO, compRev, movRev, compRand, movRand;
        long tini, tfim, ttotalO, ttotalRev, ttotalRand;
        
        //Arquivo Ordenado//
        arqOrd.initComp();
        arqOrd.initMov();
        
        tini = System.currentTimeMillis();
        arqOrd.mergeSort1();
        tfim = System.currentTimeMillis();
        
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = (tfim - tini) / 1000;
        
        //Arquivo Reverso//
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        
        tini = System.currentTimeMillis();
        auxRev.mergeSort1();
        tfim = System.currentTimeMillis();
        
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        ttotalRev = (tfim - tini) / 1000;
        
        //Arquivo Randômico//
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        
        tini = System.currentTimeMillis();
        auxRand.mergeSort1();
        tfim = System.currentTimeMillis();
        
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        ttotalRand = (tfim - tini) / 1000;
        
        gravaLinhaTabela("  Merge Sort 1", compO, -1, movO, -1, ttotalO, compRev, -1, 
                movRev, -1, ttotalRev, compRand, -1, movRand, -1, ttotalRand);
    }

    private void execMergeSort2() throws IOException {
        
        int compO, movO, compRev, movRev, compRand, movRand;
        long tini, tfim, ttotalO, ttotalRev, ttotalRand;
        
        //Arquivo Ordenado//
        arqOrd.initComp();
        arqOrd.initMov();
        
        tini = System.currentTimeMillis();
        arqOrd.mergeSort2();
        tfim = System.currentTimeMillis();
        
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = (tfim - tini) / 1000;
        
        //Arquivo Reverso//
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        
        tini = System.currentTimeMillis();
        auxRev.mergeSort2();
        tfim = System.currentTimeMillis();
        
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        ttotalRev = (tfim - tini) / 1000;
        
        //Arquivo Randômico//
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        
        tini = System.currentTimeMillis();
        auxRand.mergeSort2();
        tfim = System.currentTimeMillis();
        
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        ttotalRand = (tfim - tini) / 1000;
        
        gravaLinhaTabela("  Merge Sort 2", compO, -1, movO, -1, ttotalO, compRev, -1, 
                movRev, -1, ttotalRev, compRand, -1, movRand, -1, ttotalRand);
    }

    private void execCountingSort() throws IOException {
        
        int compO, movO, compRev, movRev, compRand, movRand;
        long tini, tfim, ttotalO, ttotalRev, ttotalRand;
        
        //Arquivo Ordenado//
        arqOrd.initComp();
        arqOrd.initMov();
        
        tini = System.currentTimeMillis();
        arqOrd.countingSort();
        tfim = System.currentTimeMillis();
        
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = (tfim - tini) / 1000;
        
        //Arquivo Reverso//
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        
        tini = System.currentTimeMillis();
        auxRev.countingSort();
        tfim = System.currentTimeMillis();
        
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        ttotalRev = (tfim - tini) / 1000;
        
        //Arquivo Randômico//
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        
        tini = System.currentTimeMillis();
        auxRand.countingSort();
        tfim = System.currentTimeMillis();
        
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        ttotalRand = (tfim - tini) / 1000;
        
        gravaLinhaTabela("  Counting Sort", compO, -1, movO, -1, ttotalO, compRev, -1, 
                movRev, -1, ttotalRev, compRand, -1, movRand, -1, ttotalRand);
    }

    private void execBucketSort() throws IOException {
        
        int compO, movO, compRev, movRev, compRand, movRand;
        long tini, tfim, ttotalO, ttotalRev, ttotalRand;
        
        //Arquivo Ordenado//
        arqOrd.initComp();
        arqOrd.initMov();
        
        tini = System.currentTimeMillis();
        arqOrd.bucketSort();
        tfim = System.currentTimeMillis();
        
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = (tfim - tini) / 1000;
        
        //Arquivo Reverso//
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        
        tini = System.currentTimeMillis();
        auxRev.bucketSort();
        tfim = System.currentTimeMillis();
        
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        ttotalRev = (tfim - tini) / 1000;
        
        //Arquivo Randômico//
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        
        tini = System.currentTimeMillis();
        auxRand.bucketSort();
        tfim = System.currentTimeMillis();
        
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        ttotalRand = (tfim - tini) / 1000;
        
        gravaLinhaTabela("  Bucket Sort", compO, -1, movO, -1, ttotalO, compRev, -1, 
                movRev, -1, ttotalRev, compRand, -1, movRand, -1, ttotalRand);
    }
    
    private void execRadixSort() throws IOException {
        
        int compO, movO, compRev, movRev, compRand, movRand;
        long tini, tfim, ttotalO, ttotalRev, ttotalRand;
        
        //Arquivo Ordenado//
        arqOrd.initComp();
        arqOrd.initMov();
        
        tini = System.currentTimeMillis();
        arqOrd.radixSort();
        tfim = System.currentTimeMillis();
        
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = (tfim - tini) / 1000;
        
        //Arquivo Reverso//
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        
        tini = System.currentTimeMillis();
        auxRev.radixSort();
        tfim = System.currentTimeMillis();
        
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        ttotalRev = (tfim - tini) / 1000;
        
        //Arquivo Randômico//
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        
        tini = System.currentTimeMillis();
        auxRand.radixSort();
        tfim = System.currentTimeMillis();
        
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        ttotalRand = (tfim - tini) / 1000;
        
        gravaLinhaTabela("  Radix Sort", compO, -1, movO, -1, ttotalO, compRev, -1, 
                movRev, -1, ttotalRev, compRand, -1, movRand, -1, ttotalRand);
    }

    private void execCombSort() throws IOException {
        
        int compO, movO, compRev, movRev, compRand, movRand;
        long tini, tfim, ttotalO, ttotalRev, ttotalRand;
        
        //Arquivo Ordenado//
        arqOrd.initComp();
        arqOrd.initMov();
        
        tini = System.currentTimeMillis();
        arqOrd.combSort();
        tfim = System.currentTimeMillis();
        
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = (tfim - tini) / 1000;
        
        //Arquivo Reverso//
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        
        tini = System.currentTimeMillis();
        auxRev.combSort();
        tfim = System.currentTimeMillis();
        
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        ttotalRev = (tfim - tini) / 1000;
        
        //Arquivo Randômico//
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        
        tini = System.currentTimeMillis();
        auxRand.combSort();
        tfim = System.currentTimeMillis();
        
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        ttotalRand = (tfim - tini) / 1000;
        
        gravaLinhaTabela("  Comb Sort", compO, -1, movO, -1, ttotalO, compRev, -1, 
                movRev, -1, ttotalRev, compRand, -1, movRand, -1, ttotalRand);
    }
    
    private void execGnomeSort() throws IOException {
        
        int compO, movO, compRev, movRev, compRand, movRand;
        long tini, tfim, ttotalO, ttotalRev, ttotalRand;
        
        //Arquivo Ordenado//
        arqOrd.initComp();
        arqOrd.initMov();
        
        tini = System.currentTimeMillis();
        arqOrd.bucketSort();
        tfim = System.currentTimeMillis();
        
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = (tfim - tini) / 1000;
        
        //Arquivo Reverso//
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        
        tini = System.currentTimeMillis();
        auxRev.bucketSort();
        tfim = System.currentTimeMillis();
        
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        ttotalRev = (tfim - tini) / 1000;
        
        //Arquivo Randômico//
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        
        tini = System.currentTimeMillis();
        auxRand.bucketSort();
        tfim = System.currentTimeMillis();
        
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        ttotalRand = (tfim - tini) / 1000;
        
        gravaLinhaTabela("  Gnome Sort", compO, -1, movO, -1, ttotalO, compRev, -1, 
                movRev, -1, ttotalRev, compRand, -1, movRand, -1, ttotalRand);
    }

    private void execTimSort() throws IOException {
        
        int compO, movO, compRev, movRev, compRand, movRand;
        long tini, tfim, ttotalO, ttotalRev, ttotalRand;
        
        //Arquivo Ordenado//
        arqOrd.initComp();
        arqOrd.initMov();
        
        tini = System.currentTimeMillis();
        arqOrd.timSort();
        tfim = System.currentTimeMillis();
        
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = (tfim - tini) / 1000;
        
        //Arquivo Reverso//
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        
        tini = System.currentTimeMillis();
        auxRev.timSort();
        tfim = System.currentTimeMillis();
        
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        ttotalRev = (tfim - tini) / 1000;
        
        //Arquivo Randômico//
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        
        tini = System.currentTimeMillis();
        auxRand.timSort();
        tfim = System.currentTimeMillis();
        
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        ttotalRand = (tfim - tini) / 1000;
        
        gravaLinhaTabela("  Tim Sort", compO, -1, movO, -1, ttotalO, compRev, -1, 
                movRev, -1, ttotalRev, compRand, -1, movRand, -1, ttotalRand);
    }
    
    public void executa() throws IOException {
        
        initArquivos();
        
        geraTabela();
        
        execInsercaoDireta();
        execInsercaoBinaria();
        execSelecaoDireta();
        execBubbleSort();
        execShakeSort();
        execShellSort();
        execHeapSort();
        execQuickComPivo();
        execQuickSemPivo();
        execMergeSort1();
        execMergeSort2();
        execCountingSort();
        execBucketSort();
        execRadixSort();
        execCombSort();
        execGnomeSort();
        execTimSort();
    }
    
    private int calculaCompInsDirMin(int n) {
        return n - 1;
    }
    
    private int calculaCompInsDirMed(int n) {
        return (int)((Math.pow(n, 2) + n - 2) / 4);
    }
    
    private int calculaCompInsDirMax(int n) {
        return (int)((Math.pow(n, 2) + n - 4) / 4);
    }
    
    private int calculaMovInsDirMin(int n) {
        return 3 * (n - 1);
    }
    
    private int calculaMovInsDirMed(int n) {
        return (int)((Math.pow(n, 2) + 9 * n - 10) / 4);
    }
    
    private int calculaMovInsDirMax(int n) {
        return (int)((Math.pow(n, 2) + 3 * n - 4) / 2);
    }
    
    private int calculaCompInsBin(int n) {
        return (int)(n * (Math.log(n) - Math.log(1.44269) + 0.5));
    }
    
    private int calculaMovInsBinMin(int n) {
        return 3 * (n - 1);
    }
    
    private int calculaMovInsBinMed(int n) {
        return (int)((Math.pow(n, 2) + 9 * n - 10) / 4);
    }
    
    private int calculaMovInsBinMax(int n) {
        return (int) ((Math.pow(n, 2) + 3 * n - 4)/2);
    }
    
    private int calculaCompSeleDir(int n) {
        return (n * n - n) / 2;
    }
    
    private int calculaMovSeleDirMin(int n) {
        return 3 * (n - 1);
    }
    
    private int calculaMovSeleDirMed(int n) {
        return (int)(n * (Math.log(1.44269) * n + 6));
    }
    
    private int calculaMovSeleDirMax(int n) {
        return (int)(Math.pow(n, 2) / 4 + 3 * (n - 1));
    }
    
    private int calculaCompBubble(int n) {
        return (int)((Math.pow(n, 2) - n) / 2);
    }
    
    private int calculaMovBubbleMin() {
        return 0;
    }
    
    private int calculaMovBubbleMed(int n) {
        return 3 * calculaCompBubble(n);
    }
    
    private int calculaMovBubbleMax(int n) {
        return calculaMovBubbleMed(n)/2;
    }
}