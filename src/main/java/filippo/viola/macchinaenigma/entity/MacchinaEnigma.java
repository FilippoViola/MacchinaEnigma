package filippo.viola.macchinaenigma.entity;

import java.util.ArrayList;

public class MacchinaEnigma {
    private final ArrayList<Rotore> rotori;
    private final Riflettore rif;
    private final Scambiatore sc;
    private final int nRotori;

    public MacchinaEnigma(ArrayList<Rotore> rotori, Riflettore rif,Scambiatore sc) {
        this.rotori = rotori;
        this.nRotori = rotori.size();
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
        this.nRotori = 3;
    }

    public void ruotaRotori(boolean invertito){
        for (Rotore rotore : rotori) {
            boolean b = invertito ? rotore.ruotaInvertito() : rotore.ruota();
            if (!b) {
                break;
            }
        }
    }

    public void ruotaRotore(int indice, boolean invertito){
        if(invertito){
            rotori.get(indice).ruotaInizialeInvertito();
        } else {
            rotori.get(indice).ruotaIniziale();
        }
    }

    public void setCablaggioRotore(int indice, int cab){
        rotori.get(indice).setNumeroCablaggio(cab);
    }

    public int getNRotori(){
        return nRotori;
    }

    public char getRotazioneRotore(int indice){
        return rotori.get(indice).getRotazione();
    }

    public char codificaCarattere(char c){
        if(!Character.isLetter(c))
            return ' ';
        c = Character.toUpperCase(c);
        c = sc.scambia(c);
        ruotaRotori(false);
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
            char temp = codificaCarattere(c);
            if(temp == ' ')
                continue;
            codificata.append(temp);
        }
        return codificata.toString();
    }

    public void clear(){
        for(Rotore r : rotori){
            r.clear();
        }
    }
}
