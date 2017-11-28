/**
 * function：client login page
 */
package view;

import java.io.*;
import javax.swing.*;

import controlor.ClientUser;
import tools.*;
import model.*;

import model.Message;
import model.MessageType;
import tools.ManageClientConServerThread;
import tools.ManageFriendList;

import java.awt.*;
import java.awt.event.*;

public class ClientLogin extends JFrame implements ActionListener {


	JLabel jbl1;

	JTabbedPane jtp;
	JPanel jp2, jp3, jp4;
	JLabel jp2_jbl1, jp2_jbl2, jp2_jbl3, jp2_jbl4;
	JButton jp2_jb1;
	JTextField jp2_jtf;
	JPasswordField jp2_jpf;
	JCheckBox jp2_jcb1, jp2_jcb2;

	JPanel jp1;
	JButton jp1_jb1, jp1_jb2, jp1_jb3;

	public static void main(String[] args) {
		ClientLogin qqClientLogin = new ClientLogin();
	}

	public ClientLogin() {
		jbl1 = new JLabel(new ImageIcon("image/tou.gif"));

		jp2 = new JPanel(new GridLayout(3, 3));

		jp2_jbl1 = new JLabel("UserID", JLabel.CENTER);
		jp2_jbl2 = new JLabel("Password", JLabel.CENTER);
		jp2_jbl3 = new JLabel("Forgot password", JLabel.CENTER);
		jp2_jbl3.setForeground(Color.blue);
		jp2_jbl4 = new JLabel("Applying password protection", JLabel.CENTER);
		jp2_jb1 = new JButton("CLear Number");
		jp2_jtf = new JTextField();
		jp2_jpf = new JPasswordField();
		jp2_jcb1 = new JCheckBox("Invisible login");
		jp2_jcb2 = new JCheckBox("Remember password");

		jp2.add(jp2_jbl1);
		jp2.add(jp2_jtf);
		jp2.add(jp2_jb1);
		jp2.add(jp2_jbl2);
		jp2.add(jp2_jpf);
		jp2.add(jp2_jbl3);
		jp2.add(jp2_jcb1);
		jp2.add(jp2_jcb2);
		jp2.add(jp2_jbl4);

		jtp = new JTabbedPane();
		jtp.add("User Account", jp2);
		jp3 = new JPanel();
		jtp.add("Mobile phone", jp3);
		jp4 = new JPanel();
		jtp.add("E-mail", jp4);

		jp1 = new JPanel();
		jp1_jb1 = new JButton("Login");
		jp1_jb1.addActionListener(this);
		jp1_jb2 = new JButton("Cancle");

		jp1_jb3 = new JButton("Register Guide");

		jp1.add(jp1_jb1);
		jp1.add(jp1_jb2);
		jp1.add(jp1_jb3);

		this.add(jbl1, "North");
		this.add(jtp, "Center");
		this.add(jp1, "South");
		this.setSize(350, 240);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		// if user click login button
		if (arg0.getSource() == jp1_jb1) {
			ClientUser qqClientUser = new ClientUser();
			User u = new User();
			u.setUserId(jp2_jtf.getText().trim());
			u.setPasswd(new String(jp2_jpf.getPassword()));

			if (qqClientUser.checkUser(u)) {
				try {
					FriendList qqList = new FriendList(u.getUserId());
					ManageFriendList.addQqFriendList(u.getUserId(), qqList);

					ObjectOutputStream oos = new ObjectOutputStream(ManageClientConServerThread
							.getClientConServerThread(u.getUserId()).getS().getOutputStream());

					Message m = new Message();
					m.setMesType(MessageType.message_get_onLineFriend);
					
					m.setSender(u.getUserId());
					oos.writeObject(m);
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
