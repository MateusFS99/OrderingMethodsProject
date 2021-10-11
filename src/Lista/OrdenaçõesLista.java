package Lista;

public class OrdenaçõesLista {
    
    public void testaOrdenacoesLista() {
        
        long tini, tfim;
        String metodos[] = {"Inserção Direta","Inserção Binária","Seleção Direta","Bubble Sort","Shake Sort",
                            "Shell Sort","Heap Sort","Quick Sort com Pivo","Quick Sort sem Pivo","Merge Sort 1",
                            "Merge Sort 2","Counting Sort","Bucket Sort","Radix Sort","Comb Sort","Gnome Sort","Tim Sort"};
        Lista l;
        for (int i = 0 ; i < metodos.length ; i++) {
            
            l = new Lista();
        
            l.geraListaReversa();
            
            //l.exibe();
            System.out.println(metodos[i] + ":");
            tini = System.currentTimeMillis();
            switch (i) {
                case 0: l.insercaoDireta();break;
                case 1: l.insercaoBinaria();break;
                case 2: l.selecaoDireta();break;
                case 3: l.bubbleSort();break;
                case 4: l.shakeSort();break;
                case 5: l.shellSort();break;
                case 6: l.heapSort();break;
                case 7: l.quickComPivo();break;
                case 8: l.quickSemPivo();break;
                case 9: l.mergeSort1();break;
                case 10: l.mergeSort2();break;
                case 11: l.countingSort();break;
                case 12: l.bucketSort();break;
                case 13: l.radixSort();break;
                case 14: l.combSort();break;
                case 15: l.gnomeSort();break;
                case 16: l.timSort();break;
                default: break;
            }
            tfim = System.currentTimeMillis();
            l.exibe();
            System.out.println("Tempo: " + (tfim-tini) + "ms");
        }
    }
}