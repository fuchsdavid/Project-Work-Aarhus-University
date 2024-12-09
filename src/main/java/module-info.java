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
    requires java.xml.crypto;
    opens org.database to org.hibernate.orm.core;


    requires google.api.client;
    requires com.google.api.client.auth;
    requires com.google.api.client;
    requires com.google.api.client.json.gson;
    requires google.api.services.calendar.v3.rev411;
    requires com.google.api.client.extensions.jetty.auth;
    requires com.google.api.client.extensions.java6.auth;
    requires jdk.httpserver;

    opens com.example.habittracker to javafx.fxml;
    exports com.example.habittracker;
    exports com.example.habittracker.utils;
    opens com.example.habittracker.utils to javafx.fxml;
}