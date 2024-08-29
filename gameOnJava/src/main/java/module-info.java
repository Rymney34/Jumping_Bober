module com.example.gameonjava {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gameonjava to javafx.fxml;
    exports com.example.gameonjava;
}