package filippo.viola.macchinaenigma.entity;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

public class Riflettore {;
    private final String cablaggio;
    public static final String DEFAULT_CABLEAGGIO;
    private static final String DEFAULT_CONFIGURAZIONE_PATH = "configurazione/riflettore.txt";
    public Riflettore(String cablaggio) {
        this.cablaggio = cablaggio;
    }

    public Riflettore() {
        this.cablaggio = DEFAULT_CABLEAGGIO;
    }

    static {
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(DEFAULT_CONFIGURAZIONE_PATH);
            br = new BufferedReader(fr);
            DEFAULT_CABLEAGGIO = br.readLine().toUpperCase();
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

