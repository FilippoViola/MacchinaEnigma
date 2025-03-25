package filippo.viola.macchinaenigma.entity;

import java.util.ArrayList;

public class MacchinaEnigma {
    private final ArrayList<Rotore> rotori;
    private final Riflettore rif;
    private final Scambiatore sc;
    public MacchinaEnigma(Rotore r1, Rotore r2, Rotore r3, Riflettore rif,Scambiatore sc) {
        this.rotori = new ArrayList<>();
        rotori.add(r1);
        rotori.add(r2);
        rotori.add(r3);
        this.rif = rif;
        this.sc = sc;
    }

    public MacchinaEnigma() {
        this.rotori = new ArrayList<>();
        this.rif = new Riflettore();
        sc = new Scambiatore();
        rotori.add(new Rotore(0,0));
        rotori.add(new Rotore(1,0));
        rotori.add(new Rotore(2,0));
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
        c = sc.scambia(c);
        ruotaRotori();
        for (Rotore rotore : rotori) {
            c = rotore.codifica(c);
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

    public void clear(){
        for(Rotore r : rotori){
            r.clear();
        }
    }
}
