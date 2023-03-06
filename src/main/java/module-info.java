module com.c195.schedulingappbb {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.c195.schedulingappbb to javafx.fxml;
    exports com.c195.schedulingappbb;
    exports helper;
    opens helper to javafx.fxml;
}