package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collector.Characteristics;

import model.User;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXScrollPane;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController implements Initializable {

	private static final String ITEM = "Item ";

	@FXML
	private MaterialDesignIconView mainClose;

	@FXML
	private MaterialDesignIconView minimizeButton;

	@FXML
	private MaterialDesignIconView addFriendButton;

	@FXML
	private MaterialDesignIconView sendMessage;

	@FXML
	private JFXScrollPane chatListPane;

	@FXML
	private Label username;

	@FXML
	private JFXListView<String> chatList;
	
    @FXML
    private JFXListView<String> messageList;

	@FXML
	void addFriend(MouseEvent event) throws IOException {
		System.err.println("....");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("SearchFriend.fxml"));
		Parent root = loader.load();
//		root.setEffect(new DropShadow());
		Scene scene = new Scene(root);
//		scene.setFill(Color.TRANSPARENT);
		Stage stage = new Stage();
//		stage.getScene().getRoot().setEffect(new DropShadow());
		stage.setScene(scene);
		stage.initStyle(StageStyle.UNDECORATED);

		stage.show();
	}

	@FXML
	void minimize(MouseEvent event) {
		Stage stage = (Stage) minimizeButton.getScene().getWindow();
		stage.setIconified(true);
	}

	@FXML
	void sendMessage(MouseEvent event) {
		messageList.getItems().add("...");
	}

	@FXML
	void closeStage(MouseEvent event) {
		 Platform.exit();
		 System.exit(0);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		chatList.getItems().add("1");
		messageList.getItems().add("...");

	}

	public void initData(User user) {
		username.setText(user.getUserId());
	}

	// public void initData(User user) {
	// System.out.println(user.getUserId()+"....");
	// this.username.setText(user.getUserId());
	// }

}
