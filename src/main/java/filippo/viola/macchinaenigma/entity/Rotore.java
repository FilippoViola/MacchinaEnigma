package filippo.viola.macchinaenigma.entity;

public class Rotore {
    private int numeroCablaggio;
    private int rotazione;
    private int rotazioneIniziale;
    public Rotore(int numeroCablaggio, int rotazione) {
        this.numeroCablaggio = numeroCablaggio;
        this.rotazione = rotazione;
        rotazioneIniziale = rotazione;
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


    public boolean ruota(){
        int r = rotazione;
        rotazione = (rotazione + 1) % 26;
        return r == Cablaggio.getCablaggio(numeroCablaggio).getPuntoScatto();
    }

    public void ruotaIniziale(){
        rotazioneIniziale = (rotazioneIniziale + 1) % 26;
        rotazione = rotazioneIniziale;
    }

    public void ruotaInizialeInvertito(){
        rotazioneIniziale = (rotazioneIniziale + 25) % 26;
        rotazione = rotazioneIniziale;
    }

    public boolean ruotaInvertito(){
        int r = rotazione;
        rotazione = (rotazione + 25) % 26;
        return r == Cablaggio.getCablaggio(numeroCablaggio).getPuntoScatto();
    }

    public boolean sulPuntoScatto(){
        return rotazione == Cablaggio.getCablaggio(numeroCablaggio).getPuntoScatto();
    }

    public void clear(){
        this.rotazione = rotazioneIniziale;
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

    public void setRotazione(int rotazione){
        this.rotazione = rotazione % 26;
        this.rotazioneIniziale = rotazione;
    }
}

