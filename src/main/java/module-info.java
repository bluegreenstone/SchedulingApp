module com.c195.schedulingappbb {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.c195.schedulingappbb to javafx.fxml;
    exports com.c195.schedulingappbb;
}