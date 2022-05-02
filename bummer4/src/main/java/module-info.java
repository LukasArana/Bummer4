module com.example.bummer4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires okhttp3;
    requires com.google.gson;
    requires batik.transcoder;
    requires java.desktop;
    requires javafx.swing;
    opens com.bummer4 to javafx.fxml, com.google.gson;
    exports com.bummer4;
}