package view;

import java.io.IOException;
import java.io.ObjectOutputStream;

import org.json.simple.JSONObject;

import controlor.ClientUser;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.MessageType;
import model.User;
import tools.ManageClientConServerThread;
import tools.ManageFriendList;

/**
 * Sample custom control hosting a text field and a button.
 */
public class ClientLoginUIController extends VBox{
    @FXML TextField userIDField;
    @FXML PasswordField passwordField;
    @FXML Stage stage;

    public ClientLoginUIController(Stage stage) {
    	this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("clientLogInUI.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    
    public String getText() {
        return textProperty().get();
    }
    
    public void setText(String value) {
        textProperty().set(value);
    }
    
    public StringProperty textProperty() {
        return userIDField.textProperty();                
    }
        
    @FXML
    protected void doSomething() {
        System.out.println("The button was clicked!");
    }
    
    @SuppressWarnings("unchecked")
	@FXML
    protected void attemptLogIn() {
    	System.out.println("Attempting Log in");
    	
    	// if user click login button
		//Create a new user object
		ClientUser qqClientUser = new ClientUser();
		User u = new User();
		
		System.out.println("User Created");
		//Set the user's username and password which was entered in the window
		u.setUserId(userIDField.getText().trim());
		u.setPasswd(new String(passwordField.getText()));
		//u.setPasswd(new String("123456"));

		System.out.println("User Prepared. Sending...");
		//Check the user log in info by sending it to the server
		//If successful...
		if (qqClientUser.checkUser(u)) {
			
			System.out.println("Log In Successful");
			stage.close();
			try {
				//Prepare friend list
				FriendList qqList = new FriendList(u.getUserId());
				ManageFriendList.addQqFriendList(u.getUserId(), qqList);

				ObjectOutputStream oos = new ObjectOutputStream(ManageClientConServerThread
						.getClientConServerThread(u.getUserId()).getS().getOutputStream());
				
				JSONObject m = new JSONObject();
				System.out.println("ClientLogin: "+m.toString());
				m.put("mesType", MessageType.message_get_onLineFriend);
				m.put("sender", u.getUserId());
				oos.writeObject(m);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
    }
    
    @FXML
    protected void attemptRegistration() {
    	System.out.println("Attempting to register");
    }
}
