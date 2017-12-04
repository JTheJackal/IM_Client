package view;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collector.Characteristics;

import org.json.simple.JSONObject;

import model.MessageType;

import tools.ManageClientConServerThread;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;

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

	private String ownerId;
	private String friendId;

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
	private JFXTabPane tabPane;

	@FXML
	private JFXTextField messageInputField;

	@FXML
	private Label username;

	@FXML
	private Label friendName;

	@FXML
	private JFXListView<String> chatList;

	@FXML
	private JFXListView<String> contactList;

	@FXML
	private JFXListView<String> messageList;

	@FXML
	void addFriend(MouseEvent event) throws IOException {
		System.err.println("....");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("SearchFriend.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		Stage stage = new Stage();
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
		
		/*
		Message m = new Message();
		m.setMesType(MessageType.message_comm_mes);
		m.setSender(ownerId);
		m.setGetter("2");
		m.setCon(messageInputField.getText());
		m.setSendTime(new java.util.Date().toString());
		*/
		
		JSONObject messageObj = new JSONObject();
		messageObj.put("mesType", MessageType.message_comm_mes);
		messageObj.put("sender", ownerId);
		messageObj.put("getter", "2");
		messageObj.put("con", messageInputField.getText());
		messageObj.put("sendTime", new java.util.Date().toString());
		// send to server
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					ManageClientConServerThread.getClientConServerThread(ownerId).getS().getOutputStream());
			//oos.writeObject(m);
			oos.writeObject(messageObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void openChatView(MouseEvent event) {
		String friend = chatList.getSelectionModel().getSelectedItem();
		friendName.setText(friend);
		friendId = friend;
	}

	@FXML
	void openChatInContact(MouseEvent event) {
		String friend = contactList.getSelectionModel().getSelectedItem();
		if (!chatList.getItems().contains(friend)) {
			chatList.getItems().add(friend);
		}
		friendName.setText(friend);
		friendId = friend;
	}

	@FXML
	void closeStage(MouseEvent event) {
//		Stage stage = (Stage) mainClose.getScene().getWindow();
//		stage.close();
		try {
			Platform.exit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	//public void initData(User user) {
	public void initData(JSONObject user) {
		for (int i = 0; i < 20; i++) {
			contactList.getItems().add(Integer.toString(i));
		}
		/*
		ownerId = user.getUserId();
		username.setText(user.getUserId());
		*/
		ownerId = user.get("userId").toString();
		username.setText(ownerId);
		
	}

}
