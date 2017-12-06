/**
 * this is a backend that client connect to the server
 */
package controlor;

import tools.*;
import model.*;

import java.util.*;

import org.json.simple.JSONObject;

import java.net.*;
import java.io.*;

public class ClientConServer {

	public Socket s;

	// request firstly
	//public boolean sendLoginInfoToServer(Object o) {
	public boolean sendInfoToServer(JSONObject o) {

		boolean status = false;
		try {
			s = new Socket("127.0.0.1", 9999);
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(o);

			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

			//Message ms = (Message) ois.readObject();
			JSONObject ms = (JSONObject) ois.readObject();
			
			System.out.println("ClientConServer object: "+ms.toString());
			
			// check user login
			System.out.println("ClientConServer messageType: "+ms.get("messType").toString());
			
			if (ms.get("messType").toString().equals(MessageType.message_LoginSuccess)) {
				// create a thread connected between this user and server
				ClientConServerThread ccst = new ClientConServerThread(s);
				ccst.start();
				//ManageClientConServerThread.addClientConServerThread(((User) o).getUserId(), ccst);
				ManageClientConServerThread.addClientConServerThread( o.get("userId").toString(), ccst);

				status = true;
			} else if (ms.get("messType").toString().equals(MessageType.message_createAccSuccess)){
				status = true;
			}
			else {
				s.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return status;
	}

	public void SendInfoToServer(Object o) {
		/*
		 * try { Socket s=new Socket("127.0.0.1",9999);
		 * 
		 * } catch (Exception e) { e.printStackTrace(); // TODO: handle
		 * exception }finally{
		 * 
		 * }
		 */
	}
}