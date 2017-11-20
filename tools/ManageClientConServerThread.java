/**
 * this is a class to manage threads between Clients and server
 */
package tools;

import java.util.*;
public class ManageClientConServerThread {

	private static HashMap hm=new HashMap<String, ClientConServerThread>();
	
	//add
	public static void addClientConServerThread(String qqId,ClientConServerThread ccst)
	{
		hm.put(qqId, ccst);
	}
	
	//get by id
	public static ClientConServerThread getClientConServerThread(String id)
	{
		return (ClientConServerThread)hm.get(id);
	}
}