module com.example.mykursova {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.sql;
    requires mysql.connector.j;


    opens com.example.mykursova to javafx.fxml;
    opens com.example.mykursova.taxes to javafx.fxml;
    exports com.example.mykursova;
    exports com.example.mykursova.taxes;
}