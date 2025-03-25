package filippo.viola.macchinaenigma;

import filippo.viola.macchinaenigma.entity.MacchinaEnigma;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class EnigmaController {
    @FXML
    public TextField inputText;
    @FXML
    public TextField outputText;
    @FXML
    private GridPane gridBottoni;
    @FXML
    private TextField txtInput;

    MacchinaEnigma me;

    private Button bottoni[];

    @FXML
    void initialize(){
        me = new MacchinaEnigma();
        bottoni = new Button[27];
        char lettera = 'A';
        gridBottoni.setVgap(10);
        gridBottoni.setHgap(10);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                bottoni[i * 9 + j] = new Button("" + lettera);
                bottoni[i * 9 + j].setPrefWidth(60);
                final char letteraF = lettera;
                bottoni[i * 9 + j].setOnAction(e -> {
                    System.out.println("Ciao " + e.getSource());
                    System.out.println("Hai premuto la lettera " + letteraF);
                });
                //bottoni[i*9+j].setOnMouseClicked(new Eve);
                gridBottoni.add(bottoni[i * 9 + j], j, i);
                lettera++;
                if (lettera == '[') return;
            }
        }
    }

    void aggiungiCarattere(){

    }

    @FXML
    void decodeAndOutput(){
        me.clear();
        String text = inputText.getText();
        System.out.println(text);
        outputText.setText(me.codificaStringa(text));
    }
}