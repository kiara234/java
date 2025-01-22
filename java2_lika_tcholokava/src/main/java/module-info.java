module org.example.java2_lika_tcholokava {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens org.example.java2_lika_tcholokava to javafx.fxml;
    exports org.example.java2_lika_tcholokava;
}