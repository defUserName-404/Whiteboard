module org.defusername {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.defusername to javafx.fxml;
    exports org.defusername;
	exports org.defusername.controllers;
	opens org.defusername.controllers to javafx.fxml;
}
