/**
 * this is a class to communicate with server
 * offer method: check user
 * 
 */

package controlor;

import org.json.simple.JSONObject;

import model.*;

public class ClientUser {

	/*
	public boolean checkUser(User u) {
		return new ClientConServer().sendLoginInfoToServer(u);
	}
	*/
	
	public boolean contactServer(JSONObject u) {
		return new ClientConServer().sendInfoToServer(u);
	}
}