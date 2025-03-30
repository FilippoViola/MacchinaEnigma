package filippo.viola.macchinaenigma;

import filippo.viola.macchinaenigma.entity.MacchinaEnigma;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

import java.util.function.UnaryOperator;

public class EnigmaController {
    @FXML
    public TextArea inputText;
    @FXML
    public TextArea outputText;
    @FXML
    public GridPane gridRotori;
    @FXML
    private GridPane gridBottoni;

    private MacchinaEnigma me;

    private Button bottoni[];

    private TextField rotazioni[];

    private boolean haRuotato = false;

    private void aggiornaRotazioni(){
        for (int i = 0; i < rotazioni.length; i++){
            System.out.println(me.getRotazioneRotore(i));
            rotazioni[i].setText(String.valueOf(me.getRotazioneRotore(i)));
        }
    }

    private void aggiungiCarattere(char c){
        inputText.appendText(String.valueOf(c));
        if(!Character.isLetter(c)){
            return;
        }
        haRuotato = true;
        outputText.appendText(String.valueOf(me.codificaCarattere(c)));
        aggiornaRotazioni();
    }

    private void cancellaCarattere(){
        String inText = inputText.getText();
        if (inText != null && !inText.isEmpty()) {
            // cancella l'ultimo carattere
            char ultimoCarattere = inText.charAt(inText.length() - 1);
            inputText.setText(inText.substring(0, inText.length() - 1));
            if(Character.isLetter(ultimoCarattere)){
                String outText = outputText.getText();
                outputText.setText(outText.substring(0,outText.length() - 1));
                me.ruotaRotori(true);
                aggiornaRotazioni();
                haRuotato = !outputText.getText().isBlank();
            }
        }
    }

    private void initSingoloRotore(int indice){
        // invertire indice
        final int indiceGrid = me.getNRotori() - indice - 1;
        //TODO cambiare il cablaggio

        TextField textRotazione = new TextField();
        rotazioni[indice] = textRotazione;

        // ruotare
        Button b = new Button();
        b.setText("+");
        b.setOnAction(_ -> {
            if(haRuotato)
                return;
            me.ruotaRotore(indice,false);
            rotazioni[indice].setText(String.valueOf(me.getRotazioneRotore(indice)));
        });
        gridRotori.add(b,indiceGrid,1);

        textRotazione.setText(String.valueOf(me.getRotazioneRotore(indice)));
        gridRotori.add(textRotazione,indiceGrid,2);

        b = new Button();
        b.setText("-");
        b.setOnAction(_ -> {
            if(haRuotato)
                return;
            me.ruotaRotore(indice,true);
            rotazioni[indice].setText(String.valueOf(me.getRotazioneRotore(indice)));
        });

        gridRotori.add(b,indiceGrid,3);
    }

    private void initRotori(){
        rotazioni = new TextField[me.getNRotori()];

        for (int i = 0; i < 4; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPrefHeight(50);
            gridRotori.getRowConstraints().add(rowConstraints);
        }
        for(int i = 0; i < me.getNRotori(); i++){
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPrefWidth(50);
            gridRotori.getColumnConstraints().add(columnConstraints);
        }
        for(int i = 0; i < me.getNRotori(); i++){
            initSingoloRotore(i);
        }
    }

    private void initBottoni(){
        bottoni = new Button[27];
        gridBottoni.setVgap(10);
        gridBottoni.setHgap(10);

        for (int i = 0; i < 3; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPrefHeight(50);
            gridBottoni.getRowConstraints().add(rowConstraints);
        }
        for (int i = 0; i < 9; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPrefWidth(60);
            gridBottoni.getColumnConstraints().add(columnConstraints);
        }

        int pos = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                char lettera = (char)(pos + 'A');
                Button b = new Button();
                gridBottoni.add(b,j,i);
                b.setPrefWidth(40);
                b.setPrefWidth(60);
                GridPane.setHgrow(b, Priority.ALWAYS);
                if (lettera == '['){
                    b.setText("canc");
                    b.setOnAction(_ -> cancellaCarattere());
                    break;
                }
                b.setText(String.valueOf(lettera));
                b.setOnAction(_ -> aggiungiCarattere(lettera));
                bottoni[pos++] = b;
            }
        }

    }

    @FXML
    private void initialize(){
        me = new MacchinaEnigma();
        initRotori();
        initBottoni();
        UnaryOperator<TextFormatter.Change> filter = change -> {
            if (change.getText() != null) {
                change.setText(change.getText().toUpperCase());
            }
            return change;
        };
        inputText.setTextFormatter(new TextFormatter<>(filter));
    }

    @FXML
    private void decodeAndOutput(){
        me.clear();
        String text = inputText.getText();
        outputText.setText(me.codificaStringa(text));
        haRuotato = !outputText.getText().isBlank();
        aggiornaRotazioni();
    }
}