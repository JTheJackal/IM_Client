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
	public boolean sendLoginInfoToServer(Object o) {
		boolean b = false;
		try {
			s = new Socket("127.0.0.1", 9999);
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(o);

			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

			//Message ms = (Message) ois.readObject();
			JSONObject ms = (JSONObject) ois.readObject();
			
			System.out.println("ClientConServer"+ms.toString());
			
			// check user login
			System.out.println("ClientConServer2"+ms.get("mesType").toString());
			if (ms.get("mesType").toString().equals("1")) {
				// create a thread connected between this user and server
				ClientConServerThread ccst = new ClientConServerThread(s);
				ccst.start();
				ManageClientConServerThread.addClientConServerThread(((User) o).getUserId(), ccst);
				b = true;
			} else {
				s.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return b;
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