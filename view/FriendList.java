/**
 * Friendlist page
 */
package view;

import tools.*;
//import model.Message;

import javax.swing.*;

import org.json.simple.JSONObject;

import java.awt.*;
import java.awt.event.*;

public class FriendList extends JFrame implements ActionListener, MouseListener {

	JPanel jphy1, jphy2, jphy3;
	JButton jphy_jb1, jphy_jb2, jphy_jb3;
	JScrollPane jsp1;
	String owner;

	JPanel jpmsr1, jpmsr2, jpmsr3;
	JButton jpmsr_jb1, jpmsr_jb2, jpmsr_jb3;
	JScrollPane jsp2;
	JLabel[] jb1s;

	CardLayout cl;

	// update online Friendlist
	public void upateFriend(JSONObject m) {
		System.out.println("FriendList"+m.toString());
		String onLineFriend[] = m.get("connection").toString().split(" ");

		for (int i = 0; i < onLineFriend.length; i++) {

			jb1s[Integer.parseInt(onLineFriend[i]) - 1].setEnabled(true);
		}
	}

	public FriendList(String ownerId) {
		this.owner = ownerId;

		jphy_jb1 = new JButton("My Friend");
		jphy_jb2 = new JButton("Stranger");
		jphy_jb2.addActionListener(this);
		jphy_jb3 = new JButton("Black List");
		jphy1 = new JPanel(new BorderLayout());

		jphy2 = new JPanel(new GridLayout(50, 1, 4, 4));
		
		// initiate friendlist
		jb1s = new JLabel[50];

		for (int i = 0; i < jb1s.length; i++) {
			jb1s[i] = new JLabel(i + 1 + "", new ImageIcon("image/mm.jpg"), JLabel.LEFT);
			// default offline
			jb1s[i].setEnabled(false);
			if (jb1s[i].getText().equals(ownerId)) {
				jb1s[i].setEnabled(true);
			}
			jb1s[i].addMouseListener(this);
			jphy2.add(jb1s[i]);

		}

		jphy3 = new JPanel(new GridLayout(2, 1));
		jphy3.add(jphy_jb2);
		jphy3.add(jphy_jb3);

		jsp1 = new JScrollPane(jphy2);

		jphy1.add(jphy_jb1, "North");
		jphy1.add(jsp1, "Center");
		jphy1.add(jphy3, "South");

		jpmsr_jb1 = new JButton("我的好友");
		jpmsr_jb1.addActionListener(this);
		jpmsr_jb2 = new JButton("陌生�?");
		jpmsr_jb3 = new JButton("黑名�?");
		jpmsr1 = new JPanel(new BorderLayout());

		jpmsr2 = new JPanel(new GridLayout(20, 1, 4, 4));

		JLabel[] jb1s2 = new JLabel[20];

		for (int i = 0; i < jb1s2.length; i++) {
			jb1s2[i] = new JLabel(i + 1 + "", new ImageIcon("image/mm.jpg"), JLabel.LEFT);
			jpmsr2.add(jb1s2[i]);
		}

		jpmsr3 = new JPanel(new GridLayout(2, 1));
		jpmsr3.add(jpmsr_jb1);
		jpmsr3.add(jpmsr_jb2);

		jsp2 = new JScrollPane(jpmsr2);

		jpmsr1.add(jpmsr3, "North");
		jpmsr1.add(jsp2, "Center");
		jpmsr1.add(jpmsr_jb3, "South");

		cl = new CardLayout();
		this.setLayout(cl);
		this.add(jphy1, "1");
		this.add(jpmsr1, "2");

		this.setTitle(ownerId);
		this.setSize(140, 400);
		this.setVisible(true);

	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == jphy_jb2) {
			cl.show(this.getContentPane(), "2");
		} else if (arg0.getSource() == jpmsr_jb1) {
			cl.show(this.getContentPane(), "1");
		}
	}

	public void mouseClicked(MouseEvent arg0) {
		// double click event 
		if (arg0.getClickCount() == 2) {
			// get friendNo
			String friendNo = ((JLabel) arg0.getSource()).getText();
			Chat chat = new Chat(this.owner, friendNo);

			// add Chat to ManageChat Class
			ManageChat.addChat(this.owner + " " + friendNo, chat);

		}
	}

	public void mouseEntered(MouseEvent arg0) {
		JLabel jl = (JLabel) arg0.getSource();
		jl.setForeground(Color.red);
	}

	public void mouseExited(MouseEvent arg0) {
		JLabel jl = (JLabel) arg0.getSource();
		jl.setForeground(Color.black);
	}

	public void mousePressed(MouseEvent arg0) {

	}

	public void mouseReleased(MouseEvent arg0) {

	}

}
