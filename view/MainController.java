package view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collector.Characteristics;

import org.json.simple.JSONObject;

import model.MessageType;

import tools.ManageClientConServerThread;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;

import controlor.ClientUser;
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
	private ObjectInputStream ois;

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
	private JFXTextField friendNameToAdd;

	@FXML
	private Label username;

	@FXML
	private Label friendName;

	@FXML
	private JFXButton cancelAddFriendButton;

	@FXML
	private JFXButton addButton;

	@FXML
	private JFXListView<String> chatList;

	@FXML
	private JFXListView<String> contactList;

	@FXML
	private JFXListView<String> messageList;

	@FXML
	void addFriend(MouseEvent event) throws IOException {
		System.err.println("Add button pressed.");
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
	void findFriend(MouseEvent event) throws IOException {

		System.err.println("Finding requested friend...");
		

		if (friendNameToAdd.getText() != null) {
			// please provide a method to addFriend, the parameter is 'friendNameToAdd.getText()'
			// and add the call the method inside if(...)
			
			ClientUser clientUser = new ClientUser();

			JSONObject userObj = new JSONObject();
			userObj.put("userId", "user1"); //CHANGE ME ===============================================================================================================
			userObj.put("friendId", new String(friendNameToAdd.getText()));	
			userObj.put("messType", new String(MessageType.message_addFriend));

			if (clientUser.contactServer(userObj)) {//please provide a method to check (if exist) and create account
				String infoContent = "Add friend successsfully!";
				showInfo(infoContent);
			} else {
				String infoContent = "Failed: Sever Error";
				showInfo(infoContent);
			}
			
			
		} else {// fail to add (maybe there is no this user in database.)
			String infoContent = "Failed: please enter a user ID.";
			showInfo(infoContent);
			
		}
	}

	private void showInfo(String infoContent) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("info.fxml"));
		Parent root = loader.load();

		InfoController infoController = loader.getController();
		infoController.init(infoContent);
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();

	}

	@FXML
	void cancelAddFriend(MouseEvent event) throws IOException {

		System.err.println("Cancel add friend.");
		try {
			Stage stage = (Stage) cancelAddFriendButton.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void minimize(MouseEvent event) {
		Stage stage = (Stage) minimizeButton.getScene().getWindow();
		stage.setIconified(true);
	}

	@FXML
	void sendMessage(MouseEvent event) {

		System.err.println("---");
		System.err.println("I am sending a message");

		JSONObject messageObj = new JSONObject();
		messageObj.put("mesType", MessageType.message_comm_mes);
		messageObj.put("sender", ownerId);
		messageObj.put("getter", friendId);
		messageObj.put("con", ownerId + " :" + messageInputField.getText());
		messageObj.put("sendTime", new java.util.Date().toString());

		// send to server
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					ManageClientConServerThread.getClientConServerThread(ownerId).getS().getOutputStream());
			// oos.writeObject(m);
			oos.writeObject(messageObj);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Copy to chat window.
		messageList.getItems().add("You: " + messageInputField.getText());
		messageInputField.setText("");
	}

	@FXML
	void openChatView(MouseEvent event) {
		String friend = chatList.getSelectionModel().getSelectedItem();
		friendName.setText(friend);
		friendId = friend;
		messageList.getItems().clear();
	}

	@FXML
	void openChatInContact(MouseEvent event) {
		String friend = contactList.getSelectionModel().getSelectedItem();
		if (!chatList.getItems().contains(friend)) {
			chatList.getItems().add(friend);
		}
		friendName.setText(friend);
		friendId = friend;
		messageList.getItems().clear();
	}

	@FXML
	void closeStage(MouseEvent event) {
		// Stage stage = (Stage) mainClose.getScene().getWindow();
		// stage.close();
		try {
			Platform.exit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	// public void initData(User user) {
	public void initData(JSONObject userObject) {

		// Soon to be replaced by an online list of contacts retreived from the server.
		for (int i = 0; i < 20; i++) {
			contactList.getItems().add("user" + Integer.toString(i));
		}
		/*
		 * ownerId = user.getUserId(); username.setText(user.getUserId());
		 */

		ownerId = userObject.get("userId").toString();
		username.setText(ownerId);

		try {

			// Give the thread a reference to this controller.
			System.err.println("Setting controller in thread");
			ManageClientConServerThread.getClientConServerThread(ownerId).setController(this);
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * try { ois = new ObjectInputStream(
		 * ManageClientConServerThread.getClientConServerThread(ownerId).getS().
		 * getInputStream()); } catch (Exception e) { e.printStackTrace(); }
		 */

		/*
		 * JSONObject m; try { m = (JSONObject) ois.readObject();
		 * 
		 * if (m.get("mesType").toString().equals(MessageType.message_comm_mes)) {
		 * 
		 * System.out.println("Receiving a message from: " + m.get("sender"));
		 * 
		 * 
		 * } } catch (ClassNotFoundException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); }
		 */

	}

	public void showMessage(JSONObject chatObject) {

		System.out.println("I got a message");
		System.out.println("The message was from: " + chatObject.get("sender".toString()));

		messageList.getItems().add(chatObject.get("con").toString());

	}

}
