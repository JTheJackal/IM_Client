/**
 * this is a chat page
 * because clients should be a read state,
 * so we can make is as a thread
 * 
 */
package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.json.simple.JSONObject;

import controlor.ClientConServer;
import tools.ManageClientConServerThread;
import model.MessageType;

public class Chat extends JFrame implements ActionListener {

	JTextArea jta;
	JTextField jtf;
	JButton jb;
	JPanel jp;
	String ownerId;
	String friendId;

	public static void main(String[] args) {
		// QqChat qqChat=new QqChat("1","1");
	}

	public Chat(String ownerId, String friend) {
		this.ownerId = ownerId;
		this.friendId = friend;
		//jta = new JTextArea();
		//jtf = new JTextField(15);
		//jb = new JButton("sent");
		//jb.addActionListener(this);
		//jp = new JPanel();
		//jp.add(jtf);
		//jp.add(jb);

		//this.add(jta, "Center");
		//this.add(jp, "South");
		//this.setTitle(ownerId + " is talking with" + friend);
		//this.setIconImage((new ImageIcon("image/qq.gif").getImage()));
		//this.setSize(300, 200);
		//this.setVisible(true);

	}

	// show message
	public void showMessage(JSONObject m) {
		System.out.println("A message was received.");
		System.out.println("Chat"+m.toString());
		String info = m.get("sender") + " said to " + m.get("getter") + " :" + m.get("connection") + "\r\n";
		//this.jta.append(info);
	}
	
	public void sendMessage(JSONObject m) {
		
		JSONObject messageObj = new JSONObject();
		messageObj.put("mesType", MessageType.message_comm_mes);
		messageObj.put("sender", this.ownerId);
		messageObj.put("getter", this.friendId);
		messageObj.put("con", jtf.getText());
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

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
