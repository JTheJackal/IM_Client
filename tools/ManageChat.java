/**
 * This is a class to manage Chat page
 */
package tools;

import java.util.*;

import view.*;
public class ManageChat {

	private static HashMap hm=new HashMap<String, Chat>();
	
	//add
	public static void addChat(String loginIdAnFriendId,Chat chat)
	{
		hm.put(loginIdAnFriendId, chat);
	}
	//get
	public static Chat getChat(String loginIdAnFriendId )
	{
		if(hm.get(loginIdAnFriendId) != null) {
			
			return (Chat)hm.get(loginIdAnFriendId);
		}else {
			
			System.out.println("There is nothing to return");
			System.out.println("Creating a  new chat...");
			return null;
		}
		
		//return (Chat)hm.get(loginIdAnFriendId);
	}
	
}