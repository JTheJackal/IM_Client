/**
 * this is a thread between Client and Server
 */
package tools;

import java.io.*;
import java.net.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import view.Chat;
import view.FriendList;
import model.*;

public class ClientConServerThread extends Thread {

	private Socket s;

	public ClientConServerThread(Socket s) {
		this.s = s;
	}

	public void run() {
		while (true) {
			try {

				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				
				JSONObject m = (JSONObject) ois.readObject();
				
				System.out.println("ClientConServerThread"+m.toString());
						
				if (m.get("mesType").toString().equals(MessageType.message_comm_mes)) {
					Chat chat = ManageChat.getChat(m.get("getter") + " " + m.get("sender"));
					chat.showMessage(m);
				}
				
				else if (m.get("mesType").toString().equals(MessageType.message_ret_onLineFriend)) {
					String con = m.get("connection").toString();
					System.out.println(con);
					String friends[] = con.split(" ");
					String getter = m.get("getter").toString();
					System.out.println("getter=" + getter);

					FriendList qqFriendList = ManageFriendList.getQqFriendList(getter);

					// update Friend
					if (qqFriendList != null) {
						qqFriendList.upateFriend(m);
					}
				}
				
				
				/*
				Message m = (Message) ois.readObject();
				
				if (m.getMesType().equals(MessageType.message_comm_mes)) {

					// show message which is from server on Chat page
					
				} else if (m.getMesType().equals(MessageType.message_ret_onLineFriend)) {
					String con = m.getCon();
					String friends[] = con.split(" ");
					String getter = m.getGetter();
					System.out.println("getter=" + getter);

					FriendList qqFriendList = ManageFriendList.getQqFriendList(getter);

					// update Friend
					if (qqFriendList != null) {
						qqFriendList.upateFriend(m);
					}
				}
				*/
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Socket getS() {
		return s;
	}

	public void setS(Socket s) {
		this.s = s;
	}

}