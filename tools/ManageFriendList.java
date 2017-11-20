/**
 * this is a class to manage friendlist
 */
package tools;

import java.util.*;

import view.*;

import java.io.*;
public class ManageFriendList {

	private static HashMap hm=new HashMap<String, FriendList>();
	
	public static void addQqFriendList(String qqid,FriendList qqFriendList){
		
		hm.put(qqid, qqFriendList);
	}
	
	public static FriendList getQqFriendList(String qqId)
	{
		return (FriendList)hm.get(qqId);
	}
}