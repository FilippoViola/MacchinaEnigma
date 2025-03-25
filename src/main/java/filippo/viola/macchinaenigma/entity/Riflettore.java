package filippo.viola.macchinaenigma.entity;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Riflettore {;
    private final String cablaggio;
    private static final String DEFAULT_CONFIGURAZIONE_PATH = "configurazione/riflettore.txt";
    public Riflettore(String cablaggio) {
        this.cablaggio = cablaggio;
    }

    public Riflettore() {
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(DEFAULT_CONFIGURAZIONE_PATH);
            br = new BufferedReader(fr);
            this.cablaggio = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (null != fr){
                try {
                    fr.close();
                } catch (IOException e) {
                    System.out.println("WTF");
                }
            }
            if(null != br){
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("WTF");
                }
            }
        }
    }

    public char rifletti(char c) {
        int indice = c - 'A';
        return cablaggio.charAt(indice);
    }
}

