package filippo.viola.macchinaenigma.entity;

import java.util.ArrayList;

public class MacchinaEngima {
    private ArrayList<Rotore> rotori;
    private Riflettore rif;

    public MacchinaEngima(Rotore r1, Rotore r2, Rotore r3, Riflettore rif) {
        this.rotori = new ArrayList<>();
        rotori.add(r1);
        rotori.add(r2);
        rotori.add(r3);
        this.rif = rif;
    }

    public void ruotaRotori(){
        rotori.getFirst().ruota();
        for (int i = 1; i < rotori.size(); i++) {
            if (rotori.get(i-1).getRotazione() == 0){
                rotori.get(i-1).ruota();
            } else {
                break;
            }
        }
    }

    public char codificaCarattere(char c){
        c = Character.toUpperCase(c);
        ruotaRotori();
        for (int i = 0; i < rotori.size(); i++) {
            c = rotori.get(i).codifica(c);
        }

        c = rif.rifletti(c);

        for (int i = rotori.size()-1; i >= 0; i--) {
            c = rotori.get(i).codificaInvertito(c);
        }

        return c;
    }

    public String codificaStringa(String s){
        StringBuilder codificata = new StringBuilder();
        for(char c : s.toCharArray()){
            codificata.append(codificaCarattere(c));
        }
        return codificata.toString();
    }
}
