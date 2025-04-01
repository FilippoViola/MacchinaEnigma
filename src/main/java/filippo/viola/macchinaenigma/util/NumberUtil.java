package filippo.viola.macchinaenigma.util;

import java.util.HashMap;
import java.util.Map;

public class NumberUtil {

    public static String convertiInRomano(int numero) {
        if (numero <= 0 || numero > 3999) {
            throw new IllegalArgumentException("Il numero deve essere tra 1 e 3999.");
        }

        String[] migliaia = {"", "M", "MM", "MMM"};
        String[] centinaia = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] decine = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] unita = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

        return migliaia[numero / 1000] +
                centinaia[(numero % 1000) / 100] +
                decine[(numero % 100) / 10] +
                unita[numero % 10];
    }

    public static int convertiDaRomano(String numeroRomano) {
        Map<Character, Integer> valori = new HashMap<>();
        valori.put('I', 1);
        valori.put('V', 5);
        valori.put('X', 10);
        valori.put('L', 50);
        valori.put('C', 100);
        valori.put('D', 500);
        valori.put('M', 1000);

        int totale = 0;
        int precedente = 0;

        for (int i = numeroRomano.length() - 1; i >= 0; i--) {
            char c = numeroRomano.charAt(i);
            int valore = valori.get(c);

            if (valore < precedente) {
                totale -= valore;
            } else {
                totale += valore;
            }

            precedente = valore;
        }

        return totale;
    }
}
