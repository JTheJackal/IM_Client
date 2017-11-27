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
			System.out.println("Creating new socket");
			s = new Socket("127.0.0.1", 9999);
			System.out.println("Creating output stream");
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			System.out.println("Writing object");
			oos.writeObject(o);

			System.out.println("Creating input stream");
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

			//Message ms = (Message) ois.readObject();
			System.out.println("Constructing json object from inbound data");
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