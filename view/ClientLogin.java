/**
 * function：client login page
 */
package view;

import java.io.*;
import javax.swing.*;

import org.json.simple.JSONObject;

import controlor.ClientUser;
import tools.*;
import model.*;

import java.awt.*;
import java.awt.event.*;

public class ClientLogin extends JFrame implements ActionListener {


	JLabel jbl1;

	JTabbedPane jtp;
	JPanel jPanel2, jp3, jp4;
	JLabel jPanel2_jbl1, jPanel2_jbl2, jPanel2_jbl3, jPanel2_jbl4;
	JButton jPanel2_jb1;
	JTextField jPanel2_jtf;
	JPasswordField jPanel2_jpf;
	JCheckBox jPanel2_jcb1, jPanel2_jcb2;

	JPanel jPanel1;
	JButton logInBTN, cancelBTN, registerBTN;

	public static void main(String[] args) {
		ClientLogin qqClientLogin = new ClientLogin();
	}

	public ClientLogin() {
		jbl1 = new JLabel();

		jPanel2 = new JPanel(new GridLayout(3, 3));

		jPanel2_jbl1 = new JLabel("UserID", JLabel.CENTER);
		jPanel2_jbl2 = new JLabel("Password", JLabel.CENTER);
		jPanel2_jbl3 = new JLabel("Forgot password", JLabel.CENTER);
		jPanel2_jbl3.setForeground(Color.blue);
		jPanel2_jbl4 = new JLabel("Applying password protection", JLabel.CENTER);
		jPanel2_jb1 = new JButton("CLear Number");
		jPanel2_jtf = new JTextField();
		jPanel2_jpf = new JPasswordField();
		jPanel2_jcb1 = new JCheckBox("Invisible login");
		jPanel2_jcb2 = new JCheckBox("Remember password");

		jPanel2.add(jPanel2_jbl1);
		jPanel2.add(jPanel2_jtf);
		jPanel2.add(jPanel2_jb1);
		jPanel2.add(jPanel2_jbl2);
		jPanel2.add(jPanel2_jpf);
		jPanel2.add(jPanel2_jbl3);
		jPanel2.add(jPanel2_jcb1);
		jPanel2.add(jPanel2_jcb2);
		jPanel2.add(jPanel2_jbl4);

		jtp = new JTabbedPane();
		jtp.add("User Account", jPanel2);
		/*
		jp3 = new JPanel();
		jtp.add("Mobile phone", jp3);
		jp4 = new JPanel();
		jtp.add("E-mail", jp4);
		*/

		jPanel1 = new JPanel();
		logInBTN = new JButton("Login");
		logInBTN.addActionListener(this);
		cancelBTN = new JButton("Cancle");

		registerBTN = new JButton("Register Guide");

		jPanel1.add(logInBTN);
		jPanel1.add(cancelBTN);
		jPanel1.add(registerBTN);

		this.add(jbl1, "North");
		this.add(jtp, "Center");
		this.add(jPanel1, "South");
		this.setSize(350, 240);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		// if user click login button
		if (arg0.getSource() == logInBTN) {
			//Create a new user object
			ClientUser qqClientUser = new ClientUser();
			User u = new User();
			//Set the user's username and password which was entered in the window
			u.setUserId(jPanel2_jtf.getText().trim());
			u.setPasswd(new String(jPanel2_jpf.getPassword()));

			//Check the user log in info by sending it to the server
			//If successful...
			if (qqClientUser.checkUser(u)) {
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
					
					/*
					Message m = new Message();
					m.setMesType(MessageType.message_get_onLineFriend);
					
					m.setSender(u.getUserId());
					oos.writeObject(m);
					*/
					
				} catch (Exception e) {
					e.printStackTrace();
				}

				// close login page
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(this, "UserID or password wrong!");
			}
		}
	}

}