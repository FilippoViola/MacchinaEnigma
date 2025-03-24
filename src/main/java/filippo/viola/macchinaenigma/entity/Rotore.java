package filippo.viola.macchinaenigma.entity;

import entity.Cablaggio;

public class Rotore {
    private int numeroCablaggio;
    private int rotazione;
    public Rotore(int numeroCablaggio, int rotazione) {
        this.numeroCablaggio = numeroCablaggio;
        this.rotazione = rotazione;
    }

    public char codifica(char c){
        int pos = (c - 'A' + rotazione) % 26;
        Cablaggio cab = Cablaggio.getCablaggio(numeroCablaggio);
        return (char)((cab.cablaggio[pos] + pos - rotazione + 26) % 26 + 'A');
    }

    public char codificaInvertito(char c){
        int pos = (c - 'A' + rotazione) % 26;
        Cablaggio cab = Cablaggio.getCablaggio(numeroCablaggio);
        return (char)((cab.cablaggioInvertito[pos] + pos + 26 - rotazione) % 26 + 'A');
    }

    public void ruota(){
        rotazione = (rotazione + 1) % 26;
    }

    public char getRotazione(){
        return (char)(rotazione + 'A');
    }

    public int getNumeroCablaggio(){
        return numeroCablaggio;
    }

    public void setNumeroCablaggio(int numeroCablaggio){
        this.numeroCablaggio = numeroCablaggio;
    }
}

