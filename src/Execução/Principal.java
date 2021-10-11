package Execução;

import Arquivo.OrdenaçõesArquivo;
import Lista.OrdenaçõesLista;
import java.io.IOException;

public class Principal {
    
    public static void main(String args[]) throws IOException {
        
        OrdenaçõesArquivo oa = new OrdenaçõesArquivo();
        OrdenaçõesLista ol = new OrdenaçõesLista();
        
        oa.executa();
        //oa.testaOrdenacoesArquivo();
        //ol.testaOrdenacoesLista();
    }
}