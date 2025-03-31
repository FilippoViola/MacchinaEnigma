package filippo.viola.macchinaenigma.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Scambiatore {

    /**
     * Contiene informazioni sulla mappatura tra i caratteri, '*' indica che non è
     * scambiato con nessun altro.
     */
    private final ArrayList<Character> cavi;

    /**
     * Costruttore della classe
     * @param scambio indica come vengono scambiato le lettere,
     *                suddivisi in gruppi di due(queste vengono scambiate)
     *                es: "ACDB" A e C, D e B vengono scambiati
     */
    public Scambiatore(String scambio){
        cavi = new ArrayList<>(26);
        Collections.fill(cavi,'*');
        for(int i = 0; i < scambio.length(); i+=2){
            char a = scambio.charAt(i);
            char b = scambio.charAt(i+1);
            cavi.set(a-'A',b);
            cavi.set(b-'A',a);
        }
    }

    public Scambiatore() {

        cavi = new ArrayList<>(Collections.nCopies(26,'*'));
    }

    /**
     * aggiunge la corrispondenza tra i due caratteri
     * @param a carattere scambiato
     * @param b carattere scambiato
     * @return se è possibile aggiungere il cavo
     */
    public boolean aggiungiCavo(char a,char b){
        int posA = a-'A';
        int posB = b-'A';
        if(cavi.get(posA) != '*' || cavi.get(posB) != '*'){ // esiste già un cavo
            return false;
        }
        cavi.set(posA,b);
        cavi.set(posB,a);
        return true;
    }

    public boolean aggiungiCavo(String s){
        return aggiungiCavo(s.charAt(0),s.charAt(1));
    }


    /**
     * togliere il cavo che scambia il carattere c
     * @param c carattere
     * @return Se è stato possibile togliere il cavo
     */
    public boolean togliCavo(char c){
        int pos = c - 'A';
        int corrispondente = scambia(c) - 'A';
        if(corrispondente == pos){
            return false;
        }
        cavi.set(corrispondente,'*');
        cavi.set(pos,'*');
        return true;
    }

    public char scambia(char c){
        int pos = c - 'A';
        if(cavi.get(pos) == '*'){
            return c;
        }
        return cavi.get(pos);
    }
}
