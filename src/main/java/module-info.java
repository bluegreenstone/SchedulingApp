module com.c195.schedulingappbb {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.c195.schedulingappbb to javafx.fxml;
    exports com.c195.schedulingappbb;
    exports DAO;
    exports model;
    opens DAO to javafx.fxml;
    opens model to javafx.base;
}