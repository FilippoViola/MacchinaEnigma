package filippo.viola.macchinaenigma.entity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;

public class Cablaggio {
    private static final String PATH_CABLAGGI = "configurazione/cablaggio.txt";
    private static final ArrayList<Cablaggio> cablaggi;
    public int[] cablaggio;
    public int[] cablaggioInvertito;
    private int puntoScatto;
    public Cablaggio(String cablaggio){
        this.cablaggio = new int[26];
        this.cablaggioInvertito = new int[26];
        for(int i = 0; i < 26; i++){
            char c = cablaggio.charAt(i);
            if(Character.isUpperCase(c)){
                puntoScatto = i;
            } else {
                c = Character.toUpperCase(c);
            }
            int a = c - 'A';
            this.cablaggio[i] = a - i;
            this.cablaggioInvertito[a] = i - a;
        }
    }

    public static int getTotaleCabaggi(){
        return cablaggi.size();
    }

    public int getPuntoScatto() {
        return puntoScatto;
    }

    public static Cablaggio getCablaggio(int numero){
        return cablaggi.get(numero);
    }

    static {
        int tmp = 0;
        cablaggi = new ArrayList<>();
        FileReader fr = null;
        BufferedReader br = null;
        try{
            fr = new FileReader(PATH_CABLAGGI);
            br = new BufferedReader(fr);
            String ca;
            while((ca = br.readLine()) != null){
                cablaggi.add(new Cablaggio(ca));
            }
        } catch (IOException e){
            System.out.println("Errore lettura");
        } finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("Errore lettura");
                }
            }
            if(fr != null){
                try {
                    fr.close();
                } catch (IOException e) {
                    System.out.println("Errore lettura");
                }
            }
        }
    }
}
