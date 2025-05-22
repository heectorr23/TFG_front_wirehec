module com.wirehec.front_wirehec {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;
    requires java.logging;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires static lombok;
    requires java.sql;

    opens com.wirehec.front_wirehec to javafx.fxml;
    opens com.wirehec.front_wirehec.DTO to javafx.base;
    opens com.wirehec.front_wirehec.Controllers to javafx.fxml;

    exports com.wirehec.front_wirehec;
    exports com.wirehec.front_wirehec.Controllers;
}



