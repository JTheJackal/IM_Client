package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChatWindowController extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        ChatWindowUIController customControl = new ChatWindowUIController();
        
        stage.setScene(new Scene(customControl));
        stage.setTitle("Instant Messenger");
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
