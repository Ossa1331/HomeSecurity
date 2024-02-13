module com.example.homesecurity {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires java.sql;


    opens com.example.homesecurity to javafx.fxml;
    exports com.example.homesecurity;
}