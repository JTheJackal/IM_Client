package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.simple.JSONObject;

import controlor.ClientUser;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.MessageType;

public class SigninController implements Initializable {

	private double xOffset = 0;
	private double yOffset = 0;

	@FXML
	private MaterialDesignIconView signinClose;

	@FXML
	private MaterialDesignIconView signinMinimize;

	@FXML
	private AnchorPane cardPane;

	@FXML
	private AnchorPane allPane;

	@FXML
	private JFXTextField usernameField;

	@FXML
	private JFXPasswordField passwordField;

	@FXML
	private JFXButton signIn;

	@FXML
	void closeStage(MouseEvent event) {
		try {
			Platform.exit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void minimizeStage(MouseEvent event) {
		Stage stage = (Stage) signinMinimize.getScene().getWindow();
		stage.setIconified(true);
		System.out.println("123");
	}

	@FXML
	void signIn(ActionEvent event) throws IOException {
		System.out.println(passwordField.getText() + usernameField.getText());

		ClientUser qqClientUser = new ClientUser();
		
		JSONObject userObj = new JSONObject();
		userObj.put("userId", usernameField.getText());
		userObj.put("passwd", new String(passwordField.getText()));
		userObj.put("messType", new String(MessageType.message_login));
		
		/*
		User u = new User();
		u.setUserId(usernameField.getText());
		u.setPasswd(new String(passwordField.getText()));
		*/

		/*
		if (qqClientUser.checkUser(u)) {
			openMainStage(u);
		*/
		
		if(qqClientUser.checkUser(userObj)) {
			openMainStage(userObj);
		} else {
			System.err.println("...........");
		}
	}

	//private void openMainStage(User u) throws IOException {
	private void openMainStage(JSONObject u) throws IOException{
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Main.fxml"));
		Parent root = loader.load();

		MainController mainController = loader.getController();
		mainController.initData(u);
		Scene scene = new Scene(root);
		Stage stage = (Stage) signIn.getScene().getWindow();
		root.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stage.setX(event.getScreenX() - xOffset);
				stage.setY(event.getScreenY() - yOffset);
			}
		});
		stage.setScene(scene);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		JFXDepthManager.setDepth(cardPane, 2);
		// JFXDepthManager.setDepth(allPane, 2);

	}

}