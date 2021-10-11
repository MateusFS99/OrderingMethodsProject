package Arquivo;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Registro {
    
    public final int tf = 1022;
    private int numero;
    private char lixo[] = new char[tf];
    private Registro prox, ant;

    public Registro() {}
    
    public Registro(int numero) {
        
        this.numero = numero;
        for (int i = 0 ; i < tf ; i++)
            lixo[i] = 'X';
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Registro getProx() {
        return prox;
    }

    public void setProx(Registro prox) {
        this.prox = prox;
    }

    public Registro getAnt() {
        return ant;
    }

    public void setAnt(Registro ant) {
        this.ant = ant;
    }
    
    public static int length() {
        return 2048;
    }
    
    public void leDoArq(RandomAccessFile arquivo) {
        
        try {

            numero = arquivo.readInt();
            for (int i = 0 ; i < tf ; i++)
                lixo[i] = arquivo.readChar();
        } catch (IOException e){}
    }
    
    public void gravaNoArq(RandomAccessFile arquivo) {
        
        try {
            
            arquivo.writeInt(numero);
            for (int i = 0 ; i < tf ; i++)
                arquivo.writeChar(lixo[i]);
        } catch(IOException e) {}
    }
}