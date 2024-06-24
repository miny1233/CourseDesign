module com.example.coursedesign {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.coursedesign to javafx.fxml;
    exports com.example.coursedesign;
}