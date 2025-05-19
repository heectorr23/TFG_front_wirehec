module com.wirehec.front_wirehec {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires static lombok;
    requires java.sql;

    opens com.wirehec.front_wirehec to javafx.fxml;
    exports com.wirehec.front_wirehec;
}