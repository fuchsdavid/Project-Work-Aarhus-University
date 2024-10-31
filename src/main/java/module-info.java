module com.example.habittracker {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires jakarta.mail;

    requires java.persistence;
    requires java.transaction;
    requires java.sql;
    requires org.hibernate.orm.core;
    opens org.database to org.hibernate.orm.core;


    opens com.example.habittracker to javafx.fxml;

    exports com.example.habittracker;
}