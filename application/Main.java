package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import view.ClientLoginUIController;


public class Main extends Application {

	@Override
	public void start(Stage stage) throws Exception {
        ClientLoginUIController customControl = new ClientLoginUIController(stage);
        
        stage.setScene(new Scene(customControl));
        stage.setTitle("Instant Messenger");
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
