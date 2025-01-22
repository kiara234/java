module org.example.java_lika_tcholokava {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires static lombok;

    opens org.example.java_lika_tcholokava to javafx.fxml;
    exports org.example.java_lika_tcholokava;
}