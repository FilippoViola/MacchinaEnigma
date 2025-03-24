module filippo.viola.macchinaenigma {
    requires javafx.controls;
    requires javafx.fxml;


    opens filippo.viola.macchinaenigma to javafx.fxml;
    exports filippo.viola.macchinaenigma;
}