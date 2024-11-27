module ChatJavaUI {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.sql;
	requires java.base;
	
	opens application to javafx.graphics, javafx.fxml;
	   opens Controller to javafx.fxml; // This line is crucial
	    
}
