package filippo.viola.macchinaenigma.entity;


public class Riflettore {;
        private String cablaggio;
        public Riflettore(String cablaggio) {
            this.cablaggio = cablaggio;
        }

        public char rifletti(char c) {
            int indice = c - 'A';
            return cablaggio.charAt(indice);
        }
    }

