package filippo.viola.macchinaenigma;

import filippo.viola.macchinaenigma.entity.Cablaggio;
import filippo.viola.macchinaenigma.entity.MacchinaEnigma;
import filippo.viola.macchinaenigma.util.NumberUtil;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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
    public GridPane scambiatori;

    @FXML
    private GridPane gridBottoni;

    private MacchinaEnigma me;

    private Button bottoni[];

    private TextField rotazioni[];

    private boolean haRuotato = false;

    private static final int N_SCAMBIATORI = 6;


    private static final UnaryOperator<TextFormatter.Change> UPPERCASE_FILTER = change -> {
        if (change.getText() != null) {
            change.setText(change.getText().toUpperCase());
        }
        return change;
    };

    private void aggiornaRotazioni(){
        for (int i = 0; i < rotazioni.length; i++){
            rotazioni[i].setText(me.getRotazioneRotore(i) + "-");
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

    private void addRowColToGrid(GridPane grid, int rows, int rowHeight, int cols, int colWidth){
        for (int i = 0; i < rows; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPrefHeight(rowHeight);
            grid.getRowConstraints().add(rowConstraints);
        }
        for(int i = 0; i < cols; i++){
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPrefWidth(colWidth);
            grid.getColumnConstraints().add(columnConstraints);
        }
    }

    private void initScambiatori(){
        addRowColToGrid(scambiatori,1,50,N_SCAMBIATORI,100);
        scambiatori.setAlignment(Pos.CENTER);
        for(int i = 0; i < N_SCAMBIATORI; i++){
            TextField sc = new TextField();
            sc.setMaxWidth(80);
            scambiatori.add(sc,i,0);
            allineaAlcentro(sc);
            sc.setTextFormatter(new TextFormatter<>(UPPERCASE_FILTER));
            sc.textProperty().addListener(new ChangeListener<>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    sc.textProperty().removeListener(this);
                    try {
                        if (newValue.isEmpty()) {
                            return;
                        }
                        if (!newValue.matches("^[A-Z]*$")) {
                            sc.setText(oldValue);
                            return;
                        }

                        if (oldValue.length() == 2) {
                            me.getScambiatore().togliCavo(oldValue);
                        }
                        if (newValue.length() == 2) {
                            if (!me.getScambiatore().aggiungiCavo(newValue)) {
                                Platform.runLater(() -> sc.setText(""));
                            }
                        } else if (newValue.length() >= 3) {
                            sc.setText(newValue.substring(0, 2));
                        }
                    } finally {
                        sc.textProperty().addListener(this);
                    }
                }
            });
        }
    }


    private static void allineaAlcentro(Node n){
        GridPane.setHalignment(n, HPos.CENTER);
        GridPane.setValignment(n, VPos.CENTER);
    }

    private void initSingoloRotore(int indice){
        // invertire indice
        final int indiceGrid = me.getNRotori() - indice - 1;
        //cambiare il cablaggio
        ComboBox<String> c = new ComboBox<>();
        for (int i = 1; i <= Cablaggio.getTotaleCabaggi(); i++) {
            c.getItems().add(NumberUtil.convertiInRomano(i));
        }
        c.setValue(NumberUtil.convertiInRomano(me.getCablaggio(indice)+1));
        c.setOnAction(e -> {
            me.setCablaggioRotore(indice,NumberUtil.convertiDaRomano(c.getValue()) - 1);
        });
        c.showingProperty().addListener((_, _, newValue) -> {
            if (haRuotato && newValue) {
                c.hide(); // Blocca l'apertura
            }
        });

        gridRotori.add(c, indiceGrid, 0);
        TextField textRotazione = new TextField();
        textRotazione.setPrefWidth(20);
        textRotazione.setAlignment(Pos.CENTER);
        textRotazione.setTextFormatter(new TextFormatter<>(UPPERCASE_FILTER));
        textRotazione.textProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                textRotazione.textProperty().removeListener(this);
                try {
                    if(newValue.length() == 2 && newValue.charAt(1) == '-'){
                        textRotazione.setText(String.valueOf(newValue.charAt(0)));
                    } else if (newValue.isEmpty() || !newValue.matches("^[A-Z]+$") || haRuotato) {
                        textRotazione.setText(oldValue);
                    } else {
                        char c = newValue.charAt(newValue.length() - 1);
                        textRotazione.setText(String.valueOf(c));
                        me.setRotazioneRotore(indice, c - 'A');
                    }
                } finally {
                    textRotazione.textProperty().addListener(this);
                }
            }
        });

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
        allineaAlcentro(b);

        textRotazione.setText(String.valueOf(me.getRotazioneRotore(indice)));
        gridRotori.add(textRotazione,indiceGrid,2);
        allineaAlcentro(textRotazione);

        b = new Button();
        b.setText("-");
        b.setOnAction(_ -> {
            if(haRuotato)
                return;
            me.ruotaRotore(indice,true);
            rotazioni[indice].setText(String.valueOf(me.getRotazioneRotore(indice)));
        });

        gridRotori.add(b,indiceGrid,3);
        allineaAlcentro(b);
    }

    private void initRotori(){
        rotazioni = new TextField[me.getNRotori()];
        addRowColToGrid(gridRotori,4,50,me.getNRotori(),60);
        GridPane.setHalignment(gridRotori, HPos.CENTER);
        for(int i = 0; i < me.getNRotori(); i++){
            initSingoloRotore(i);
        }
    }

    private void initBottoni(){
        bottoni = new Button[27];
        gridBottoni.setVgap(10);
        gridBottoni.setHgap(10);

        addRowColToGrid(gridBottoni,3,50,9,60);

        int pos = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                char lettera = (char)(pos + 'A');
                Button b = new Button();
                gridBottoni.add(b,j,i);
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
        initScambiatori();
        initRotori();
        initBottoni();
        inputText.setTextFormatter(new TextFormatter<>(UPPERCASE_FILTER));
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